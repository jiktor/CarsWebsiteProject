package com.cars.backend.Services;

import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dto.CarAdvertDto;

import java.util.List;

public interface CarAdvertsService {
	public List<Brands> getBrands();
	public void saveCarAdvert(CarAdvertDto advertDto);
}
