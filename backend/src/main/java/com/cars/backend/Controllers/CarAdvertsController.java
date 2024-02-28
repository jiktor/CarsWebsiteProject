package com.cars.backend.Controllers;

import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dao.Enums.Models;
import com.cars.backend.Models.Dto.CarAdvertDto;
import com.cars.backend.Services.CarAdvertsService;
import com.cars.backend.Services.Impl.ModelsServiceImpl;
import com.cars.backend.Services.ModelsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
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
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
//					@PostMapping("/save")
//					public ResponseEntity createAdvert(@RequestBody CarAdvertDto carAdvertDto,
//													   @NonNull HttpServletRequest request){
//						carAdvertsService.saveCarAdvert(carAdvertDto,request);
//						return ResponseEntity.ok("success");
//					}
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
	@PostMapping("/save")
	public ResponseEntity createAdvert(@RequestParam("model") String model,
									   @RequestParam("brand") String brand,
									   @RequestParam("price") String price,
									   @RequestParam("engine") String engine,
									   @RequestParam("dateOfManufacturing") String dateOfManufacturing,
									   @RequestParam("description") String description,
									   @RequestParam(value = "images",required = false) MultipartFile[] images,
									   @NonNull HttpServletRequest request){
		CarAdvertDto carAdvertDto = null;
		try {
			carAdvertDto = new CarAdvertDto()
															.setBrand(brand)
															.setModel(model)
															.setDateOfManufacturing(Date.valueOf(dateOfManufacturing))
															.setDescription(description)
															.setEngine(engine)
															.setPrice(Float.valueOf(price));
			if(images != null) carAdvertDto.setImages(convertMultipartFilesToBytes(List.of(images)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		carAdvertsService.saveCarAdvert(carAdvertDto,request);
		return ResponseEntity.ok("success");
	}
	private List<byte[]> convertMultipartFilesToBytes(List<MultipartFile> multipartFiles) throws IOException {
		// Convert MultipartFile objects to byte arrays
		List<byte[]> imageDataList = new ArrayList<>();
		for (MultipartFile file : multipartFiles) {
			imageDataList.add(file.getBytes());
		}
		return imageDataList;
	}
}
