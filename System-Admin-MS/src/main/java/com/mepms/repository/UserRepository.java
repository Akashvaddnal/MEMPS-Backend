package com.mepms.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mepms.entity.UserEO;

@Repository
public interface UserRepository extends MongoRepository<UserEO, String> {
	Optional<UserEO> findByEmail(String email);
}
