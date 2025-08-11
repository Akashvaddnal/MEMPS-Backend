package com.mepms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mepms.entity.Department;
import com.mepms.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Department createMockDepartment(String id, String name) {
        Department department = new Department();
        department.setId(id);
        department.setName(name);
        department.setLocation("Location " + id);
        department.setContactPerson("Contact " + id);
        department.setPhone("1234567890");
        department.setEmail(name.toLowerCase() + "@example.com");
        department.setCreatedAt(new Date());
        department.setUpdatedAt(new Date());
        department.setEquipmentInventory(new HashMap<>());
        return department;
    }

    @Test
    public void getAllDepartments_ShouldReturnAllDepartments() throws Exception {
        Department dept1 = createMockDepartment("1", "Department 1");
        Department dept2 = createMockDepartment("2", "Department 2");
        List<Department> departments = Arrays.asList(dept1, dept2);

        Mockito.when(departmentService.searchDepartments(null, null)).thenReturn(departments);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(dept1.getName())))
                .andExpect(jsonPath("$[1].name", is(dept2.getName())));
    }

    @Test
    public void getDepartmentById_ShouldReturnDepartment() throws Exception {
        Department department = createMockDepartment("1", "Department 1");
        Mockito.when(departmentService.getById("1")).thenReturn(Optional.of(department));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(department.getName())));
    }

    @Test
    public void getDepartmentById_ShouldReturnNotFound() throws Exception {
        Mockito.when(departmentService.getById("99")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/departments/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createDepartment_ShouldReturnCreatedDepartment() throws Exception {
        Department department = createMockDepartment("1", "New Department");
        Mockito.when(departmentService.createDepartment(Mockito.any(Department.class))).thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(department.getName())));
    }

    @Test
    public void updateDepartment_ShouldReturnUpdatedDepartment() throws Exception {
        Department department = createMockDepartment("1", "Updated Department");
        Mockito.when(departmentService.updateDepartment(Mockito.anyString(), Mockito.any(Department.class)))
                .thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(department.getName())));
    }

    @Test
    public void updateDepartment_ShouldReturnNotFound() throws Exception {
        Department department = createMockDepartment("99", "Non-existent Department");
        Mockito.when(departmentService.updateDepartment(Mockito.anyString(), Mockito.any(Department.class)))
                .thenThrow(new NoSuchElementException());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/departments/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void assignEquipmentToDepartment_ShouldReturnUpdatedDepartment() throws Exception {
        Department department = createMockDepartment("1", "Department 1");
        Mockito.when(departmentService.assignEquipmentToDepartment("1", "equip1"))
                .thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/departments/1/assign-equipment/equip1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(department.getName())));
    }

    @Test
    public void transferEquipmentUnit_ShouldReturnUpdatedDepartment() throws Exception {
        Department department = createMockDepartment("1", "Department 1");
        Mockito.when(departmentService.transferEquipmentUnit("equip1", "fromDept", "toDept"))
                .thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/departments/departments/fromDept/transfer/equip1/to/toDept")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(department.getName())));
    }
}