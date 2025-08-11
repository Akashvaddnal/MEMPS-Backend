package com.mepms.service;


import java.time.LocalDateTime;
import java.util.List;

import com.mepms.entity.EmailRecord;


public interface EmailRecordService {

    EmailRecord create(EmailRecord emailRecord);

    EmailRecord update(EmailRecord emailRecord);

    void deleteById(String id);

    EmailRecord getById(String id);

    List<EmailRecord> getAll();

    List<EmailRecord> getByRecipients(String recipient);

    List<EmailRecord> getBySentDateRange(LocalDateTime start, LocalDateTime end);

    List<EmailRecord> getBySuccess(boolean success);

    void addDocumentToEmail(String id, EmailRecord.DocumentAttachment doc);
    void sendEmail(EmailRecord emailRecord) throws Exception;  // Include sending operation
    
    void sendOtp(String to, String otp);
}
