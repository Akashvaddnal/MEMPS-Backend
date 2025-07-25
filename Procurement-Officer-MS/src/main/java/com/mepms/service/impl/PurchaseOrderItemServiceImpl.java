package com.mepms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mepms.entity.PurchaseOrderItem;
import com.mepms.repository.PurchaseOrderItemRepository;
import com.mepms.service.PurchaseOrderItemService;

@Service
public class PurchaseOrderItemServiceImpl implements PurchaseOrderItemService {

    private final PurchaseOrderItemRepository itemRepository;

    public PurchaseOrderItemServiceImpl(PurchaseOrderItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<PurchaseOrderItem> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Optional<PurchaseOrderItem> getItemById(String id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<PurchaseOrderItem> getItemsByPoId(String poId) {
        return itemRepository.findByPoId(poId);
    }

    @Override
    public List<PurchaseOrderItem> getItemsByEquipmentId(String equipmentId) {
        return itemRepository.findByEquipmentId(equipmentId);
    }

    @Override
    public PurchaseOrderItem createItem(PurchaseOrderItem item) {
        return itemRepository.save(item);
    }

    @Override
    public PurchaseOrderItem updateItem(String id, PurchaseOrderItem item) {
        item.setId(id);
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }
}
