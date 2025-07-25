package com.mepms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mepms.entity.PurchaseOrder;
import com.mepms.repository.PurchaseOrderRepository;
import com.mepms.service.PurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    public Optional<PurchaseOrder> getPurchaseOrderById(String id) {
        return purchaseOrderRepository.findById(id);
    }

    @Override
    public Optional<PurchaseOrder> getPurchaseOrderByPoNumber(String poNumber) {
        return purchaseOrderRepository.findByPoNumber(poNumber);
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrdersByVendorId(String vendorId) {
        return purchaseOrderRepository.findByVendorId(vendorId);
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrdersByStatus(String status) {
        return purchaseOrderRepository.findByStatus(status);
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrdersByDateRange(Date start, Date end) {
        // For date range search you might need a custom query in repository
        throw new UnsupportedOperationException("Date range search not implemented");
    }

    @Override
    public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public PurchaseOrder updatePurchaseOrder(String id, PurchaseOrder purchaseOrder) {
        purchaseOrder.setId(id);
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public void deletePurchaseOrder(String id) {
        purchaseOrderRepository.deleteById(id);
    }

    @Override
    public Double getTotalAmountByVendor(String vendorId) {
        List<PurchaseOrder> orders = purchaseOrderRepository.findByVendorId(vendorId);
        return orders.stream()
            .mapToDouble(order -> order.getTotalAmount() != null ? order.getTotalAmount() : 0)
            .sum();
    }
}
