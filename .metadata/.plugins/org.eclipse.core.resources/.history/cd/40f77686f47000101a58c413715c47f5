package com.mepms.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mepms.entity.EmailRecord;
import com.mepms.repository.EmailRecordRepository;
import com.mepms.service.EmailRecordService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailRecordServiceImpl implements EmailRecordService {

    @Autowired
    private EmailRecordRepository emailRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public EmailRecord create(EmailRecord emailRecord) {
    	try {
			sendEmail(emailRecord);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return emailRepo.save(emailRecord);
    }

    @Override
    public EmailRecord update(EmailRecord emailRecord) {
        return emailRepo.save(emailRecord);
    }

    @Override
    public void deleteById(String id) {
        emailRepo.deleteById(id);
    }

    @Override
    public EmailRecord getById(String id) {
        return emailRepo.findById(id).orElse(null);
    }

    @Override
    public List<EmailRecord> getAll() {
        return emailRepo.findAll();
    }

    @Override
    public List<EmailRecord> getByRecipients(String recipient) {
        return emailRepo.findByRecipientsContaining(recipient);
    }

    @Override
    public List<EmailRecord> getBySentDateRange(LocalDateTime start, LocalDateTime end) {
        return emailRepo.findBySentAtBetween(start, end);
    }

    @Override
    public List<EmailRecord> getBySuccess(boolean success) {
        return emailRepo.findBySuccess(success);
    }

    @Override
    public void addDocumentToEmail(String id, EmailRecord.DocumentAttachment doc) {
        Optional<EmailRecord> opt = emailRepo.findById(id);
        if (opt.isPresent()) {
            EmailRecord record = opt.get();
            if(record.getDocuments() == null) {
                record.setDocuments(new ArrayList<>());
            }
            record.getDocuments().add(doc);
            emailRepo.save(record);
        }
    }

    @Override
    public void sendEmail(EmailRecord emailRecord) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String[] to = emailRecord.getRecipients().toArray(new String[0]);
        helper.setTo(to);
        helper.setSubject(emailRecord.getSubject());
        helper.setText(emailRecord.getContent());

        if(emailRecord.getDocuments() != null) {
            for(EmailRecord.DocumentAttachment doc : emailRecord.getDocuments()) {
                ByteArrayResource resource = new ByteArrayResource(doc.getData());
                helper.addAttachment(doc.getName(), resource);
            }
        }

        mailSender.send(message);

        // Save record as sent
        emailRecord.setSentAt(LocalDateTime.now());
        emailRecord.setSuccess(true);
        emailRecord.setErrorMessage(null);
        emailRepo.save(emailRecord);
    }
}
