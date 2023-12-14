package com.cars.backend.Services.Impl;

import com.cars.backend.Models.Dao.BrandsDao;
import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Repositories.BrandsRepository;
import com.cars.backend.Services.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandsServiceImpl implements BrandsService {
	private final BrandsRepository brandsRepository;
	@Autowired
	public BrandsServiceImpl(BrandsRepository brandsRepository){
		this.brandsRepository=brandsRepository;
	}
	@Override
	public BrandsDao getBrandDaoByBrand(Brands brand) {
		return brandsRepository.getBrandByBrand(brand);
	}
}
