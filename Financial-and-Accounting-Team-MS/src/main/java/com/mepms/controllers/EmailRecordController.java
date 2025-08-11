//package com.mepms.controllers;
//
//
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.mepms.entity.EmailRecord;
//import com.mepms.service.EmailRecordService;
//
//@RestController
//@RequestMapping("/api/emails")
//public class EmailRecordController {
//
//    @Autowired
//    private EmailRecordService emailRecordService;
//
//    @PostMapping
//    public EmailRecord create(@RequestBody EmailRecord emailRecord) {
//        emailRecord.setSentAt(LocalDateTime.now());
//        return emailRecordService.create(emailRecord);
//    }
//
//    @GetMapping
//    public List<EmailRecord> getAll() {
//        return emailRecordService.getAll();
//    }
//
//    @GetMapping("/{id}")
//    public EmailRecord getById(@PathVariable String id) {
//        return emailRecordService.getById(id);
//    }
//
//    @GetMapping("/recipient/{email}")
//    public List<EmailRecord> getByRecipient(@PathVariable String email) {
//        return emailRecordService.getByRecipient(email);
//    }
//
//    @PostMapping("/{id}/documents")
//    public void addDocumentToEmail(@PathVariable String id, @RequestBody EmailRecord.DocumentAttachment doc) {
//        emailRecordService.addDocumentToEmail(id, doc);
//    }
//
//    @PutMapping("/{id}")
//    public EmailRecord update(@PathVariable String id, @RequestBody EmailRecord emailRecord) {
//        emailRecord.setId(id);
//        return emailRecordService.update(emailRecord);
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable String id) {
//        emailRecordService.deleteById(id);
//    }
//}


package com.mepms.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mepms.entity.EmailRecord;
import com.mepms.service.EmailRecordService;

@RestController
@RequestMapping("/api/emails")
@CrossOrigin
public class EmailRecordController {

    @Autowired
    private EmailRecordService emailRecordService;

    @PostMapping
    public EmailRecord create(@RequestBody EmailRecord emailRecord) {
        emailRecord.setSentAt(LocalDateTime.now());
        EmailRecord created = emailRecordService.create(emailRecord);
        // Optionally send email via service here
        return created;
    }

    @GetMapping
    public List<EmailRecord> getAll() {
        return emailRecordService.getAll();
    }

    @GetMapping("/{id}")
    public EmailRecord getById(@PathVariable String id) {
        return emailRecordService.getById(id);
    }

    @GetMapping("/recipient/{email}")
    public List<EmailRecord> getByRecipient(@PathVariable String email) {
        return emailRecordService.getByRecipients(email);
    }

    @PostMapping("/{id}/documents")
    public void addDocument(@PathVariable String id, @RequestBody EmailRecord.DocumentAttachment doc) {
        emailRecordService.addDocumentToEmail(id, doc);
    }

    @PutMapping("/{id}")
    public EmailRecord update(@PathVariable String id, @RequestBody EmailRecord emailRecord) {
        emailRecord.setId(id);
        return emailRecordService.update(emailRecord);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        emailRecordService.deleteById(id);
    }
}
