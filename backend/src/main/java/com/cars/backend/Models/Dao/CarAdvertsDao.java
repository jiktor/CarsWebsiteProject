package com.cars.backend.Models.Dao;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;

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
	@Lob
	@Column(name = "image_data")
	private List<byte[]> imageData;
	@ManyToOne
	@NonNull
	@JoinColumn(referencedColumnName = "id")
	private ModelsDao model;
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private UserDao owner;
}
