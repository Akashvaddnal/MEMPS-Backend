package com.mepms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "roles")
public class RoleEO {
	
	@Id
	private String id;
	

	@NotBlank(message = "Role Name is Required")
	@Size(min = 3, max = 30, message = "RoleName must be between 4 and 30 characters")
	private String roleName;
	
	@NotBlank(message = "Role Description is Required")
	@Size(min = 5, max = 100, message = "Role Description must be between 5 and 100 characters")
	private String description;
}
