package com.cars.backend.Controllers;

import com.cars.backend.Models.Dto.CarAdvertDto;
import com.cars.backend.Services.CarAdvertsService;
import com.cars.backend.Services.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@GetMapping("/getAdverts")
	public ResponseEntity<List<CarAdvertDto>> getAdvertsWithPagination(
			@RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
			@RequestParam(value = "sortField",required = false) String sortField,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			@RequestParam(value = "model",required = false) String model,
			@RequestParam(value = "brand",required = false) String brand,
			@RequestParam(value = "fromPrice",required = false) String fromPrice,
			@RequestParam(value = "toPrice",required = false) String toPrice,
			@RequestParam(value = "engine",required = false) String engine,
			@RequestParam(value = "dateOfManufacturing",required = false) String dateOfManufacturing){
		if(sortField == null && sortOrder == null ) {
			return ResponseEntity.ok(carAdvertsService.getAdvertsWithPagination(pageNumber, pageSize));
		} else{

			// метод от сървис за сортиране
			return ResponseEntity.ok(carAdvertsService.getAdvertsWithFiltrationAndPaginationAndSorting(
					pageNumber,
					pageSize,sortField,
					sortOrder,
					engine,
					brand,
					model,
					dateOfManufacturing,
					fromPrice,
					toPrice));
		}
	}
}
