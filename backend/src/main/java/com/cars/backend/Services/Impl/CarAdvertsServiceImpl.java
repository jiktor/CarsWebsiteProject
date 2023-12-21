package com.cars.backend.Services.Impl;

import com.cars.backend.Models.Dao.CarAdvertsDao;
import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dao.Enums.Models;
import com.cars.backend.Models.Dto.CarAdvertDto;
import com.cars.backend.Repositories.CarAdvertsRepository;
import com.cars.backend.Services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class CarAdvertsServiceImpl implements CarAdvertsService {
	private final CarAdvertsRepository carAdvertsRepository;
	private final ModelsService modelsService;
	private final BrandsService brandsService;
	private final UsreService usreService;
	private final JwtService jwtService;
	private final ModelMapper mapper;
	@Autowired
	public CarAdvertsServiceImpl(CarAdvertsRepository carAdvertsRepository, ModelsService modelsService, BrandsService brandsService, UsreService usreService, JwtService jwtService, ModelMapper mapper){
		this.carAdvertsRepository = carAdvertsRepository;
		this.modelsService = modelsService;
		this.brandsService = brandsService;
		this.usreService = usreService;
		this.jwtService = jwtService;
		this.mapper = mapper;
	}
	@Override
	public List<Brands> getBrands() {
		return Arrays.stream(Brands.values()).toList();
	}

	@Override
	public void saveCarAdvert(CarAdvertDto advertDto,
							  @NonNull HttpServletRequest request) {
		CarAdvertsDao carAdvertsDao = mapper.map(advertDto, CarAdvertsDao.class);
		carAdvertsDao.setDescription(advertDto.getDescription());
		carAdvertsDao.setModel(modelsService.getModelsByModel(Models.valueOf(advertDto.getModel())));
		carAdvertsDao.setOwner(usreService.findUserByEmail(jwtService.extractUsername(request.getHeader("Authorization").substring(7))));
		carAdvertsRepository.save(carAdvertsDao);
	}
}
