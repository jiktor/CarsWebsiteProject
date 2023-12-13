package com.cars.backend.Controllers;

import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Services.CarAdvertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars-advert-website/createAdvert")
@CrossOrigin(origins = "http://localhost:4200")
public class CarAdvertsController {
	private CarAdvertsService carAdvertsService;
	@Autowired
	public CarAdvertsController(CarAdvertsService carAdvertsService){
		this.carAdvertsService=carAdvertsService;
	}
	@GetMapping("/getBrands")
	public ResponseEntity<List<Brands>> getBrands(){
		return ResponseEntity.ok(carAdvertsService.getBrands());
	}
}
