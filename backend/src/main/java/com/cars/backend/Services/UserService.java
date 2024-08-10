package com.cars.backend.Services;

import com.cars.backend.Models.Dao.UserDao;
import com.cars.backend.Models.Dto.CarAdvertDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserService {
	UserDao findUserByEmail(String email);
	void saveUser(UserDao userDao);
	public void updateLastCheckedRequest(@NonNull HttpServletRequest request, Long carAdvertId);


}
