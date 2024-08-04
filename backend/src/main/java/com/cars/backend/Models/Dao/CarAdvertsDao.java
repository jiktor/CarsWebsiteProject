package com.cars.backend.Models.Dao;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.Set;

@Entity
public class CarAdvertsDao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	@NonNull
	private float price;
	@Column
	private String engine;
	@Lob
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	@Column
	private Date dateOfManufacturing;
	//todo: pictures must be nullable=false
	@Column
	private String horsePower;
	@Column
	private String mileage;
	@Column
	private String gearbox;
	@Column
	private String color;
	@Column
	private String engineType;
	@Column
	private String euroEmmissions;
	@Column
	private String locationOfTheCar;
//	@Column
//	private String condition;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "id")
	private Set<ImageDao> imageData;
	@ManyToOne
	@NonNull
	@JoinColumn(referencedColumnName = "id")
	private ModelsDao model;
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private UserDao owner;

	public CarAdvertsDao() {
	}

//	public CarAdvertsDao(float price, String engine, String description, Date dateOfManufacturing, String horsePower, String mileage, Set<ImageDao> imageData, @NonNull ModelsDao model, UserDao owner) {
//		this.price = price;
//		this.engine = engine;
//		this.description = description;
//		this.dateOfManufacturing = dateOfManufacturing;
//		this.horsePower = horsePower;
//		this.mileage = mileage;
//		this.imageData = imageData;
//		this.model = model;
//		this.owner = owner;
//	}

	public CarAdvertsDao(Long id, float price, String engine, String description, Date dateOfManufacturing, Set<ImageDao> imageData, @NonNull ModelsDao model, UserDao owner) {
		this.id = id;
		this.price = price;
		this.engine = engine;
		this.description = description;
		this.dateOfManufacturing = dateOfManufacturing;
		this.imageData = imageData;
		this.model = model;
		this.owner = owner;
	}

//	public CarAdvertsDao(Long id, float price, String engine, String description, Date dateOfManufacturing, String horsePower, String mileage, String gearbox, String color, String engineType, String euroEmissionStandard, String locationOfTheCar, String condition, Set<ImageDao> imageData, @NonNull ModelsDao model, UserDao owner) {
//		this.id = id;
//		this.price = price;
//		this.engine = engine;
//		this.description = description;
//		this.dateOfManufacturing = dateOfManufacturing;
//		this.horsePower = horsePower;
//		this.mileage = mileage;
//		this.gearbox = gearbox;
//		this.color = color;
//		this.engineType = engineType;
//		this.euroEmissionStandard = euroEmissionStandard;
//		this.locationOfTheCar = locationOfTheCar;
//		this.condition = condition;
//		this.imageData = imageData;
//		this.model = model;
//		this.owner = owner;
//	}
//


	public String getGearbox() {
		return gearbox;
	}

	public CarAdvertsDao setGearbox(String gearbox) {
		this.gearbox = gearbox;
		return this;
	}

	public String getColor() {
		return color;
	}

	public CarAdvertsDao setColor(String color) {
		this.color = color;
		return this;
	}

	public String getEngineType() {
		return engineType;
	}

	public CarAdvertsDao setEngineType(String engineType) {
		this.engineType = engineType;
		return this;
	}

	public String getEuroEmmissions() {
		return euroEmmissions;
	}

	public CarAdvertsDao setEuroEmmissions(String euroEmmissions) {
		this.euroEmmissions = euroEmmissions;
		return this;
	}

	public String getLocationOfTheCar() {
		return locationOfTheCar;
	}

	public CarAdvertsDao setLocationOfTheCar(String locationOfTheCar) {
		this.locationOfTheCar = locationOfTheCar;
		return this;
	}

//	public String getCondition() {
//		return condition;
//	}
//
//	public CarAdvertsDao setCondition(String condition) {
//		this.condition = condition;
//		return this;
//	}

	public Long getId() {
		return id;
	}

	public CarAdvertsDao setId(Long id) {
		this.id = id;
		return this;
	}

	public float getPrice() {
		return price;
	}

	public CarAdvertsDao setPrice(float price) {
		this.price = price;
		return this;
	}

	public String getEngine() {
		return engine;
	}

	public CarAdvertsDao setEngine(String engine) {
		this.engine = engine;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CarAdvertsDao setDescription(String description) {
		this.description = description;
		return this;
	}

	public Date getDateOfManufacturing() {
		return dateOfManufacturing;
	}

	public CarAdvertsDao setDateOfManufacturing(Date dateOfManufacturing) {
		this.dateOfManufacturing = dateOfManufacturing;
		return this;
	}

	public Set<ImageDao> getImageData() {
		return imageData;
	}

	public CarAdvertsDao setImageData(Set<ImageDao> imageData) {
		this.imageData = imageData;
		return this;
	}

	@NonNull
	public ModelsDao getModel() {
		return model;
	}

	public CarAdvertsDao setModel(@NonNull ModelsDao model) {
		this.model = model;
		return this;
	}

	public UserDao getOwner() {
		return owner;
	}

	public CarAdvertsDao setOwner(UserDao owner) {
		this.owner = owner;
		return this;
	}

	public String getHorsePower() {
		return horsePower;
	}

	public CarAdvertsDao setHorsePower(String horsePower) {
		this.horsePower = horsePower;
		return this;
	}

	public String getMileage() {
		return mileage;
	}

	public CarAdvertsDao setMileage(String mileage) {
		this.mileage = mileage;
		return this;
	}
}
