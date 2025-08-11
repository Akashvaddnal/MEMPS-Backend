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
import com.mepms.entity.EquipmentUsage;
import com.mepms.service.EquipmentUsageService;

@WebMvcTest(EquipmentUsageController.class)
public class EquipmentUsageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentUsageService service;

//    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private EquipmentUsage createMockUsage(String id, String equipmentId) {
        EquipmentUsage usage = new EquipmentUsage();
        usage.setId(id);
        usage.setEquipmentId(equipmentId);
        usage.setUnitId("unit" + id);
        usage.setUsedBy("user" + id);
        usage.setReservedBy("reserver" + id);
        usage.setUsageStart(Instant.now());
        usage.setUsageEnd(Instant.now().plusSeconds(3600));
        usage.setPurpose("Test Purpose");
        usage.setStatus("ACTIVE");
        return usage;
    }

    @Test
    public void createEquipmentUsage_ShouldReturnCreatedUsage() throws Exception {
        EquipmentUsage usage = createMockUsage("1", "equip1");
        Mockito.when(service.save(Mockito.any(EquipmentUsage.class))).thenReturn(usage);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/equipment-usage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usage)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId", is(usage.getEquipmentId())));
    }

    @Test
    public void getEquipmentUsageById_ShouldReturnUsage() throws Exception {
        EquipmentUsage usage = createMockUsage("1", "equip1");
        Mockito.when(service.findById("1")).thenReturn(Optional.of(usage));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment-usage/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(usage.getId())));
    }

    @Test
    public void getEquipmentUsageById_ShouldReturnNotFound() throws Exception {
        Mockito.when(service.findById("99")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment-usage/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUsageByEquipmentId_ShouldReturnUsages() throws Exception {
        EquipmentUsage usage1 = createMockUsage("1", "equip1");
        EquipmentUsage usage2 = createMockUsage("2", "equip1");
        List<EquipmentUsage> usages = Arrays.asList(usage1, usage2);

        Mockito.when(service.findByEquipmentId("equip1")).thenReturn(usages);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment-usage/equipment/equip1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].equipmentId", is("equip1")))
                .andExpect(jsonPath("$[1].equipmentId", is("equip1")));
    }

    @Test
    public void updateEquipmentUsage_ShouldReturnUpdatedUsage() throws Exception {
        EquipmentUsage usage = createMockUsage("1", "equip1");
        Mockito.when(service.updateEquipmentUsage("1", usage)).thenReturn(usage);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/equipment-usage/equipment-usage/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usage)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(usage.getId())));
    }
}