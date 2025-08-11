package com.mepms;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mepms.controller.EquipmentLifeCycleController;
import com.mepms.entity.EquipmentLifeCycle;
import com.mepms.service.EquipmentLifeCycleService;

@WebMvcTest(EquipmentLifeCycleController.class)
public class EquipmentLifeCycleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentLifeCycleService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private EquipmentLifeCycle createMockLifeCycle(String id, String equipmentId) {
        EquipmentLifeCycle lifecycle = new EquipmentLifeCycle();
        lifecycle.setId(id);
        lifecycle.setEquipmentId(equipmentId);
        lifecycle.setUnitId("unit" + id);
        lifecycle.setAcquisitionDate(new Date());
        lifecycle.setExpectedEndOfLife(new Date(System.currentTimeMillis() + 1000000000));
        lifecycle.setMaintenanceCount(3);
        lifecycle.setTotalMaintenanceCost(1500.0);
        return lifecycle;
    }

    @Test
    public void createLifeCycle_ShouldReturnCreatedLifeCycle() throws Exception {
        EquipmentLifeCycle lifecycle = createMockLifeCycle("1", "equip1");
        Mockito.when(service.save(Mockito.any(EquipmentLifeCycle.class))).thenReturn(lifecycle);

        mockMvc.perform(post("/api/equipment-lifecycle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lifecycle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId", is(lifecycle.getEquipmentId())));
    }

    @Test
    public void getLifeCycleById_ShouldReturnLifeCycle() throws Exception {
        EquipmentLifeCycle lifecycle = createMockLifeCycle("1", "equip1");
        Mockito.when(service.findById("1")).thenReturn(Optional.of(lifecycle));

        mockMvc.perform(get("/api/equipment-lifecycle/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(lifecycle.getId())));
    }

    @Test
    public void getLifeCycleById_ShouldReturnNotFound() throws Exception {
        Mockito.when(service.findById("99")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/equipment-lifecycle/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllLifeCycles_ShouldReturnAllLifeCycles() throws Exception {
        EquipmentLifeCycle lifecycle1 = createMockLifeCycle("1", "equip1");
        EquipmentLifeCycle lifecycle2 = createMockLifeCycle("2", "equip2");
        List<EquipmentLifeCycle> lifecycles = Arrays.asList(lifecycle1, lifecycle2);

        Mockito.when(service.findAll()).thenReturn(lifecycles);

        mockMvc.perform(get("/api/equipment-lifecycle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].equipmentId", is(lifecycle1.getEquipmentId())))
                .andExpect(jsonPath("$[1].equipmentId", is(lifecycle2.getEquipmentId())));
    }

    @Test
    public void updateLifeCycle_ShouldReturnUpdatedLifeCycle() throws Exception {
        EquipmentLifeCycle lifecycle = createMockLifeCycle("1", "equip1");
        Mockito.when(service.update("1", lifecycle)).thenReturn(lifecycle);

        mockMvc.perform(put("/api/equipment-lifecycle/lifecycle/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lifecycle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(lifecycle.getId())));
    }
}