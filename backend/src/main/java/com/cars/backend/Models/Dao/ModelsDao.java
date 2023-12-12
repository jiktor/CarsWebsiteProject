package com.cars.backend.Models.Dao;

import com.cars.backend.Models.Dao.Enums.Models;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ModelsDao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	@NonNull
	@Enumerated(EnumType.STRING)
	private Models model;
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private BrandsDao brand;
	@OneToMany(mappedBy = "model")

	private List<CarAdvertsDao> carAdvertsDaoList;
}