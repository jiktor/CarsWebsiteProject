package com.cars.backend.Services;

import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dto.CarAdvertDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;

import java.text.ParseException;
import java.util.List;

public interface CarAdvertsService {
	public List<Brands> getBrands();
	public void saveCarAdvert(CarAdvertDto advertDto,@NonNull HttpServletRequest request);
	public void saveCarAdvertNew(CarAdvertDto advertDto,@NonNull HttpServletRequest request);
	public List<CarAdvertDto> getPreviouslyViewedAdds(@NonNull HttpServletRequest request, Long advrtId);
	public List<CarAdvertDto> getAllAdverts();
	public List<CarAdvertDto> getAdvertsWithPagination(int pageNumber, int pageSize);
	public List<CarAdvertDto> getAdvertsWithPaginationAndSorting(int pageNumber, int pageSize,String sortField, String sortOrder);
	public List<CarAdvertDto> getAdvertsByEngineWithPaginationAndSorting(int pageNumber, int pageSize,String sortField, String sortOrder,String engine);
	public Long getAllAdvertsCount(int adsPerPage);
	//!!below is the original working service method for fitration
//	public List<CarAdvertDto> getAdvertsWithFiltrationAndPaginationAndSorting(int pageNumber,
//																			  int pageSize,
//																			  String sortField,
//																			  String sortOrder,
//																			  String engine,
//																			  String brand,
//																			  String model,
//																			  String dateOfManufacturing,
//																			  String fromPrice,
//																			  String toPrice);
	//!!above is the original working method for filtration
	//!!below is the new method for filtration
	public List<CarAdvertDto> getAdvertsWithFiltrationAndPaginationAndSorting(int pageNumber,
																			  int pageSize,
																			  String sortField,
																			  String sortOrder,
																			  String engine,
																			  String brand,
																			  String model,
																			  String fromDate,
																			  String toDate,
																			  String fromPrice,
																			  String toPrice) throws ParseException;
	//!!above is the new method for filtration
	public Long getAdvertsCountWithFilter(int pageNumber,
										  int pageSize,
										  String sortField,
										  String sortOrder,
										  String engine,
										  String brand,
										  String model,
										  String dateOfManufacturing,
										  String fromPrice,
										  String toPrice);

	Object getSingleAdvert(Long advertId, HttpServletRequest request);

	Object getOwnerByAdvert(Long advertId);
}
