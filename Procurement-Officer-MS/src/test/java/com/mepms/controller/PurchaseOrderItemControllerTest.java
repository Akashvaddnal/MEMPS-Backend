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
import com.mepms.entity.PurchaseOrderItem;
import com.mepms.service.PurchaseOrderItemService;

@WebMvcTest(PurchaseOrderItemController.class)
public class PurchaseOrderItemControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseOrderItemService itemService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected Object getController() {
        return new PurchaseOrderItemController(itemService);
    }

    private PurchaseOrderItem createMockItem(String id, String poId) {
        PurchaseOrderItem item = new PurchaseOrderItem();
        item.setId(id);
        item.setPoId(poId);
        item.setEquipmentId("equip1");
        item.setQuantity(2);
        item.setUnitPrice(500.0);
        item.setTotal(1000.0);
        return item;
    }

    @Test
    public void getAllItems_ShouldReturnAllItems() throws Exception {
        PurchaseOrderItem item1 = createMockItem("1", "po1");
        PurchaseOrderItem item2 = createMockItem("2", "po1");
        List<PurchaseOrderItem> items = Arrays.asList(item1, item2);

        Mockito.when(itemService.getAllItems()).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/purchase-order-items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].poId", is(item1.getPoId())))
                .andExpect(jsonPath("$[1].poId", is(item2.getPoId())));
    }

    @Test
    public void getItemById_ShouldReturnItem() throws Exception {
        PurchaseOrderItem item = createMockItem("1", "po1");
        Mockito.when(itemService.getItemById("1")).thenReturn(Optional.of(item));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/purchase-order-items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(item.getId())));
    }

    @Test
    public void getItemsByPurchaseOrder_ShouldReturnItems() throws Exception {
        PurchaseOrderItem item1 = createMockItem("1", "po1");
        PurchaseOrderItem item2 = createMockItem("2", "po1");
        List<PurchaseOrderItem> items = Arrays.asList(item1, item2);

        Mockito.when(itemService.getItemsByPoId("po1")).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/purchase-order-items/po/po1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].poId", is("po1")))
                .andExpect(jsonPath("$[1].poId", is("po1")));
    }

    @Test
    public void createItem_ShouldReturnCreatedItem() throws Exception {
        PurchaseOrderItem item = createMockItem("1", "po1");
        Mockito.when(itemService.createItemWithDuplicationCheck(Mockito.any(PurchaseOrderItem.class))).thenReturn(item);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/purchase-order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.poId", is(item.getPoId())));
    }

    @Test
    public void updateItem_ShouldReturnUpdatedItem() throws Exception {
        PurchaseOrderItem item = createMockItem("1", "po1");
        Mockito.when(itemService.updateItemWithDuplicationCheck(Mockito.anyString(), Mockito.any(PurchaseOrderItem.class)))
                .thenReturn(item);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/purchase-order-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(item.getId())));
    }
}