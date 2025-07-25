package com.mepms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.mepms.entity.PurchaseOrder;
import com.mepms.service.PurchaseOrderService;

@RestController
@RequestMapping("/api/purchase-orders")
@CrossOrigin(origins = "*")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public List<PurchaseOrder> getAll() {
        return purchaseOrderService.getAllPurchaseOrders();
    }

    @GetMapping("/{id}")
    public Optional<PurchaseOrder> getById(@PathVariable String id) {
        return purchaseOrderService.getPurchaseOrderById(id);
    }

    @GetMapping("/po-number/{poNumber}")
    public Optional<PurchaseOrder> getByPoNumber(@PathVariable String poNumber) {
        return purchaseOrderService.getPurchaseOrderByPoNumber(poNumber);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<PurchaseOrder> getByVendor(@PathVariable String vendorId) {
        return purchaseOrderService.getPurchaseOrdersByVendorId(vendorId);
    }

    @GetMapping("/status/{status}")
    public List<PurchaseOrder> getByStatus(@PathVariable String status) {
        return purchaseOrderService.getPurchaseOrdersByStatus(status);
    }

    @PostMapping
    public PurchaseOrder create(@RequestBody PurchaseOrder po) {
        return purchaseOrderService.createPurchaseOrder(po);
    }

    @PutMapping("/{id}")
    public PurchaseOrder update(@PathVariable String id, @RequestBody PurchaseOrder po) {
        return purchaseOrderService.updatePurchaseOrder(id, po);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        purchaseOrderService.deletePurchaseOrder(id);
    }

    // Example report endpoint
    @GetMapping("/reports/total-amount/vendor/{vendorId}")
    public Double getTotalAmountByVendor(@PathVariable String vendorId) {
        return purchaseOrderService.getTotalAmountByVendor(vendorId);
    }
}
