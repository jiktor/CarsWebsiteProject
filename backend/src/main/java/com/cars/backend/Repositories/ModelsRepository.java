package com.cars.backend.Repositories;

import com.cars.backend.Models.Dao.BrandsDao;
import com.cars.backend.Models.Dao.Enums.Brands;
import com.cars.backend.Models.Dao.Enums.Models;
import com.cars.backend.Models.Dao.ModelsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelsRepository extends JpaRepository<ModelsDao,Long>{
	public List<ModelsDao> getModelsByBrand(BrandsDao brandsDao);
}
