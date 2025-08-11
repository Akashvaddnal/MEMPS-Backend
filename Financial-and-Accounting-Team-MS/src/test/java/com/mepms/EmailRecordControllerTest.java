package com.mepms;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mepms.controllers.EmailRecordController;
import com.mepms.entity.EmailRecord;
import com.mepms.service.EmailRecordService;

@WebMvcTest(EmailRecordController.class)
public class EmailRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailRecordService emailRecordService;

//    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private EmailRecord createMockEmail(String id) {
        EmailRecord email = new EmailRecord();
        email.setId(id);
        email.setRecipients(Arrays.asList("recipient@example.com"));
        email.setSubject("Test Email");
        email.setContent("This is a test email");
        email.setSentAt(LocalDateTime.now());
        email.setSuccess(true);
        return email;
    }

    @Test
    public void createEmailRecord_ShouldReturnCreatedEmail() throws Exception {
        EmailRecord email = createMockEmail("1");
        Mockito.when(emailRecordService.create(Mockito.any(EmailRecord.class))).thenReturn(email);

        mockMvc.perform(post("/api/emails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(email)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject", is(email.getSubject())));
    }

    @Test
    public void getAllEmailRecords_ShouldReturnAllEmails() throws Exception {
        EmailRecord email1 = createMockEmail("1");
        EmailRecord email2 = createMockEmail("2");
        List<EmailRecord> emails = Arrays.asList(email1, email2);

        Mockito.when(emailRecordService.getAll()).thenReturn(emails);

        mockMvc.perform(get("/api/emails"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(email1.getId())))
                .andExpect(jsonPath("$[1].id", is(email2.getId())));
    }

    @Test
    public void getEmailRecordById_ShouldReturnEmail() throws Exception {
        EmailRecord email = createMockEmail("1");
        Mockito.when(emailRecordService.getById("1")).thenReturn(email);

        mockMvc.perform(get("/api/emails/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(email.getId())));
    }

    @Test
    public void getEmailsByRecipient_ShouldReturnEmails() throws Exception {
        EmailRecord email1 = createMockEmail("1");
        EmailRecord email2 = createMockEmail("2");
        List<EmailRecord> emails = Arrays.asList(email1, email2);

        Mockito.when(emailRecordService.getByRecipients("recipient@example.com")).thenReturn(emails);

        mockMvc.perform(get("/api/emails/recipient/recipient@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].recipients[0]", is("recipient@example.com")));
    }

    @Test
    public void updateEmailRecord_ShouldReturnUpdatedEmail() throws Exception {
        EmailRecord email = createMockEmail("1");
        Mockito.when(emailRecordService.update(Mockito.any(EmailRecord.class))).thenReturn(email);

        mockMvc.perform(put("/api/emails/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(email)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(email.getId())));
    }
}