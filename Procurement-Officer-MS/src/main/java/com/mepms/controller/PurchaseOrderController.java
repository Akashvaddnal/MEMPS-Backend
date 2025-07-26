package com.mepms.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mepms.entity.PurchaseOrder;
import com.mepms.entity.PurchaseOrderItem;
import com.mepms.repository.PurchaseOrderItemRepository;
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
    
    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

//    @PostMapping
//    public PurchaseOrder create(@RequestBody PurchaseOrder po) {
//    	if (po.getId() == null || po.getId().equals("undefined")) {
//			// Generate a new ID if not provided
//    		po.setId(new ObjectId().toString());
//        }
//        return purchaseOrderService.createPurchaseOrder(po);
//    	 // Save the main order first
//        PurchaseOrder savedOrder = purchaseOrderService.createPurchaseOrder(po);
//        
//        // Then save items with reference to the order
//        if (po.getItems() != null) {
//            for (PurchaseOrderItem item : po.getItems()) {
//                item.setPoId(savedOrder.getId());
//                if (item.getId() == null) {
//                    item.setId(new ObjectId().toString());
//                }
//                purchaseOrderItemRepository.save(item);
//            }
//        }
//        
//        return savedOrder;
//    }
    
    @PostMapping
    public PurchaseOrder create(@RequestBody PurchaseOrder po) {
        // Generate new ID if needed
    	System.err.println("Received Purchase Order Id: " + po.getId());
        if (po.getId() == null || po.getId().equals("undefined")) {
            po.setId(new ObjectId().toString());
            System.err.println("Generated new Purchase Order Id: " + po.getId());
        }
        
        // Generate PO number if not provided
        if (po.getPoNumber() == null || po.getPoNumber().isEmpty()) {
            po.setPoNumber(generatePONumber());
        }
        
        // Save the order
        PurchaseOrder savedOrder = purchaseOrderService.createPurchaseOrder(po);
        
        // Process items
        if (po.getItems() != null) {
            List<PurchaseOrderItem> savedItems = new ArrayList<>();
            for (PurchaseOrderItem item : po.getItems()) {
                item.setPoId(savedOrder.getId());
                if (item.getId() == null || item.getId().equals("undefined")) {
                    item.setId(new ObjectId().toString());
                }
                savedItems.add(purchaseOrderItemRepository.save(item));
            }
            savedOrder.setItems(savedItems);
        }
        
        return savedOrder;
    }

    private String generatePONumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        int randomNum = new Random().nextInt(1000);
        return String.format("PO-%s-%03d", date, randomNum);
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
