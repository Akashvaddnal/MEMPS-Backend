//package com.mepms.repository;
//
//import com.mepms.entity.StockLevels;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//public interface StockLevelsRepository extends MongoRepository<StockLevels, String> {
//    List<StockLevels> findByEquipmentId(String equipmentId);
//}
//

package com.mepms.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mepms.entity.StockLevels;

@Repository
public interface StockLevelsRepository extends MongoRepository<StockLevels, String> {
    // Add custom queries if needed
	List<StockLevels> findByEquipmentId(String equipmentId);
}
