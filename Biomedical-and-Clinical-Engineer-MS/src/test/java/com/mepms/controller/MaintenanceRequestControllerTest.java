package com.mepms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mepms.entity.MaintenanceRequest;
import com.mepms.service.MaintenanceRequestService;

class MaintenanceRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MaintenanceRequestService maintenanceRequestService;

    @InjectMocks
    private MaintenanceRequestController controller;

    private MaintenanceRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        request = new MaintenanceRequest();
        request.setId("1");
        request.setEquipmentId("equip1");
        request.setStatus("Pending");
    }

    @Test
    void createMaintenance() throws Exception {
        when(maintenanceRequestService.create(any(MaintenanceRequest.class))).thenReturn(request);

        mockMvc.perform(post("/api/Main-Req/maintenance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"equipmentId\":\"equip1\",\"status\":\"Pending\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Pending"));

        verify(maintenanceRequestService, times(1)).create(any(MaintenanceRequest.class));
    }

    @Test
    void getMaintenance() throws Exception {
        when(maintenanceRequestService.getById("1")).thenReturn(request);

        mockMvc.perform(get("/api/Main-Req/maintenance/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId").value("equip1"));

        verify(maintenanceRequestService, times(1)).getById("1");
    }

    @Test
    void getAllMaintenances() throws Exception {
        List<MaintenanceRequest> requests = Arrays.asList(request);
        when(maintenanceRequestService.getAll()).thenReturn(requests);

        mockMvc.perform(get("/api/Main-Req/maintenance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("Pending"));

        verify(maintenanceRequestService, times(1)).getAll();
    }

    @Test
    void updateMaintenance() throws Exception {
        when(maintenanceRequestService.update(anyString(), any(MaintenanceRequest.class))).thenReturn(request);

        mockMvc.perform(put("/api/Main-Req/maintenance/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"equipmentId\":\"equip1\",\"status\":\"Pending\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Pending"));

        verify(maintenanceRequestService, times(1)).update(eq("1"), any(MaintenanceRequest.class));
    }

    @Test
    void deleteMaintenance() throws Exception {
        doNothing().when(maintenanceRequestService).delete("1");

        mockMvc.perform(delete("/api/Main-Req/maintenance/1"))
                .andExpect(status().isOk());

        verify(maintenanceRequestService, times(1)).delete("1");
    }
}