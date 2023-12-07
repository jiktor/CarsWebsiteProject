package com.cars.backend.Repositories;

import com.cars.backend.Models.Dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Long> {
	public Optional<UserDao> findByEmail(String email);
	//public void save(UserDao userDao);
}
