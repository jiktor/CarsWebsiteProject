package com.cars.backend.Services;

import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dto.CarAdvertDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CarAdvertsService {
	public List<Brands> getBrands();
	public void saveCarAdvert(CarAdvertDto advertDto,@NonNull HttpServletRequest request);
}
