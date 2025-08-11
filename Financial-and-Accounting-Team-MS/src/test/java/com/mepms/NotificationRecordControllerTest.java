package com.mepms;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mepms.controllers.NotificationRecordController;
import com.mepms.entity.NotificationRecord;
import com.mepms.service.NotificationService;

@WebMvcTest(NotificationRecordController.class)
public class NotificationRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

//    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private NotificationRecord createMockNotification(String id) {
        NotificationRecord notification = new NotificationRecord();
        notification.setId(id);
        notification.setUserId("user1");
        notification.setTitle("Test Notification");
        notification.setMessage("This is a test notification");
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setType("SYSTEM");
        return notification;
    }

    @Test
    public void createNotification_ShouldReturnCreatedNotification() throws Exception {
        NotificationRecord notification = createMockNotification("1");
        Mockito.when(notificationService.create(Mockito.any(NotificationRecord.class))).thenReturn(notification);

        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notification)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(notification.getTitle())));
    }

    @Test
    public void getAllNotifications_ShouldReturnAllNotifications() throws Exception {
        NotificationRecord notification1 = createMockNotification("1");
        NotificationRecord notification2 = createMockNotification("2");
        List<NotificationRecord> notifications = Arrays.asList(notification1, notification2);

        Mockito.when(notificationService.getAll()).thenReturn(notifications);

        mockMvc.perform(get("/api/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(notification1.getId())))
                .andExpect(jsonPath("$[1].id", is(notification2.getId())));
    }

    @Test
    public void getNotificationById_ShouldReturnNotification() throws Exception {
        NotificationRecord notification = createMockNotification("1");
        Mockito.when(notificationService.getById("1")).thenReturn(notification);

        mockMvc.perform(get("/api/notifications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notification.getId())));
    }

    @Test
    public void getNotificationsByUserId_ShouldReturnNotifications() throws Exception {
        NotificationRecord notification1 = createMockNotification("1");
        NotificationRecord notification2 = createMockNotification("2");
        List<NotificationRecord> notifications = Arrays.asList(notification1, notification2);

        Mockito.when(notificationService.getByUserId("user1")).thenReturn(notifications);

        mockMvc.perform(get("/api/notifications/user/user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userId", is("user1")));
    }

    @Test
    public void markNotificationAsRead_ShouldReturnOk() throws Exception {
        mockMvc.perform(patch("/api/notifications/1/read"))
                .andExpect(status().isOk());

        Mockito.verify(notificationService, Mockito.times(1)).markAsRead("1");
    }
}