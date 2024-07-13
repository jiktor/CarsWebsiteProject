package com.cars.backend.Services.Impl;

import com.cars.backend.Models.Dao.*;
import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dao.Enums.Models;
import com.cars.backend.Models.Dto.CarAdvertDto;
import com.cars.backend.Models.Dto.UserDto;
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
	public Long getAllAdvertsCount(int adsPerPage){
		if(carAdvertsRepository.count() <= adsPerPage)
			return 0L;
		else if (carAdvertsRepository.count()%adsPerPage == 0)
			return carAdvertsRepository.count()/adsPerPage-1;
		else
			return carAdvertsRepository.count()/adsPerPage;
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
		//TODO ако няма снимки гърми -> проверка зя NULL
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

	//TODO to be deprecated to !!!!!
	@Override
	public List<CarAdvertDto> getAdvertsByEngineWithPaginationAndSorting(int pageNumber, int pageSize, String sortField, String sortOrder, String engine) {
		Sort sort = Sort.by(sortField).ascending(); // or Sort.by(sortField).descending() for descending order
		if ("desc".equals(sortOrder)) {
			sort = sort.descending();
		}
		org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<CarAdvertsDao> carAdvertsDaoPage = carAdvertsRepository.findByEngine(engine,pageable);
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
	//!!!!!
	@Override
	public List<CarAdvertDto> getAdvertsWithFiltrationAndPaginationAndSorting(int pageNumber,
																			  int pageSize,
																			  String sortField,
																			  String sortOrder,
																			  String engine,
																			  String brand,
																			  String model,
																			  String dateOfManufacturing,
																			  String fromPrice,
																			  String toPrice
																			  ) {
		Sort sort = Sort.by(sortField).ascending(); // or Sort.by(sortField).descending() for descending order
		if ("desc".equals(sortOrder)) {
			sort = sort.descending();
		}else{
			sort = sort.ascending();
		}
		org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Long modelsDaoId = null;
		if(model!=null && !model.equals("null")){
			modelsDaoId = modelsService.getModelsByModel(Models.valueOf(model)).getId();
		}

		Long brandsDaoId = null;
		if(brand!=null && !brand.equals("null")){
			brandsDaoId = brandsService.getBrandDaoByBrand(Brands.valueOf(brand)).getId();
		}
		Float fromPriceact = null;
		if(fromPrice!=null && fromPrice != ""){
			fromPriceact = Float.valueOf(fromPrice);
		}
		Float toPriceact = null;
		if(toPrice!=null && toPrice != ""){
			toPriceact = Float.valueOf(toPrice);
		}
		Date dateManufacturing = null;
		if(dateManufacturing != null){
			dateManufacturing = new Date(dateOfManufacturing);
		}
		Page<CarAdvertsDao> carAdvertsDaoPage = carAdvertsRepository.findByDynamicParams(
				modelsDaoId,
				brandsDaoId,
				fromPriceact,
				toPriceact,
				engine,
				dateManufacturing,
				pageable
		);
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
					.setId(carAdvertsDao.getId())
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
	public Object getSingleAdvert(Long advertId) {
		CarAdvertsDao carAdvertsDao = carAdvertsRepository.getReferenceById(advertId);

		CarAdvertDto carAdvertDto = new CarAdvertDto();
		carAdvertDto.setImages(new HashSet<>());
		carAdvertDto
				.setId(carAdvertsDao.getId())
				.setBrand(carAdvertsDao.getModel().getBrand().getBrand().toString())
				.setModel(carAdvertsDao.getModel().getModel().name())
				.setEngine(carAdvertsDao.getEngine())
				.setDescription(carAdvertsDao.getDescription())
				.setPrice(carAdvertsDao.getPrice())
				.setDateOfManufacturing(carAdvertsDao.getDateOfManufacturing());

		for(ImageDao image : carAdvertsDao.getImageData()){
			carAdvertDto.getImages().add(image.getImage());
		}
		return carAdvertDto;
	}

	@Override
	public Object getOwnerByAdvert(Long advertId) {
		CarAdvertsDao carAdvertsDao = this.carAdvertsRepository.getReferenceById(advertId);
		UserDao userDao = carAdvertsDao.getOwner();
		UserDto userDto =  new UserDto();

		userDto
				.setEmail(userDao.getEmail())
				.setFirstName(userDao.getFirstName())
				.setSecondName(userDao.getSecondName());
		return userDto;
	}


}
