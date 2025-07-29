package com.mepms.controllers;

import com.mepms.entity.AuditLogEO;
import com.mepms.service.AuditLogService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuditLogController.class)
public class AuditLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditLogService auditLogService;

    @Test
    public void testGetAllLogs() throws Exception {
        AuditLogEO log = new AuditLogEO();
        log.setId("log1");
        log.setUserId("user1");
        log.setAction("CREATE");
        log.setTimestamp(Instant.now());
        log.setDetails("Created a record");

        Mockito.when(auditLogService.getAllLogs()).thenReturn(Collections.singletonList(log));

        mockMvc.perform(get("/api/audit-logs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("log1")))
                .andExpect(jsonPath("$[0].action", is("CREATE")));

        Mockito.verify(auditLogService, Mockito.times(1)).getAllLogs();
    }

    @Test
    public void testGetLogById_Found() throws Exception {
        AuditLogEO log = new AuditLogEO();
        log.setId("log1");
        log.setUserId("user1");
        log.setAction("CREATE");
        log.setTimestamp(Instant.now());
        log.setDetails("Created a record");

        Mockito.when(auditLogService.getLogById("log1")).thenReturn(Optional.of(log));

        mockMvc.perform(get("/api/audit-logs/log1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("log1")))
                .andExpect(jsonPath("$.action", is("CREATE")));

        Mockito.verify(auditLogService).getLogById("log1");
    }

    @Test
    public void testGetLogById_NotFound() throws Exception {
        Mockito.when(auditLogService.getLogById("nonexistent")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/audit-logs/nonexistent")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        Mockito.verify(auditLogService).getLogById("nonexistent");
    }

    @Test
    public void testCreateLog() throws Exception {
        // JSON must include required fields: userId, action, timestamp, details
        String logJson = String.format("""
            {
              "userId": "user1",
              "action": "CREATE",
              "timestamp": "%s",
              "details": "Created a record"
            }
            """, Instant.now().toString());

        AuditLogEO savedLog = new AuditLogEO();
        savedLog.setId("log1");
        savedLog.setUserId("user1");
        savedLog.setAction("CREATE");
        savedLog.setTimestamp(Instant.now());
        savedLog.setDetails("Created a record");

        Mockito.when(auditLogService.saveAuditLog(Mockito.any(AuditLogEO.class))).thenReturn(savedLog);

        mockMvc.perform(post("/api/audit-logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(logJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("log1")))
                .andExpect(jsonPath("$.action", is("CREATE")))
                .andExpect(jsonPath("$.userId", is("user1")));

        Mockito.verify(auditLogService).saveAuditLog(Mockito.any(AuditLogEO.class));
    }

    @Test
    public void testDeleteById() throws Exception {
        Mockito.doNothing().when(auditLogService).deleteLog("log1");

        mockMvc.perform(delete("/api/audit-logs/log1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        Mockito.verify(auditLogService).deleteLog("log1");
    }
}
