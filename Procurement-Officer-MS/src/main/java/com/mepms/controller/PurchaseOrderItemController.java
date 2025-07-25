package com.mepms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.mepms.entity.PurchaseOrderItem;
import com.mepms.service.PurchaseOrderItemService;

@RestController
@RequestMapping("/api/purchase-order-items")
public class PurchaseOrderItemController {

    private final PurchaseOrderItemService itemService;

    public PurchaseOrderItemController(PurchaseOrderItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<PurchaseOrderItem> getAll() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Optional<PurchaseOrderItem> getById(@PathVariable String id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/po/{poId}")
    public List<PurchaseOrderItem> getByPurchaseOrder(@PathVariable String poId) {
        return itemService.getItemsByPoId(poId);
    }

    @GetMapping("/equipment/{equipmentId}")
    public List<PurchaseOrderItem> getByEquipment(@PathVariable String equipmentId) {
        return itemService.getItemsByEquipmentId(equipmentId);
    }

    @PostMapping
    public PurchaseOrderItem create(@RequestBody PurchaseOrderItem item) {
        return itemService.createItem(item);
    }

    @PutMapping("/{id}")
    public PurchaseOrderItem update(@PathVariable String id, @RequestBody PurchaseOrderItem item) {
        return itemService.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        itemService.deleteItem(id);
    }
}
