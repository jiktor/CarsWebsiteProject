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
	@Column(unique = true)
	@NonNull
	@Enumerated(EnumType.STRING)
	private Models model;
	@ManyToOne(fetch = FetchType.EAGER)
	@NonNull
	@JoinColumn(referencedColumnName = "id")
	private BrandsDao brand;
	@OneToMany(mappedBy = "model")

	private List<CarAdvertsDao> carAdvertsDaoList;
}
