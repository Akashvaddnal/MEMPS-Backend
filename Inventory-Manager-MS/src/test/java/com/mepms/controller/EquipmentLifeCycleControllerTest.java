package com.mepms.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import com.mepms.entity.EquipmentLifeCycle;
import com.mepms.service.EquipmentLifeCycleService;

@WebMvcTest(EquipmentLifeCycleController.class)
public class EquipmentLifeCycleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentLifeCycleService service;

//    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private EquipmentLifeCycle createMockLifeCycle(String id, String equipmentId) {
        EquipmentLifeCycle lifecycle = new EquipmentLifeCycle();
        lifecycle.setId(id);
        lifecycle.setEquipmentId(equipmentId);
        lifecycle.setUnitId("unit" + id);
        lifecycle.setAcquisitionDate(Instant.now());
        lifecycle.setExpectedEndOfLife(Instant.now().plusSeconds(1000000));
        lifecycle.setMaintenanceCount(3);
        lifecycle.setTotalMaintenanceCost(1500.0);
        lifecycle.setStatus("ACTIVE");
        return lifecycle;
    }

    @Test
    public void createLifeCycle_ShouldReturnCreatedLifeCycle() throws Exception {
        EquipmentLifeCycle lifecycle = createMockLifeCycle("1", "equip1");
        Mockito.when(service.save(Mockito.any(EquipmentLifeCycle.class))).thenReturn(lifecycle);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/equipment-lifecycle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lifecycle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId", is(lifecycle.getEquipmentId())));
    }

    @Test
    public void getLifeCycleById_ShouldReturnLifeCycle() throws Exception {
        EquipmentLifeCycle lifecycle = createMockLifeCycle("1", "equip1");
        Mockito.when(service.findById("1")).thenReturn(Optional.of(lifecycle));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment-lifecycle/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(lifecycle.getId())));
    }

    @Test
    public void getLifeCycleById_ShouldReturnNotFound() throws Exception {
        Mockito.when(service.findById("99")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment-lifecycle/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllLifeCycles_ShouldReturnAllLifeCycles() throws Exception {
        EquipmentLifeCycle lifecycle1 = createMockLifeCycle("1", "equip1");
        EquipmentLifeCycle lifecycle2 = createMockLifeCycle("2", "equip2");
        List<EquipmentLifeCycle> lifecycles = Arrays.asList(lifecycle1, lifecycle2);

        Mockito.when(service.findAll()).thenReturn(lifecycles);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment-lifecycle")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].equipmentId", is(lifecycle1.getEquipmentId())))
                .andExpect(jsonPath("$[1].equipmentId", is(lifecycle2.getEquipmentId())));
    }

    @Test
    public void updateLifeCycle_ShouldReturnUpdatedLifeCycle() throws Exception {
        EquipmentLifeCycle lifecycle = createMockLifeCycle("1", "equip1");
        Mockito.when(service.update("1", lifecycle)).thenReturn(lifecycle);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/equipment-lifecycle/lifecycle/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lifecycle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(lifecycle.getId())));
    }
}