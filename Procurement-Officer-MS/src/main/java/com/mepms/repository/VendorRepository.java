package com.mepms.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.mepms.entity.Vendor;

public interface VendorRepository extends MongoRepository<Vendor, String> {
    Optional<Vendor> findByEmail(String email);
    Optional<Vendor> findByName(String name);
}
