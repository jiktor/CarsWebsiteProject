package com.cars.backend.Controllers;

import com.cars.backend.Models.Dto.CarAdvertDto;
import com.cars.backend.Services.CarAdvertsService;
import com.cars.backend.Services.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars-advert-website/viewAdverts")
public class CarAdvertsController {
	private final CarAdvertsService carAdvertsService;
	private final ModelsService modelsService;
	@Autowired
	public CarAdvertsController(CarAdvertsService carAdvertsService, ModelsService modelsService){
		this.carAdvertsService=carAdvertsService;
		this.modelsService = modelsService;
	}
	@GetMapping("/all")
	public List<CarAdvertDto> getAllAdevrts(){
		return carAdvertsService.getAllAdverts();
	}
}
