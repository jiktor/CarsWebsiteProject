package com.cars.backend.Controllers;

import com.cars.backend.Models.Dto.CarAdvertDto;
import com.cars.backend.Models.Dto.UserDto;
import com.cars.backend.Services.CarAdvertsService;
import com.cars.backend.Services.ModelsService;
import com.cars.backend.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/cars-advert-website/viewAdverts")
public class CarAdvertsController {
	private final CarAdvertsService carAdvertsService;
	private final ModelsService modelsService;
	private final UserService userService;
	@Autowired
	public CarAdvertsController(CarAdvertsService carAdvertsService, ModelsService modelsService, UserService userService){
		this.carAdvertsService=carAdvertsService;
		this.modelsService = modelsService;
		this.userService = userService;
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
				@RequestParam(value = "fromDate",required = false) String fromDate,
				@RequestParam(value = "toDate",required = false) String toDate,
				@RequestParam(value = "dateOfManufacturing",required = false) String dateOfManufacturing) throws ParseException {

			// метод от сървис за сортиране
			return ResponseEntity.ok(carAdvertsService.getAdvertsWithFiltrationAndPaginationAndSorting(
					pageNumber,
					pageSize,
					sortField,
					sortOrder,
					engine,
					brand,
					model,
					fromDate,
					toDate,
					fromPrice,
					toPrice));
		}
	//!!above is the new method which should add filtering by date
		@GetMapping("/getSingleAdvert")
	public ResponseEntity<CarAdvertDto> getSingleAdvert(
			@RequestParam(value = "advertId", required = true) Long advertId,
			@NonNull HttpServletRequest request
		){
			return ResponseEntity.ok((CarAdvertDto) this.carAdvertsService.getSingleAdvert(advertId, request));
		}
	@GetMapping("/getOwnerOfSingleAdvert")
	public ResponseEntity<UserDto> getOwnerForAdvert(
			@RequestParam(value = "advertId", required = true) Long advertId
	){
		return ResponseEntity.ok((UserDto) this.carAdvertsService.getOwnerByAdvert(advertId));
	}

	@GetMapping("/getAdvertsCountWithFilter")
	public ResponseEntity<Long> getAdvertsCountWithFilter(
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

		return ResponseEntity.ok(carAdvertsService.getAdvertsCountWithFilter(
				pageNumber,
				pageSize,
				sortField,
				sortOrder,
				engine,
				brand,
				model,
				dateOfManufacturing,
				fromPrice,
				toPrice));
	}

	@GetMapping("/getRecentlyViewed")
	public ResponseEntity<List<CarAdvertDto>> getRecentlyViwed(
			@RequestParam(value = "advertId", required = true) int advertId,
			@NonNull HttpServletRequest request){
		return ResponseEntity.ok(this.carAdvertsService.getPreviouslyViewedAdds(request, (long) advertId));
	}

	@GetMapping("/getAdvertsByUser")
	public ResponseEntity<List<CarAdvertDto>> getRecentlyViwed(
			@NonNull HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){
		return ResponseEntity.ok(this.carAdvertsService.getAdvertsForUser(request,pageNumber,pageSize));
	}
	@GetMapping("/getAdvertsByUserPagesCount")
	public ResponseEntity<Long> getNumberOfPagesForUserAds(
			@NonNull HttpServletRequest request,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){
		return ResponseEntity.ok(this.carAdvertsService.getAdvertsForUserCountPages(pageSize,request));
	}

	@DeleteMapping("/deleteAdvert")
	public ResponseEntity<String> deleteAdvert(
			@NonNull HttpServletRequest request,
			@RequestParam(value = "advertId") int advertId){

		this.carAdvertsService.deleteAdvertById(request,advertId);

		ResponseEntity response =  ResponseEntity.ok("Success");
		return response;
	}

	}

