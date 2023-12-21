package com.cars.backend.Services.Impl;

import com.cars.backend.Models.Dao.UserDao;
import com.cars.backend.Repositories.UserRepository;
import com.cars.backend.Services.UsreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsreServiceImpl implements UsreService {
	private final UserRepository userRepository;
	@Autowired
	public UsreServiceImpl(UserRepository userRepository) {this.userRepository = userRepository;}

	@Override
	public UserDao findUserByEmail(String email) {
		return userRepository.findByEmail(email)
							.orElseThrow();
	}
}
