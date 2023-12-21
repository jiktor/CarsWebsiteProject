package com.cars.backend.Services;

import com.cars.backend.Models.Dao.UserDao;

public interface UsreService {
	UserDao findUserByEmail(String email);
}
