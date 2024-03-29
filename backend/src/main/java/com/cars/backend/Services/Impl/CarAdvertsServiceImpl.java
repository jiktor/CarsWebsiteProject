package com.cars.backend.Services.Impl;

import com.cars.backend.Models.Dao.CarAdvertsDao;
import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dao.Enums.Models;
import com.cars.backend.Models.Dao.ImageDao;
import com.cars.backend.Models.Dto.CarAdvertDto;
import com.cars.backend.Repositories.CarAdvertsRepository;
import com.cars.backend.Repositories.ImageRepository;
import com.cars.backend.Services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarAdvertsServiceImpl implements CarAdvertsService {
	private final CarAdvertsRepository carAdvertsRepository;
	private final ModelsService modelsService;
	private final BrandsService brandsService;
	private final UsreService usreService;
	private final JwtService jwtService;
	private final ModelMapper mapper;
	private final ImageRepository imageRepository;
	@Autowired
	public CarAdvertsServiceImpl(CarAdvertsRepository carAdvertsRepository, ModelsService modelsService, BrandsService brandsService, UsreService usreService, JwtService jwtService, ModelMapper mapper, ImageRepository imageRepository){
		this.carAdvertsRepository = carAdvertsRepository;
		this.modelsService = modelsService;
		this.brandsService = brandsService;
		this.usreService = usreService;
		this.jwtService = jwtService;
		this.mapper = mapper;
		this.imageRepository = imageRepository;
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
		carAdvertsDao.setImageData(new HashSet<ImageDao>());
		for(byte [] dtoImage : advertDto.getImages()){
			ImageDao imageDao = new ImageDao();
			imageDao.setImage(dtoImage);
			carAdvertsDao.getImageData().add(imageDao);
		}
		carAdvertsRepository.save(carAdvertsDao);
	}

	@Override
	public List<CarAdvertDto> getAllAdverts() {
		List <CarAdvertsDao> carAdvertsDaos = carAdvertsRepository.findAll();
		List <CarAdvertDto> list = new ArrayList<>();
		for(CarAdvertsDao carAdvertsDao : carAdvertsDaos){
			CarAdvertDto carAdvertDto = new CarAdvertDto();

			Set<byte[]> imagesSet = new HashSet<>();
			for(ImageDao image : carAdvertsDao.getImageData()){
				imagesSet.add(image.getImage());
			}

					carAdvertDto
							.setModel(carAdvertsDao.getModel().getModel().name())
							.setDateOfManufacturing(carAdvertsDao.getDateOfManufacturing())
							.setPrice(carAdvertsDao.getPrice())
							.setDescription(carAdvertsDao.getDescription())
							.setEngine(carAdvertsDao.getEngine())
							.setImages(imagesSet)
							.setBrand(carAdvertsDao.getModel().getBrand().getBrand().name());
			list.add(carAdvertDto);
		}
		return list;
	}

	@Override
	public List<CarAdvertDto> getAdvertsWithPagination(int pageNumber, int pageSize) {
		org.springframework.data.domain.Pageable pageable =PageRequest.of(pageNumber, pageSize);
		Page<CarAdvertsDao> carAdvertsDaoPage = carAdvertsRepository.findAll(pageable);
		List<CarAdvertsDao> carAdvertsDaoList = carAdvertsDaoPage.getContent();
		//converting dao to dto object
		List <CarAdvertDto> list = new ArrayList<>();
		for(CarAdvertsDao carAdvertsDao : carAdvertsDaoList){
			CarAdvertDto carAdvertDto = new CarAdvertDto();

			Set<byte[]> imagesSet = new HashSet<>();
			for(ImageDao image : carAdvertsDao.getImageData()){
				imagesSet.add(image.getImage());
			}

			carAdvertDto
					.setModel(carAdvertsDao.getModel().getModel().name())
					.setDateOfManufacturing(carAdvertsDao.getDateOfManufacturing())
					.setPrice(carAdvertsDao.getPrice())
					.setDescription(carAdvertsDao.getDescription())
					.setEngine(carAdvertsDao.getEngine())
					.setImages(imagesSet)
					.setBrand(carAdvertsDao.getModel().getBrand().getBrand().name());
			list.add(carAdvertDto);
		}
		//
		return list;
	}

	@Override
	public List<CarAdvertDto> getAdvertsWithPaginationAndSorting(int pageNumber, int pageSize, String sortField, String sortOrder) {
		Sort sort = Sort.by(sortField).ascending(); // or Sort.by(sortField).descending() for descending order
		if ("desc".equals(sortOrder)) {
			sort = sort.descending();
		}
		org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<CarAdvertsDao> carAdvertsDaoPage = carAdvertsRepository.findAll(pageable);
		List<CarAdvertsDao> carAdvertsDaoList = carAdvertsDaoPage.getContent();
		//converting dao to dto object
		List <CarAdvertDto> list = new ArrayList<>();
		for(CarAdvertsDao carAdvertsDao : carAdvertsDaoList){
			CarAdvertDto carAdvertDto = new CarAdvertDto();

			Set<byte[]> imagesSet = new HashSet<>();
			for(ImageDao image : carAdvertsDao.getImageData()){
				imagesSet.add(image.getImage());
			}

			carAdvertDto
					.setModel(carAdvertsDao.getModel().getModel().name())
					.setDateOfManufacturing(carAdvertsDao.getDateOfManufacturing())
					.setPrice(carAdvertsDao.getPrice())
					.setDescription(carAdvertsDao.getDescription())
					.setEngine(carAdvertsDao.getEngine())
//					.setImages(imagesSet)
					.setBrand(carAdvertsDao.getModel().getBrand().getBrand().name());
			list.add(carAdvertDto);
		}
		//
		return list;
	}
}
