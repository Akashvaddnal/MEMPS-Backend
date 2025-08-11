package com.mepms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mepms.entity.EquipmentUnit;
import com.mepms.service.EquipmentUnitService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EquipmentUnitController.class)
public class EquipmentUnitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentUnitService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private EquipmentUnit createMockEquipmentUnit(String id, String equipmentId) {
        EquipmentUnit unit = new EquipmentUnit();
        unit.setId(id);
        unit.setEquipmentUnitId(equipmentId);
        unit.setDepartmentId("dept1");
        unit.setLocation("Location " + id);
        unit.setStatus("ACTIVE");
        unit.setPurchaseDate(new Date());
        return unit;
    }

    @Test
    public void createEquipmentUnit_ShouldReturnCreatedUnit() throws Exception {
        EquipmentUnit unit = createMockEquipmentUnit("1", "equip1");
        Mockito.when(service.createEquipmentUnit(Mockito.any(EquipmentUnit.class))).thenReturn(unit);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/equipment-units")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(unit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentUnitId", is(unit.getEquipmentUnitId())));
    }

    @Test
    public void getEquipmentUnitById_ShouldReturnUnit() throws Exception {
        EquipmentUnit unit = createMockEquipmentUnit("1", "equip1");
        Mockito.when(service.getEquipmentUnitById("1")).thenReturn(Optional.of(unit));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment-units/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(unit.getId())));
    }

    @Test
    public void getEquipmentUnitById_ShouldReturnNotFound() throws Exception {
        Mockito.when(service.getEquipmentUnitById("99")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment-units/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllEquipmentUnits_ShouldReturnAllUnits() throws Exception {
        EquipmentUnit unit1 = createMockEquipmentUnit("1", "equip1");
        EquipmentUnit unit2 = createMockEquipmentUnit("2", "equip2");
        List<EquipmentUnit> units = Arrays.asList(unit1, unit2);

        Mockito.when(service.getAllEquipmentUnits()).thenReturn(units);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment-units")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].equipmentUnitId", is(unit1.getEquipmentUnitId())))
                .andExpect(jsonPath("$[1].equipmentUnitId", is(unit2.getEquipmentUnitId())));
    }

    @Test
    public void updateEquipmentUnit_ShouldReturnUpdatedUnit() throws Exception {
        EquipmentUnit unit = createMockEquipmentUnit("1", "equip1");
        Mockito.when(service.getEquipmentUnitById("1")).thenReturn(Optional.of(unit));
        Mockito.when(service.updateEquipmentUnit("1", unit)).thenReturn(unit);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/equipment-units/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(unit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(unit.getId())));
    }

    @Test
    public void findEquipmentUnitsByDepartment_ShouldReturnUnits() throws Exception {
        EquipmentUnit unit1 = createMockEquipmentUnit("1", "equip1");
        EquipmentUnit unit2 = createMockEquipmentUnit("2", "equip2");
        List<EquipmentUnit> units = Arrays.asList(unit1, unit2);

        Mockito.when(service.findByDepartmentId("dept1")).thenReturn(units);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment-units/search/dept")
                        .param("departmentId", "dept1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].departmentId", is("dept1")))
                .andExpect(jsonPath("$[1].departmentId", is("dept1")));
    }
}