package com.cars.backend.Repositories;

import com.cars.backend.Models.Dao.BrandsDao;
import com.cars.backend.Models.Dao.Enums.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends JpaRepository<BrandsDao,Long> {
	public BrandsDao getBrandByBrand(Brands brand);
}
