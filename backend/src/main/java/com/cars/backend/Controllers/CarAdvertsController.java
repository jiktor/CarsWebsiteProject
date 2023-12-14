package com.cars.backend.Controllers;

import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dao.Enums.Models;
import com.cars.backend.Models.Dto.CarAdvertDto;
import com.cars.backend.Services.CarAdvertsService;
import com.cars.backend.Services.Impl.ModelsServiceImpl;
import com.cars.backend.Services.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/cars-advert-website/createAdvert")
//@CrossOrigin(origins = "http://localhost:4200/cars-advert-website/createAdvert")
public class CarAdvertsController {
	private final  CarAdvertsService carAdvertsService;
	private final ModelsService modelsService;
	@Autowired
	public CarAdvertsController(CarAdvertsService carAdvertsService, ModelsService modelsService){
		this.carAdvertsService=carAdvertsService;
		this.modelsService = modelsService;
	}
	@GetMapping("/getBrands")
	public ResponseEntity<List<Brands>> getBrands(){
		return ResponseEntity.ok(carAdvertsService.getBrands());
	}
	@GetMapping("/getModels/{brand}")
	public ResponseEntity<List<Models>>getModels(
			@PathVariable String brand
			){
		return ResponseEntity.ok(modelsService.getModelsByBrand(Brands.valueOf(brand)));
	}
	@PostMapping("/save")
	public ResponseEntity createAdvert(@RequestBody CarAdvertDto carAdvertDto){
		carAdvertsService.saveCarAdvert(carAdvertDto);
		return ResponseEntity.ok("success");
	}
}
