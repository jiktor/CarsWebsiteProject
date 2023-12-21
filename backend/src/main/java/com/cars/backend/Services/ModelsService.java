package com.cars.backend.Services;

import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dao.Enums.Models;
import com.cars.backend.Models.Dao.ModelsDao;

import java.util.List;

public interface ModelsService {
	public List<Models> getModelsByBrand(Brands brand);
	public ModelsDao getModelsByModel(Models models);
}
