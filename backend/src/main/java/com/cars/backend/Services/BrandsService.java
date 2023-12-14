package com.cars.backend.Services;

import com.cars.backend.Models.Dao.BrandsDao;
import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dao.Enums.Models;

public interface BrandsService {
	public BrandsDao getBrandDaoByBrand(Brands brand);
}
