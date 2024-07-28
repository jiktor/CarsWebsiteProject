package com.cars.backend.Services.Impl;

import com.cars.backend.Models.Dao.UserDao;
import com.cars.backend.Repositories.UserRepository;
import com.cars.backend.Services.JwtService;
import com.cars.backend.Services.UsreService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsreServiceImpl implements UsreService {
	private final UserRepository userRepository;
	private final JwtService jwtService;
	@Autowired
	public UsreServiceImpl(UserRepository userRepository, JwtService jwtService) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
	}

	@Override
	public UserDao findUserByEmail(String email) {
		return userRepository.findByEmail(email)
							.orElseThrow();
	}

	@Override
	public void saveUser(UserDao userDao) {
		this.userRepository.save(userDao);
	}

	@Override
	public void updateLastCheckedRequest(HttpServletRequest request, Long carAdvertId) {
		UserDao userDao = findUserByEmail(jwtService.extractUsername(request.getHeader("Authorization").substring(7)));

		if(userDao.getRecentlyViewdAdverts() != null){

			if(userDao.getRecentlyViewdAdverts().size() >= 3)
				userDao.getRecentlyViewdAdverts().remove(0);
		}else{
			List<Long> list = new ArrayList<>();
			userDao.setRecentlyViewdAdverts(list);
		}
		userDao.getRecentlyViewdAdverts().add(carAdvertId);
		saveUser(userDao);
	}
}
