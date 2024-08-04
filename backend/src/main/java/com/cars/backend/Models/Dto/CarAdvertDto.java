package com.cars.backend.Models.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class CarAdvertDto {
	private Long id;
	private String model;
	private String brand;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date dateOfManufacturing;

	private float price;
	private String description;
	private String engine;
	private Set<byte[]> images;
	private String horsePower;
	private String mileage;

	private String gearbox;

	private String color;

	private String engineType;

	private String euroEmmissions;

	private String locationOfTheCar;

//	private String condition;

	public CarAdvertDto() {
	}

	public CarAdvertDto(String model, String brand, Date dateOfManufacturing, float price, String description, String engine, Set<byte[]> images, Long id) {
		this.id = id;
		this.model = model;
		this.brand = brand;
		this.dateOfManufacturing = dateOfManufacturing;
		this.price = price;
		this.description = description;
		this.engine = engine;
		this.images = images;
	}

	public String getHorsePower() {
		return horsePower;
	}

	public CarAdvertDto setHorsePower(String horsePower) {
		this.horsePower = horsePower;
		return this;
	}

	public String getMileage() {
		return mileage;
	}

	public CarAdvertDto setMileage(String mileage) {
		this.mileage = mileage;
		return this;
	}

	public String getGearbox() {
		return gearbox;
	}

	public CarAdvertDto setGearbox(String gearbox) {
		this.gearbox = gearbox;
		return this;
	}

	public String getColor() {
		return color;
	}

	public CarAdvertDto setColor(String color) {
		this.color = color;
		return this;
	}

	public String getEngineType() {
		return engineType;
	}

	public CarAdvertDto setEngineType(String engineType) {
		this.engineType = engineType;
		return this;
	}

	public String getEuroEmmissions() {
		return euroEmmissions;
	}

	public CarAdvertDto setEuroEmmissions(String euroEmmissions) {
		this.euroEmmissions = euroEmmissions;
		return this;
	}

	public String getLocationOfTheCar() {
		return locationOfTheCar;
	}

	public CarAdvertDto setLocationOfTheCar(String locationOfTheCar) {
		this.locationOfTheCar = locationOfTheCar;
		return this;
	}

//	public String getCondition() {
//		return condition;
//	}
//
//	public CarAdvertDto setCondition(String condition) {
//		this.condition = condition;
//		return this;
//	}

	public Long getId() {
		return id;
	}

	public CarAdvertDto setId(Long id) {
		this.id = id;
		return this;
	}

	public Set<byte[]> getImages() {
		return images;
	}

	public CarAdvertDto setImages(Set<byte[]> images) {
		this.images = images;
		return this;
	}

	public String getModel() {
		return model;
	}

	public CarAdvertDto setModel(String model) {
		this.model = model;
		return this;
	}

	public String getBrand() {
		return brand;
	}

	public CarAdvertDto setBrand(String brand) {
		this.brand = brand;
		return this;
	}

	public float getPrice() {
		return price;
	}

	public CarAdvertDto setPrice(float price) {
		this.price = price;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CarAdvertDto setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getEngine() {
		return engine;
	}

	public CarAdvertDto setEngine(String engine) {
		this.engine = engine;
		return this;
	}

	public Date getDateOfManufacturing() {
		return dateOfManufacturing;
	}

	public CarAdvertDto setDateOfManufacturing(Date dateOfManufacturing) {
		this.dateOfManufacturing = dateOfManufacturing;
		return this;
	}
}
