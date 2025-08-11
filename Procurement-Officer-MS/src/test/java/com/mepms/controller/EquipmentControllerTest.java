package com.mepms.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.mepms.BaseControllerTest;
import com.mepms.entity.Equipment;
import com.mepms.service.EquipmentService;
import com.mepms.service.VendorService;

@WebMvcTest(EquipmentController.class)
public class EquipmentControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentService equipmentService;

    @MockBean
    private VendorService vendorService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected Object getController() {
        return new EquipmentController(equipmentService, vendorService);
    }

    private Equipment createMockEquipment(String id, String name) {
        Equipment equipment = new Equipment();
        equipment.set_id(id);
        equipment.setName(name);
        equipment.setModel("Model X");
        equipment.setSerialNumber("SN" + id);
        equipment.setCategory("Test");
        equipment.setStatus("Active");
        return equipment;
    }

    @Test
    public void getAllEquipment_ShouldReturnAllEquipment() throws Exception {
        Equipment equipment1 = createMockEquipment("1", "Equipment 1");
        Equipment equipment2 = createMockEquipment("2", "Equipment 2");
        List<Equipment> equipmentList = Arrays.asList(equipment1, equipment2);

        Mockito.when(equipmentService.getAllEquipment()).thenReturn(equipmentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(equipment1.getName())))
                .andExpect(jsonPath("$[1].name", is(equipment2.getName())));
    }

    @Test
    public void getEquipmentById_ShouldReturnEquipment() throws Exception {
        Equipment equipment = createMockEquipment("1", "Equipment 1");
        Mockito.when(equipmentService.getEquipmentById("1")).thenReturn(Optional.of(equipment));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(equipment.getName())));
    }

    @Test
    public void getEquipmentById_ShouldReturnNotFound() throws Exception {
        Mockito.when(equipmentService.getEquipmentById("99")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createEquipment_ShouldReturnCreatedEquipment() throws Exception {
        Equipment equipment = createMockEquipment("1", "New Equipment");
        Mockito.when(equipmentService.createEquipment(Mockito.any(Equipment.class))).thenReturn(equipment);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/equipment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equipment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(equipment.getName())));
    }

    @Test
    public void updateEquipment_ShouldReturnUpdatedEquipment() throws Exception {
        Equipment existingEquipment = createMockEquipment("1", "Existing Equipment");
        Equipment updatedEquipment = createMockEquipment("1", "Updated Equipment");

        Mockito.when(equipmentService.updateEquipment(Mockito.anyString(), Mockito.any(Equipment.class)))
                .thenReturn(updatedEquipment);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/equipment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEquipment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedEquipment.getName())));
    }

    @Test
    public void deleteEquipment_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/equipment/1"))
                .andExpect(status().isOk());

        Mockito.verify(equipmentService, Mockito.times(1)).deleteEquipment("1");
    }
}