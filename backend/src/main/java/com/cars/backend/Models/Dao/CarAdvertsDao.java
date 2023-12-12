package com.cars.backend.Models.Dao;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

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
	@Lob
	@Column(name = "image_data", nullable = false)
	private List<byte[]> imageData;
	@ManyToOne
	@NonNull
	@JoinColumn(referencedColumnName = "id")
	private ModelsDao model;
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private UserDao owner;
}
