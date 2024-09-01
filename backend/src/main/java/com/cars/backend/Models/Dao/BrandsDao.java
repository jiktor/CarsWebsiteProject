package com.cars.backend.Models.Dao;

import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dao.Enums.Models;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BrandsDao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	@Enumerated(EnumType.STRING)
	@NonNull
	private Brands brand;
	@OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ModelsDao> models;
}
