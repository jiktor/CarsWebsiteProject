package com.cars.backend.Repositories;

import com.cars.backend.Models.Dao.ImageDao;
import com.cars.backend.Models.Dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageDao, Long> {
}
