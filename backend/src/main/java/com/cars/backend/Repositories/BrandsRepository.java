package com.cars.backend.Repositories;

import com.cars.backend.Models.Dao.BrandsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends JpaRepository<BrandsDao,Long> {
}
