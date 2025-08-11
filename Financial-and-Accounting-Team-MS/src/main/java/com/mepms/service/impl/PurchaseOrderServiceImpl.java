package com.mepms.service.impl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mepms.entity.PurchaseOrder;
import com.mepms.repository.PurchaseOrderRepository;
import com.mepms.service.PurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepo;

    @Override
    public PurchaseOrder create(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepo.save(purchaseOrder);
    }

    @Override
    public PurchaseOrder update(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepo.save(purchaseOrder);
    }

    @Override
    public void deleteById(String id) {
        purchaseOrderRepo.deleteById(id);
    }

    @Override
    public PurchaseOrder getById(String id) {
        return purchaseOrderRepo.findById(id).orElse(null);
    }

    @Override
    public PurchaseOrder getByPoNumber(String poNumber) {
        return purchaseOrderRepo.findByPoNumber(poNumber);
    }

    @Override
    public List<PurchaseOrder> getAll() {
        return purchaseOrderRepo.findAll();
    }

    @Override
    public List<PurchaseOrder> getByStatus(String status) {
        return purchaseOrderRepo.findByStatus(status);
    }

    @Override
    public List<PurchaseOrder> getByVendorId(String vendorId) {
        return purchaseOrderRepo.findByVendorId(vendorId);
    }

    @Override
    public List<PurchaseOrder> getByDateIssuedBetween(LocalDateTime start, LocalDateTime end) {
        return purchaseOrderRepo.findByDateIssuedBetween(start, end);
    }

    @Override
    public List<PurchaseOrder> getByPriority(String priority) {
        return purchaseOrderRepo.findByPriority(priority);
    }

    @Override
    public List<PurchaseOrder> getByRequestedBy(String requester) {
        return purchaseOrderRepo.findByRequestedBy(requester);
    }

    @Override
    public PurchaseOrder addItemToPurchaseOrder(String purchaseOrderId, PurchaseOrder.POItem newItem) {
        Optional<PurchaseOrder> opt = purchaseOrderRepo.findById(purchaseOrderId);
        if (opt.isPresent()) {
            PurchaseOrder po = opt.get();
            if (po.getItems() == null) {
                po.setItems(new ArrayList<>());
            }
            po.getItems().add(newItem);
            po.setTotalAmount(po.getTotalAmount() + newItem.getTotal());
            return purchaseOrderRepo.save(po);
        }
        return null;
    }

    @Override
    public PurchaseOrder updateItemInPurchaseOrder(String purchaseOrderId, String itemId, PurchaseOrder.POItem updatedItem) {
        Optional<PurchaseOrder> opt = purchaseOrderRepo.findById(purchaseOrderId);
        if (opt.isPresent()) {
            PurchaseOrder po = opt.get();
            List<PurchaseOrder.POItem> items = po.getItems();
            if (items != null) {
                double totalAmount = po.getTotalAmount() - 
                    items.stream()
                         .filter(it -> it.getEquipmentId() != null && it.getEquipmentId().equals(itemId))
                         .mapToDouble(PurchaseOrder.POItem::getTotal)
                         .sum();
                for (int i = 0; i < items.size(); i++) {
                    PurchaseOrder.POItem item = items.get(i);
                    if (item.getEquipmentId() != null && item.getEquipmentId().equals(itemId)) {
                        items.set(i, updatedItem);
                    }
                }
                totalAmount += updatedItem.getTotal();
                po.setItems(items);
                po.setTotalAmount(totalAmount);
                return purchaseOrderRepo.save(po);
            }
        }
        return null;
    }

    @Override
    public PurchaseOrder removeItemFromPurchaseOrder(String purchaseOrderId, String itemId) {
        Optional<PurchaseOrder> opt = purchaseOrderRepo.findById(purchaseOrderId);
        if (opt.isPresent()) {
            PurchaseOrder po = opt.get();
            List<PurchaseOrder.POItem> items = po.getItems();
            if (items != null) {
                double newTotalAmount = po.getTotalAmount() -
                        items.stream()
                             .filter(it -> it.getEquipmentId() != null && it.getEquipmentId().equals(itemId))
                             .mapToDouble(PurchaseOrder.POItem::getTotal)
                             .sum();
                items.removeIf(it -> it.getEquipmentId() != null && it.getEquipmentId().equals(itemId));
                po.setItems(items);
                po.setTotalAmount(newTotalAmount);
                return purchaseOrderRepo.save(po);
            }
        }
        return null;
    }

}
