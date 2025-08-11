package com.mepms.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        
        po.setPoNumber(generatePONumber());
        
        
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
                savedItems.add(item);
            }
            savedOrder.setItems(savedItems);
        }
        
        return savedOrder;
    }

    private String generatePONumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(new Date());
//    	String date=Instant.now().toString();
        int randomNum = new Random().nextInt(1000);
        return String.format("PO-%s-%03d", date, randomNum);
    }
    

//    @PutMapping("/{id}")
//    public PurchaseOrder update(@PathVariable String id, @RequestBody PurchaseOrder po) {
//    	if(getById(id).isEmpty()) {
//			return create(po);
//		}
//        return purchaseOrderService.updatePurchaseOrder(id, po);
//    }
    
//    @PutMapping("/{id}")
//    public PurchaseOrder update(@PathVariable String id, @RequestBody PurchaseOrder po) {
//        // Check if PO exists
//        Optional<PurchaseOrder> existingOpt = getById(id);
//        if (existingOpt.isEmpty()) {
//            // If PO does not exist, create new
//            return create(po);
//        }
//
//        PurchaseOrder existing = existingOpt.get();
//
//        // Update all fields carefully
//        updatePurchaseOrderFields(existing, po);
//
//        // Save updated PO
//        return purchaseOrderService.updatePurchaseOrder(id, existing);
//    }
//
//    /**
//     * Helper method to update all relevant fields of a PurchaseOrder entity from another.
//     * Handles the items list properly to avoid duplication.
//     */
//    private void updatePurchaseOrderFields(PurchaseOrder existing, PurchaseOrder incoming) {
//        if (incoming.getPoNumber() != null) {
//            existing.setPoNumber(incoming.getPoNumber());
//        }
//
//        if (incoming.getDateIssued() != null) {
//            existing.setDateIssued(incoming.getDateIssued());
//        }
//
//        if (incoming.getStatus() != null) {
//            existing.setStatus(incoming.getStatus());
//        }
//
//        if (incoming.getVendorId() != null) {
//            existing.setVendorId(incoming.getVendorId());
//        }
//
//        if (incoming.getRequestedBy() != null) {
//            existing.setRequestedBy(incoming.getRequestedBy());
//        }
//
//        if (incoming.getApprovedBy() != null) {
//            existing.setApprovedBy(incoming.getApprovedBy());
//        }
//
//        if (incoming.getTotalAmount() != null) {
//            existing.setTotalAmount(incoming.getTotalAmount());
//        }
//
//        if (incoming.getDeliveryDate() != null) {
//            existing.setDeliveryDate(incoming.getDeliveryDate());
//        }
//
//        if (incoming.getPaymentStatus() != null) {
//            existing.setPaymentStatus(incoming.getPaymentStatus());
//        }
//
//        if (incoming.getPriority() != null) {
//            existing.setPriority(incoming.getPriority());
//        }
//
//        if (incoming.getNotes() != null) {
//            existing.setNotes(incoming.getNotes());
//        }
//
//        if (incoming.getItems() != null) {
//            // If items present in request, replace existing items completely
//            // Alternative: implement merge logic if partial update desired
//            existing.setItems(incoming.getItems());
//        }
//    }

    @PutMapping("/{id}")
    public PurchaseOrder update(@PathVariable String id, @RequestBody PurchaseOrder incomingPO) {
    	
    	System.err.println("id :"+id);
    	System.err.println(incomingPO);
        PurchaseOrder existingPO = purchaseOrderService.getPurchaseOrderById(id).orElse(null);

        // Update basic fields only
        existingPO.setStatus(incomingPO.getStatus());
        existingPO.setTotalAmount(incomingPO.getTotalAmount());
        existingPO.setDeliveryDate(incomingPO.getDeliveryDate());
        existingPO.setNotes(incomingPO.getNotes());
        // ... other simple fields as needed

        purchaseOrderService.createPurchaseOrder(existingPO);

        List<PurchaseOrderItem> incomingItems = incomingPO.getItems();

        if (incomingItems != null) {
            // Fetch current items from DB
            List<PurchaseOrderItem> existingItems = purchaseOrderItemRepository.findByPoId(id);

            Map<String, PurchaseOrderItem> existingItemsMap = existingItems.stream()
                .collect(Collectors.toMap(PurchaseOrderItem::getId, Function.identity()));

            // Process incoming items
            for (PurchaseOrderItem incomingItem : incomingItems) {
                if (incomingItem.getId() != null && existingItemsMap.containsKey(incomingItem.getId())) {
                    // Update existing item
                    PurchaseOrderItem existingItem = existingItemsMap.get(incomingItem.getId());
                    existingItem.setQuantity(incomingItem.getQuantity());
                    existingItem.setUnitPrice(incomingItem.getUnitPrice());
                    existingItem.setTotal(incomingItem.getTotal());
                    purchaseOrderItemRepository.save(existingItem);
                    existingItemsMap.remove(incomingItem.getId());
                } else {
                    // New item
                    incomingItem.setPoId(id);
                    purchaseOrderItemRepository.save(incomingItem);
                }
            }

            // Delete items no longer present
            existingItemsMap.values().forEach(item -> purchaseOrderItemRepository.deleteById(item.getId()));
        }

        // Optionally reload and return updated PO with fresh items
        return purchaseOrderService.getPurchaseOrderById(id).orElse(existingPO);
    }

    
    
    @PutMapping("/purchase-orders/{id}")
    public PurchaseOrder updateStatus(@PathVariable String id, @RequestBody PurchaseOrder updateData) {
        PurchaseOrder existing = purchaseOrderService.getPurchaseOrderById(id).orElse(null);
        if (updateData.getStatus() != null) {
            existing.setStatus(updateData.getStatus());
        }
//        // Only update items if really needed:
//        if (updateData.getItems() != null && !updateData.getItems().isEmpty()) {
//            existing.setItems(updateData.getItems());
//        }
        // ... update other fields as required
        return purchaseOrderService.createPurchaseOrder(existing);
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
