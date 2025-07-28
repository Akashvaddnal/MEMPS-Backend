//package com.mepms.entity;
//
//import org.springframework.data.annotation.Id;
//
//import lombok.Data;
//
//@Data
//public class StockLevels {
//	@Id
//	private String id;
//	private String equipmentId;
//	private int currentQuantity;
//	private int minRequired;
//	private String lastChecked;
//	
//}

package com.mepms.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "stock_levels")
public class StockLevels {
    @Id
    private String id;
    private String equipmentId;     // Store as String; convert to ObjectId in DB if needed
    private String equipmentName;
    private int currentStock;
    private int minRequired;
    private Date lastChecked;
    private Date lastUpdated;
    private String location;
    private String status;          // "Normal", "Low", "Critical"
}
