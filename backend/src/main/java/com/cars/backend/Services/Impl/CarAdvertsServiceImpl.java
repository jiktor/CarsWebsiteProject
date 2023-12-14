package com.cars.backend.Services.Impl;

import com.cars.backend.Models.Dao.CarAdvertsDao;
import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dto.CarAdvertDto;
import com.cars.backend.Repositories.CarAdvertsRepository;
import com.cars.backend.Services.CarAdvertsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class CarAdvertsServiceImpl implements CarAdvertsService {
	private final CarAdvertsRepository carAdvertsRepository;
	private final ModelMapper mapper;
	@Autowired
	public CarAdvertsServiceImpl(CarAdvertsRepository carAdvertsRepository, ModelMapper mapper){
		this.carAdvertsRepository = carAdvertsRepository;
		this.mapper = mapper;
	}
	@Override
	public List<Brands> getBrands() {
		return Arrays.stream(Brands.values()).toList();
	}

	@Override
	public void saveCarAdvert(CarAdvertDto advertDto) {
		CarAdvertsDao carAdvertsDao = mapper.map(advertDto, CarAdvertsDao.class);
		carAdvertsRepository.save(carAdvertsDao);
	}
}
