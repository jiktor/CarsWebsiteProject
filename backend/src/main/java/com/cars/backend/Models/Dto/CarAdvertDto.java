package com.cars.backend.Models.Dto;

import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dao.Enums.Models;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
public class CarAdvertDto {
	private String model;
	private String brand;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date dateOfManufacturing;

	private float price;
	private String description;
	private String engine;

	public CarAdvertDto() {
	}

	public CarAdvertDto(String model, String brand, float price, String description, String engine, Date dateOfManufacturing) {
		this.model = model;
		this.brand = brand;
		this.price = price;
		this.description = description;
		this.engine = engine;
		this.dateOfManufacturing = dateOfManufacturing;
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
