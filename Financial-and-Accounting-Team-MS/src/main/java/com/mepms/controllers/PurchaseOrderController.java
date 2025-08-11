package com.mepms.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mepms.entity.PurchaseOrder;
import com.mepms.service.PurchaseOrderService;

@RestController
@RequestMapping("/api/purchase-orders")
@CrossOrigin
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping
    public PurchaseOrder create(@RequestBody PurchaseOrder purchaseOrder) {
        return purchaseOrderService.create(purchaseOrder);
    }

    @GetMapping
    public List<PurchaseOrder> getAll() {
        return purchaseOrderService.getAll();
    }

    @GetMapping("/{id}")
    public PurchaseOrder getById(@PathVariable String id) {
        return purchaseOrderService.getById(id);
    }

    @GetMapping("/number/{poNumber}")
    public PurchaseOrder getByNumber(@PathVariable String poNumber) {
        return purchaseOrderService.getByPoNumber(poNumber);
    }

    @GetMapping("/status/{status}")
    public List<PurchaseOrder> getByStatus(@PathVariable String status) {
        return purchaseOrderService.getByStatus(status);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<PurchaseOrder> getByVendor(@PathVariable String vendorId) {
        return purchaseOrderService.getByVendorId(vendorId);
    }

    @GetMapping("/date")
    public List<PurchaseOrder> getByDateRange(@RequestParam String start, @RequestParam String end) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(start, formatter);
            LocalDate endDate = LocalDate.parse(end, formatter);

            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

            return purchaseOrderService.getByDateIssuedBetween(startDateTime, endDateTime);
        } catch (Exception e) {
            return List.of();
        }
    }

    @GetMapping("/priority/{priority}")
    public List<PurchaseOrder> getByPriority(@PathVariable String priority) {
        return purchaseOrderService.getByPriority(priority);
    }

    @GetMapping("/requester/{requester}")
    public List<PurchaseOrder> getByRequester(@PathVariable String requester) {
        return purchaseOrderService.getByRequestedBy(requester);
    }

    @PutMapping("/{id}")
    public PurchaseOrder update(@PathVariable String id, @RequestBody PurchaseOrder purchaseOrder) {
        purchaseOrder.setId(id);
        return purchaseOrderService.update(purchaseOrder);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        purchaseOrderService.deleteById(id);
    }

    // Add item to purchase order
    @PostMapping("/{id}/items")
    public PurchaseOrder addItem(@PathVariable String id, @RequestBody PurchaseOrder.POItem item) {
        return purchaseOrderService.addItemToPurchaseOrder(id, item);
    }

    // Update item by item Id
    @PutMapping("/{orderId}/items/{itemId}")
    public PurchaseOrder updateItem(@PathVariable String orderId, @PathVariable String itemId, @RequestBody PurchaseOrder.POItem item) {
        return purchaseOrderService.updateItemInPurchaseOrder(orderId, itemId, item);
    }

    // Remove item from purchase order
    @DeleteMapping("/{orderId}/items/{itemId}")
    public PurchaseOrder removeItem(@PathVariable String orderId, @PathVariable String itemId) {
        return purchaseOrderService.removeItemFromPurchaseOrder(orderId, itemId);
    }
}
