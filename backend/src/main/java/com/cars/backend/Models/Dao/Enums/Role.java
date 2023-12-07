package com.cars.backend.Models.Dao.Enums;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum Role {
	@Enumerated(EnumType.STRING)
	USER,
	ADMIN
}
