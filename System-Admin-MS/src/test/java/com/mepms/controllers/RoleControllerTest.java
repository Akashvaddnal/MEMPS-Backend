package com.mepms.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mepms.entity.RoleEO;
import com.mepms.service.RoleService;

@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Test
    public void testGetAllRoles() throws Exception {
        RoleEO role = new RoleEO();
        role.setId("role1");
        role.setRoleName("Admin");

        Mockito.when(roleService.getAllRoles()).thenReturn(Collections.singletonList(role));

        mockMvc.perform(get("/api/roles")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("role1")))
                .andExpect(jsonPath("$[0].roleName", is("Admin")));


        Mockito.verify(roleService).getAllRoles();
    }

    @Test
    public void testGetRoleById_Found() throws Exception {
        RoleEO role = new RoleEO();
        role.setId("role1");
        role.setRoleName("Admin");

        Mockito.when(roleService.getRoleById("role1")).thenReturn(Optional.of(role));

        mockMvc.perform(get("/api/roles/role1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("role1")))
                .andExpect(jsonPath("$.roleName", is("Admin")));


        Mockito.verify(roleService).getRoleById("role1");
    }

    @Test
    public void testGetRoleById_NotFound() throws Exception {
        Mockito.when(roleService.getRoleById("unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/roles/unknown")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(roleService).getRoleById("unknown");
    }

    @Test
    public void testCreateRole() throws Exception {
        String roleJson = "{ \"id\": \"role1\", \"roleName\": \"Admin\", \"description\": \"Admin role description\" }";

        RoleEO savedRole = new RoleEO();
        savedRole.setId("role1");
        savedRole.setRoleName("Admin");
        savedRole.setDescription("Admin role description");

        Mockito.when(roleService.createRole(Mockito.any(RoleEO.class))).thenReturn(savedRole);

        mockMvc.perform(post("/api/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(roleJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("role1")))
                .andExpect(jsonPath("$.roleName", is("Admin")))
                .andExpect(jsonPath("$.description", is("Admin role description")));

        Mockito.verify(roleService).createRole(Mockito.any(RoleEO.class));
    }


    @Test
    public void testUpdateRole() throws Exception {
        // Include description field here
        String roleJson = "{ \"roleName\": \"User\", \"description\": \"User role description\" }";

        RoleEO updatedRole = new RoleEO();
        updatedRole.setId("role1");
        updatedRole.setRoleName("User");
        updatedRole.setDescription("User role description");

        Mockito.when(roleService.updateRole(Mockito.eq("role1"), Mockito.any(RoleEO.class))).thenReturn(updatedRole);

        mockMvc.perform(put("/api/roles/role1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(roleJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("role1")))
                .andExpect(jsonPath("$.roleName", is("User")))
                .andExpect(jsonPath("$.description", is("User role description")));

        Mockito.verify(roleService).updateRole(Mockito.eq("role1"), Mockito.any(RoleEO.class));
    }

    @Test
    public void testDeleteRole() throws Exception {
        Mockito.doNothing().when(roleService).deleteRoleId("role1");

        mockMvc.perform(delete("/api/roles/role1"))
                .andExpect(status().isNoContent());

        Mockito.verify(roleService).deleteRoleId("role1");
    }
}
