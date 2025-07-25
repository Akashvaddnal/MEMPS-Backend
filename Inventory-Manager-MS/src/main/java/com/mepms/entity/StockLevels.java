package com.mepms.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class StockLevels {
	@Id
	private String id;
	private String equipmentId;
	private int currentQuantity;
	private int minRequired;
	private String lastChecked;
	
}
