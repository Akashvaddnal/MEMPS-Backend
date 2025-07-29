package com.mepms.controllers;

import com.mepms.entity.UserEO;
import com.mepms.service.AuditLogService;
import com.mepms.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuditLogService auditLogService;

    @Test
    public void testGetAllUsers() throws Exception {
        UserEO user = new UserEO();
        user.setId("user1");
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
        user.setDepartment("IT");
        user.setRoleName("Admin");
        user.setActive(true);

        Mockito.when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/api/users")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("user1")))
                .andExpect(jsonPath("$[0].username", is("testuser")));

        Mockito.verify(userService).getAllUsers();
    }

    @Test
    public void testGetUserById_Found() throws Exception {
        UserEO user = new UserEO();
        user.setId("user1");
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
        user.setDepartment("IT");
        user.setRoleName("Admin");
        user.setActive(true);

        Mockito.when(userService.getUserById("user1")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/user1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("user1")))
                .andExpect(jsonPath("$.username", is("testuser")));

        Mockito.verify(userService).getUserById("user1");
    }

    @Test
    public void testGetUserById_NotFound() throws Exception {
        Mockito.when(userService.getUserById("unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/unknown")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        Mockito.verify(userService).getUserById("unknown");
    }

    @Test
    public void testCreateUser() throws Exception {
        // Include required validation fields in JSON payload
        String userJson = """
            {
                "username": "testuser",
                "password": "password123",
                "email": "testuser@example.com",
                "department": "IT",
                "roleName": "Admin",
                "active": true
            }
        """;

        UserEO savedUser = new UserEO();
        savedUser.setId("user1");
        savedUser.setUsername("testuser");
        savedUser.setEmail("testuser@example.com");
        savedUser.setDepartment("IT");
        savedUser.setRoleName("Admin");
        savedUser.setActive(true);

        Mockito.when(userService.createUser(Mockito.any(UserEO.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("user1")))
                .andExpect(jsonPath("$.username", is("testuser")));

        Mockito.verify(userService).createUser(Mockito.any(UserEO.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Omit id from JSON body because it's path param
        String userJson = """
            {
                "username": "updateduser",
                "password": "newpassword123",
                "email": "updateduser@example.com",
                "department": "HR",
                "roleName": "User",
                "active": false
            }
        """;

        UserEO updatedUser = new UserEO();
        updatedUser.setId("user1");
        updatedUser.setUsername("updateduser");
        updatedUser.setEmail("updateduser@example.com");
        updatedUser.setDepartment("HR");
        updatedUser.setRoleName("User");
        updatedUser.setActive(false);

        Mockito.when(userService.updateUser(Mockito.eq("user1"), Mockito.any(UserEO.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/user1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("user1")))
                .andExpect(jsonPath("$.username", is("updateduser")))
                .andExpect(jsonPath("$.email", is("updateduser@example.com")))
                .andExpect(jsonPath("$.department", is("HR")))
                .andExpect(jsonPath("$.roleName", is("User")))
                .andExpect(jsonPath("$.active", is(false)));

        Mockito.verify(userService).updateUser(Mockito.eq("user1"), Mockito.any(UserEO.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Mockito.doNothing().when(userService).deleteUser("user1");

        mockMvc.perform(delete("/api/users/user1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        Mockito.verify(userService).deleteUser("user1");
    }
}
