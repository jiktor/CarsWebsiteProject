package com.cars.backend.Repositories;

import com.cars.backend.Models.Dao.CarAdvertsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarAdvertsRepository extends JpaRepository<CarAdvertsDao,Long> {
}
