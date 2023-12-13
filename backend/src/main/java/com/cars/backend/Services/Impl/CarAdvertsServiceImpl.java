package com.cars.backend.Services.Impl;

import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Services.CarAdvertsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class CarAdvertsServiceImpl implements CarAdvertsService {
	@Override
	public List<Brands> getBrands() {
		return Arrays.stream(Brands.values()).toList();
	}
}
