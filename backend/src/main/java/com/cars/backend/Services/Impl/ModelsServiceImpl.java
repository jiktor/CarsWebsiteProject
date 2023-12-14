package com.cars.backend.Services.Impl;

import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dao.Enums.Models;
import com.cars.backend.Models.Dao.ModelsDao;
import com.cars.backend.Repositories.BrandsRepository;
import com.cars.backend.Repositories.ModelsRepository;
import com.cars.backend.Services.BrandsService;
import com.cars.backend.Services.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelsServiceImpl implements ModelsService {
	private final ModelsRepository modelsRepository;

	private final BrandsService brandsService;
	@Autowired
	public ModelsServiceImpl(ModelsRepository modelsRepository, BrandsService brandsService){
		this.modelsRepository=modelsRepository;
		this.brandsService = brandsService;
	}
	@Override
	public List<Models> getModelsByBrand(Brands brand) {
		List<ModelsDao> modelsList = modelsRepository.getModelsByBrand(brandsService.getBrandDaoByBrand(brand));
		return modelsList.stream()
				.map(m -> m.getModel())
				.collect(Collectors.toList());
	}

}
