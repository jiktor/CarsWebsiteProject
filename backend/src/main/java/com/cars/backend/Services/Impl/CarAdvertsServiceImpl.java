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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
	public void saveCarAdvertNew(CarAdvertDto advertDto, HttpServletRequest request) {
		CarAdvertsDao carAdvertsDao = mapper.map(advertDto, CarAdvertsDao.class);
		carAdvertsDao.setDescription(advertDto.getDescription());
		carAdvertsDao.setModel(modelsService.getModelsByModel(Models.valueOf(advertDto.getModel())));
		carAdvertsDao.setOwner(usreService.findUserByEmail(jwtService.extractUsername(request.getHeader("Authorization").substring(7))));
		carAdvertsDao.setImageData(new HashSet<ImageDao>());
		if(advertDto.getMileage() != null && advertDto.getMileage() != ""){
			carAdvertsDao.setMileage(advertDto.getMileage());
		}
		if(advertDto.getHorsePower() != null && advertDto.getHorsePower() != ""){
			carAdvertsDao.setHorsePower(advertDto.getHorsePower());
		}
		if(advertDto.getGearbox() != null && advertDto.getGearbox() != ""){
			carAdvertsDao.setGearbox(advertDto.getGearbox());
		}
		if(advertDto.getColor() != null && advertDto.getColor() != ""){
			carAdvertsDao.setColor(advertDto.getColor());
		}
		if(advertDto.getEngineType() != null && advertDto.getEngineType() != ""){
			carAdvertsDao.setEngineType(advertDto.getEngineType());
		}
		if(advertDto.getEuroEmmissions() != null && advertDto.getEuroEmmissions() != ""){
			carAdvertsDao.setEuroEmmissions(advertDto.getEuroEmmissions());
		}
		if(advertDto.getLocationOfTheCar() != null && advertDto.getLocationOfTheCar() != ""){
			carAdvertsDao.setLocationOfTheCar(advertDto.getLocationOfTheCar());
		}
//		if(advertDto.getCondition() != null && advertDto.getCondition() != ""){
//			carAdvertsDao.setCondition(advertDto.getCondition());
//		}
		//TODO ако няма снимки гърми -> проверка зя NULL
		for(byte [] dtoImage : advertDto.getImages()){
			ImageDao imageDao = new ImageDao();
			imageDao.setImage(dtoImage);
			carAdvertsDao.getImageData().add(imageDao);
		}
		carAdvertsRepository.save(carAdvertsDao);
	}

	@Override
	public List<CarAdvertDto> getPreviouslyViewedAdds(HttpServletRequest request, Long advertId) {
		List <Long> advertsId = new ArrayList<>();
		List <CarAdvertsDao> carAdvertsDaoList = new ArrayList<>();
		List <CarAdvertDto> result = new ArrayList<>();
		UserDao userDao = usreService.findUserByEmail(jwtService.extractUsername(request.getHeader("Authorization").substring(7)));
		if(userDao.getRecentlyViewdAdverts() != null) {
			for (Long id : userDao.getRecentlyViewdAdverts()) {
				CarAdvertsDao carAdvertsDao = carAdvertsRepository.findById(id).get();
				CarAdvertDto carAdvertDto = new CarAdvertDto();
				//
				Set<byte[]> imagesSet = new HashSet<>();
				for (ImageDao image : carAdvertsDao.getImageData()) {
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
				result.add(carAdvertDto);
				//
			}
		}
		if(userDao.getRecentlyViewdAdverts() == null){
			usreService.updateLastCheckedRequest(request,advertId);
		}
		else {
			for(int i =0 ; i<userDao.getRecentlyViewdAdverts().size(); i++) {
				if(advertId.equals(userDao.getRecentlyViewdAdverts().get(i))) break;
				if((i+1) == userDao.getRecentlyViewdAdverts().size())usreService.updateLastCheckedRequest(request, advertId);
			}
		}

		return result;
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
	//!bellow is the original working implementation for filtration
//	@Override
//	public List<CarAdvertDto> getAdvertsWithFiltrationAndPaginationAndSorting(int pageNumber,
//																			  int pageSize,
//																			  String sortField,
//																			  String sortOrder,
//																			  String engine,
//																			  String brand,
//																			  String model,
//																			  String dateOfManufacturing,
//																			  String fromPrice,
//																			  String toPrice
//																			  ) {
//		Sort sort = Sort.by(sortField).ascending(); // or Sort.by(sortField).descending() for descending order
//		if ("desc".equals(sortOrder)) {
//			sort = sort.descending();
//		}else{
//			sort = sort.ascending();
//		}
//		org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
//
//		Long modelsDaoId = null;
//		if(model!=null && !model.equals("null")){
//			modelsDaoId = modelsService.getModelsByModel(Models.valueOf(model)).getId();
//		}
//
//		Long brandsDaoId = null;
//		if(brand!=null && !brand.equals("null")){
//			brandsDaoId = brandsService.getBrandDaoByBrand(Brands.valueOf(brand)).getId();
//		}
//		Float fromPriceact = null;
//		if(fromPrice!=null && fromPrice != ""){
//			fromPriceact = Float.valueOf(fromPrice);
//		}
//		Float toPriceact = null;
//		if(toPrice!=null && toPrice != ""){
//			toPriceact = Float.valueOf(toPrice);
//		}
//		Date dateManufacturing = null;
//		if(dateManufacturing != null){
//			dateManufacturing = new Date(dateOfManufacturing);
//		}
//		Page<CarAdvertsDao> carAdvertsDaoPage = carAdvertsRepository.findByDynamicParams(
//				modelsDaoId,
//				brandsDaoId,
//				fromPriceact,
//				toPriceact,
//				engine,
//				dateManufacturing,
//				pageable
//		);
//		List<CarAdvertsDao> carAdvertsDaoList = carAdvertsDaoPage.getContent();
//		//converting dao to dto object
//		List <CarAdvertDto> list = new ArrayList<>();
//		for(CarAdvertsDao carAdvertsDao : carAdvertsDaoList){
//			CarAdvertDto carAdvertDto = new CarAdvertDto();
//
//			Set<byte[]> imagesSet = new HashSet<>();
//			for(ImageDao image : carAdvertsDao.getImageData()){
//				imagesSet.add(image.getImage());
//			}
//
//			carAdvertDto
//					.setId(carAdvertsDao.getId())
//					.setModel(carAdvertsDao.getModel().getModel().name())
//					.setDateOfManufacturing(carAdvertsDao.getDateOfManufacturing())
//					.setPrice(carAdvertsDao.getPrice())
//					.setDescription(carAdvertsDao.getDescription())
//					.setEngine(carAdvertsDao.getEngine())
//					.setImages(imagesSet)
//					.setBrand(carAdvertsDao.getModel().getBrand().getBrand().name());
//			list.add(carAdvertDto);
//		}
//		//
//		return list;
//	}
//above is the original working implementation for filtration
	//below is the new ipmplementation for filtration
@Override
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
																		  String toPrice
) throws ParseException {
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
	Date fromDateParam = null;
	if(fromDate != null && fromDate != ""){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		fromDateParam = dateFormat.parse(fromDate);
	}
	Date toDateParam = null;
	if(toDate != null && toDate != ""){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		toDateParam = dateFormat.parse(toDate);
	}

	Page<CarAdvertsDao> carAdvertsDaoPage = carAdvertsRepository.findByDynamicParams(
			modelsDaoId,
			brandsDaoId,
			fromPriceact,
			toPriceact,
			engine,
			fromDateParam,
			toDateParam,
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
				.setBrand(carAdvertsDao.getModel().getBrand().getBrand().name())
				.setHorsePower(carAdvertsDao.getHorsePower())
				.setMileage(carAdvertsDao.getMileage())
				.setGearbox(carAdvertsDao.getGearbox())
				.setColor(carAdvertsDao.getColor())
				.setEngineType(carAdvertsDao.getEngineType())
				.setEuroEmmissions(carAdvertsDao.getEuroEmmissions())
				.setLocationOfTheCar(carAdvertsDao.getLocationOfTheCar());
		list.add(carAdvertDto);
	}
	//
	return list;
}
//@@
	@Override
	public Long getAdvertsCountWithFilter(int pageNumber,
										  int pageSize,
										  String sortField,
										  String sortOrder,
										  String engine,
										  String brand,
										  String model,
										  String dateOfManufacturing,
										  String fromPrice,
										  String toPrice) {

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
		Long advertsCount = carAdvertsRepository.findCountWithParams(modelsDaoId,brandsDaoId,fromPriceact,toPriceact,engine,dateManufacturing);
		//Logic for returning number of pages basedon total number of ads and the size of the page
		return  (advertsCount>pageSize) ? ((advertsCount%pageSize)>0) ? ((advertsCount/pageSize)+1):((advertsCount/pageSize)) : (1);
	}
	//this service is being used
	@Override
	public Object getSingleAdvert(Long advertId, HttpServletRequest request) {
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
				.setDateOfManufacturing(carAdvertsDao.getDateOfManufacturing())
				.setHorsePower(carAdvertsDao.getHorsePower())
				.setMileage(carAdvertsDao.getMileage())
				.setGearbox(carAdvertsDao.getGearbox())
				.setColor(carAdvertsDao.getColor())
				.setEngineType(carAdvertsDao.getEngineType())
				.setEuroEmmissions(carAdvertsDao.getEuroEmmissions())
				.setLocationOfTheCar(carAdvertsDao.getLocationOfTheCar());

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
