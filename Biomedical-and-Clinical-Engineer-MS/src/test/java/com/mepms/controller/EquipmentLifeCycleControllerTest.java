package com.mepms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mepms.entity.EquipmentLifeCycle;
import com.mepms.entity.EquipmentUsage;
import com.mepms.service.EquipmentLifeCycleService;

class EquipmentLifeCycleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EquipmentLifeCycleService service;

    @InjectMocks
    private EquipmentLifeCycleController controller;

    private EquipmentLifeCycle lifecycle;
    private EquipmentUsage usage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        lifecycle = new EquipmentLifeCycle();
        lifecycle.setId("1");
        lifecycle.setEquipmentId("equip1");

        usage = new EquipmentUsage();
        usage.setId("1");
        usage.setEquipmentId("equip1");
    }

    @Test
    void create() throws Exception {
        when(service.save(any(EquipmentLifeCycle.class))).thenReturn(lifecycle);

        mockMvc.perform(post("/api/equipment-lifecycle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"equipmentId\":\"equip1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId").value("equip1"));

        verify(service, times(1)).save(any(EquipmentLifeCycle.class));
    }

    @Test
    void getById() throws Exception {
        when(service.findById("1")).thenReturn(java.util.Optional.of(lifecycle));

        mockMvc.perform(get("/api/equipment-lifecycle/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId").value("equip1"));

        verify(service, times(1)).findById("1");
    }

    @Test
    void getAll() throws Exception {
        when(service.findAll()).thenReturn(Arrays.asList(lifecycle));

        mockMvc.perform(get("/api/equipment-lifecycle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].equipmentId").value("equip1"));

        verify(service, times(1)).findAll();
    }

    @Test
    void getByEquipmentId() throws Exception {
        when(service.findByEquipmentId("equip1")).thenReturn(Arrays.asList(lifecycle));

        mockMvc.perform(get("/api/equipment-lifecycle/equipment/equip1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].equipmentId").value("equip1"));

        verify(service, times(1)).findByEquipmentId("equip1");
    }

    @Test
    void getByUnitId() throws Exception {
        when(service.findByUnitId("unit1")).thenReturn(Arrays.asList(usage));

        mockMvc.perform(get("/api/equipment-lifecycle/unitid/unit1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].equipmentId").value("equip1"));

        verify(service, times(1)).findByUnitId("unit1");
    }

//    @Test
//    void delete() throws Exception {
//        doNothing().when(service).deleteById("1");
//
//        mockMvc.perform(delete("/api/equipment-lifecycle/1"))
//                .andExpect(status().isNoContent());
//
//        verify(service, times(1)).deleteById("1");
//    }

    @Test
    void updateLifecycle() throws Exception {
        when(service.update(anyString(), any(EquipmentLifeCycle.class))).thenReturn(lifecycle);

        mockMvc.perform(put("/api/equipment-lifecycle/lifecycle/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"equipmentId\":\"equip1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId").value("equip1"));

        verify(service, times(1)).update(eq("1"), any(EquipmentLifeCycle.class));
    }
}