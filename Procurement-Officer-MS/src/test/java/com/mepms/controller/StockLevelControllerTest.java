package com.mepms.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.mepms.entity.StockLevel;
import com.mepms.service.StockLevelService;

@WebMvcTest(StockLevelController.class)
public class StockLevelControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockLevelService stockLevelService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected Object getController() {
        return new StockLevelController(stockLevelService);
    }

    private StockLevel createMockStockLevel(String id, String equipmentId) {
        StockLevel stockLevel = new StockLevel();
        stockLevel.setId(id);
        stockLevel.setEquipmentId(equipmentId);
        stockLevel.setEquipmentName("Equipment " + equipmentId);
        stockLevel.setCurrentStock(50);
        stockLevel.setMinRequired(20);
        stockLevel.setLastChecked(new Date());
        stockLevel.setStatus("OK");
        return stockLevel;
    }

    @Test
    public void getAllStockLevels_ShouldReturnAllStockLevels() throws Exception {
        StockLevel level1 = createMockStockLevel("1", "equip1");
        StockLevel level2 = createMockStockLevel("2", "equip2");
        List<StockLevel> levels = Arrays.asList(level1, level2);

        Mockito.when(stockLevelService.getAllStockLevels()).thenReturn(levels);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stock-levels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].equipmentId", is(level1.getEquipmentId())))
                .andExpect(jsonPath("$[1].equipmentId", is(level2.getEquipmentId())));
    }

    @Test
    public void getStockLevelsByEquipment_ShouldReturnLevels() throws Exception {
        StockLevel level1 = createMockStockLevel("1", "equip1");
        StockLevel level2 = createMockStockLevel("2", "equip1");
        List<StockLevel> levels = Arrays.asList(level1, level2);

        Mockito.when(stockLevelService.getStockLevelsByEquipmentId("equip1")).thenReturn(levels);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stock-levels/equipment/equip1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].equipmentId", is("equip1")))
                .andExpect(jsonPath("$[1].equipmentId", is("equip1")));
    }

    @Test
    public void createStockLevel_ShouldReturnCreatedLevel() throws Exception {
        StockLevel level = createMockStockLevel("1", "equip1");
        Mockito.when(stockLevelService.createStockLevel(Mockito.any(StockLevel.class))).thenReturn(level);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/stock-levels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(level)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId", is(level.getEquipmentId())));
    }

    @Test
    public void getBelowMinimumLevels_ShouldReturnLevels() throws Exception {
        StockLevel level1 = createMockStockLevel("1", "equip1");
        level1.setCurrentStock(5);
        level1.setMinRequired(10);
        List<StockLevel> levels = Arrays.asList(level1);

        Mockito.when(stockLevelService.getStockLevelsBelowMinimum()).thenReturn(levels);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stock-levels/alerts/below-minimum")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].currentStock", is(5)));
    }
}