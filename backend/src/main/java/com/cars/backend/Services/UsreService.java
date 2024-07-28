package com.cars.backend.Services;

import com.cars.backend.Models.Dao.UserDao;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;

public interface UsreService {
	UserDao findUserByEmail(String email);
	void saveUser(UserDao userDao);
	public void updateLastCheckedRequest(@NonNull HttpServletRequest request, Long carAdvertId);
}
