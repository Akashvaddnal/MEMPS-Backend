package com.mepms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@ExtendWith(MockitoExtension.class)
public abstract class BaseControllerTest {
    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(getController())
                .build();
    }
    
    protected ObjectMapper objectMapper() {
        return new ObjectMapper()
            .findAndRegisterModules()  // Automatically finds all modules including JSR-310
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    protected abstract Object getController();
}