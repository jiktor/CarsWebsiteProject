package com.cars.backend.Models.Dao;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;
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
}
