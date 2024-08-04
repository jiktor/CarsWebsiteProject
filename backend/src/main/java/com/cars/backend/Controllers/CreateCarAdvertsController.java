package com.cars.backend.Controllers;

import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dao.Enums.Models;
import com.cars.backend.Models.Dto.CarAdvertDto;
import com.cars.backend.Services.CarAdvertsService;
import com.cars.backend.Services.ModelsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/cars-advert-website/createAdvert")
public class CreateCarAdvertsController {
	private final  CarAdvertsService carAdvertsService;
	private final ModelsService modelsService;
	@Autowired
	public CreateCarAdvertsController(CarAdvertsService carAdvertsService, ModelsService modelsService){
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

	@PostMapping("/saveAdvert")
	public ResponseEntity saveAdvert(@RequestParam("model") String model,
									   @RequestParam("brand") String brand,
									   @RequestParam("price") String price,
									   @RequestParam("engine") String engine,
									   @RequestParam("dateOfManufacturing") String dateOfManufacturing,
									   @RequestParam("description") String description,
									   @RequestParam(value = "images",required = false) MultipartFile[] images,
									   @RequestParam(value = "mileage", required = false) String milage,
									   @RequestParam(value = "horsePower", required = false) String horsePower,
									   @RequestParam(value = "gearbox", required = false) String gearbox,
									   @RequestParam(value = "color",required = false) String color,
									   @RequestParam(value = "engineType", required = false) String engineType,
									   @RequestParam(value = "euroEmissions", required = false) String euroEmissions,
									   @RequestParam(value = "locationOfTheCar", required = false) String locationOfTheCar,
									   @NonNull HttpServletRequest request){
		CarAdvertDto carAdvertDto = null;
		try {
			carAdvertDto = new CarAdvertDto()
					.setBrand(brand)
					.setModel(model)
					.setDateOfManufacturing(Date.valueOf(dateOfManufacturing))
					.setDescription(description)
					.setEngine(engine)
					.setPrice(Float.valueOf(price))
					.setMileage(milage)
					.setHorsePower(horsePower)
					.setGearbox(gearbox)
					.setColor(color)
					.setEngineType(engineType)
					.setEuroEmmissions(euroEmissions)
					.setLocationOfTheCar(locationOfTheCar);
			if(images != null) {
				carAdvertDto.setImages(convertMultipartFilesToBytes(List.of(images)));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		carAdvertsService.saveCarAdvertNew(carAdvertDto,request);
		return ResponseEntity.ok("success");
	}
	private Set<byte[]> convertMultipartFilesToBytes(List<MultipartFile> multipartFiles) throws IOException {
		// Convert MultipartFile objects to byte arrays
		Set<byte[]> imageDataList = new HashSet<>();
		for (MultipartFile file : multipartFiles) {
			imageDataList.add(file.getBytes());
		}
		return imageDataList;
	}
}
