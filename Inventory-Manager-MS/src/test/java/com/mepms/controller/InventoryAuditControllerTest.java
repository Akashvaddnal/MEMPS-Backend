package com.mepms.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mepms.entity.InventoryAudit;
import com.mepms.service.InventoryAuditService;

@WebMvcTest(InventoryAuditController.class)
public class InventoryAuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryAuditService service;

//    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private InventoryAudit createMockAudit(String id, String auditType) {
        InventoryAudit audit = new InventoryAudit();
        audit.setId(id);
        audit.setAuditType(auditType);
        audit.setDatePerformed(Instant.now());
        audit.setPerformedBy("user1");
        audit.setItemsChecked(100);
        audit.setDiscrepancies(5);
        audit.setStatus("COMPLETED");
        return audit;
    }

    @Test
    public void createInventoryAudit_ShouldReturnCreatedAudit() throws Exception {
        InventoryAudit audit = createMockAudit("1", "FULL");
        Mockito.when(service.create(Mockito.any(InventoryAudit.class))).thenReturn(audit);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory-audit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(audit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.auditType", is(audit.getAuditType())));
    }

    @Test
    public void getInventoryAuditById_ShouldReturnAudit() throws Exception {
        InventoryAudit audit = createMockAudit("1", "FULL");
        Mockito.when(service.getById("1")).thenReturn(audit);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory-audit/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(audit.getId())));
    }

    @Test
    public void getAllInventoryAudits_ShouldReturnAllAudits() throws Exception {
        InventoryAudit audit1 = createMockAudit("1", "FULL");
        InventoryAudit audit2 = createMockAudit("2", "PARTIAL");
        List<InventoryAudit> audits = Arrays.asList(audit1, audit2);

        Mockito.when(service.getAll()).thenReturn(audits);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory-audit")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].auditType", is(audit1.getAuditType())))
                .andExpect(jsonPath("$[1].auditType", is(audit2.getAuditType())));
    }

    @Test
    public void updateInventoryAudit_ShouldReturnUpdatedAudit() throws Exception {
        InventoryAudit audit = createMockAudit("1", "FULL");
        Mockito.when(service.update("1", audit)).thenReturn(audit);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/inventory-audit/inventory-audit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(audit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(audit.getId())));
    }
}