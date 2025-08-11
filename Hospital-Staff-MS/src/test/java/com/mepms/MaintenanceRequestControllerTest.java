package com.mepms;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mepms.controller.MaintenanceRequestController;
import com.mepms.entity.MaintenanceRequest;
import com.mepms.service.MaintenanceRequestService;

@WebMvcTest(MaintenanceRequestController.class)
public class MaintenanceRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaintenanceRequestService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MaintenanceRequest createMockRequest(String id, String equipmentId) {
        MaintenanceRequest request = new MaintenanceRequest();
        request.setId(id);
        request.setEquipmentId(equipmentId);
        request.setUnitId("unit" + id);
        request.setReportedBy("user1");
        request.setDepartment("dept1");
        request.setIssueDescription("Test Issue");
        request.setStatus("PENDING");
        request.setReportedAt(new Date());
        request.setMaintenanceType("CORRECTIVE");
        request.setAccepted(true);
        return request;
    }

    @Test
    public void createMaintenanceRequest_ShouldReturnCreatedRequest() throws Exception {
        MaintenanceRequest request = createMockRequest("1", "equip1");
        Mockito.when(service.create(Mockito.any(MaintenanceRequest.class))).thenReturn(request);

        mockMvc.perform(post("/api/Main-Req/maintenance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId", is(request.getEquipmentId())));
    }

    @Test
    public void getMaintenanceRequestById_ShouldReturnRequest() throws Exception {
        MaintenanceRequest request = createMockRequest("1", "equip1");
        Mockito.when(service.getById("1")).thenReturn(request);

        mockMvc.perform(get("/api/Main-Req/maintenance/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(request.getId())));
    }

    @Test
    public void getAllMaintenanceRequests_ShouldReturnAllRequests() throws Exception {
        MaintenanceRequest request1 = createMockRequest("1", "equip1");
        MaintenanceRequest request2 = createMockRequest("2", "equip2");
        List<MaintenanceRequest> requests = Arrays.asList(request1, request2);

        Mockito.when(service.getAll()).thenReturn(requests);

        mockMvc.perform(get("/api/Main-Req/maintenance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].equipmentId", is(request1.getEquipmentId())))
                .andExpect(jsonPath("$[1].equipmentId", is(request2.getEquipmentId())));
    }

    @Test
    public void updateMaintenanceRequest_ShouldReturnUpdatedRequest() throws Exception {
        MaintenanceRequest request = createMockRequest("1", "equip1");
        Mockito.when(service.update("1", request)).thenReturn(request);

        mockMvc.perform(put("/api/Main-Req/maintenance/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(request.getId())));
    }

    @Test
    public void deleteMaintenanceRequest_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/Main-Req/maintenance/1"))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).delete("1");
    }
}