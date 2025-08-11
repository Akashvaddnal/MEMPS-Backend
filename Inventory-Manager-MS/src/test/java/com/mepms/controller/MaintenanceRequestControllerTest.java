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
import com.mepms.entity.MaintenanceRequest;
import com.mepms.service.MaintenanceRequestService;

@WebMvcTest(MaintenanceRequestController.class)
public class MaintenanceRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaintenanceRequestService service;

//    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private MaintenanceRequest createMockRequest(String id, String equipmentId) {
        MaintenanceRequest request = new MaintenanceRequest();
        request.setId(id);
        request.setEquipmentId(equipmentId);
        request.setUnitId("unit" + id);
        request.setReportedBy("user1");
        request.setDepartment("dept1");
        request.setIssueDescription("Test Issue");
        request.setStatus("PENDING");
        request.setReportedAt(Instant.now());
        request.setMaintenanceType("CORRECTIVE");
        return request;
    }

    @Test
    public void createMaintenanceRequest_ShouldReturnCreatedRequest() throws Exception {
        MaintenanceRequest request = createMockRequest("1", "equip1");
        Mockito.when(service.create(Mockito.any(MaintenanceRequest.class))).thenReturn(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/Main-Req/maintenance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId", is(request.getEquipmentId())));
    }

    @Test
    public void getMaintenanceRequestById_ShouldReturnRequest() throws Exception {
        MaintenanceRequest request = createMockRequest("1", "equip1");
        Mockito.when(service.getById("1")).thenReturn(request);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/Main-Req/maintenance/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(request.getId())));
    }

    @Test
    public void getAllMaintenanceRequests_ShouldReturnAllRequests() throws Exception {
        MaintenanceRequest request1 = createMockRequest("1", "equip1");
        MaintenanceRequest request2 = createMockRequest("2", "equip2");
        List<MaintenanceRequest> requests = Arrays.asList(request1, request2);

        Mockito.when(service.getAll()).thenReturn(requests);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/Main-Req/maintenance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].equipmentId", is(request1.getEquipmentId())))
                .andExpect(jsonPath("$[1].equipmentId", is(request2.getEquipmentId())));
    }

    @Test
    public void updateMaintenanceRequest_ShouldReturnUpdatedRequest() throws Exception {
        MaintenanceRequest request = createMockRequest("1", "equip1");
        Mockito.when(service.update("1", request)).thenReturn(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/Main-Req/maintenance/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(request.getId())));
    }
}