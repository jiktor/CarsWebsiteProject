package com.cars.backend.Services;

import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dao.Enums.Models;

import java.util.List;

public interface ModelsService {
	public List<Models> getModelsByBrand(Brands brand);
}
