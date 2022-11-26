package com.dataLoader.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dataLoader.app.entity.User;
import com.dataLoader.app.entity.UserNameID;
import com.dataLoader.app.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	Logger logger = LoggerFactory.getLogger(UserService.class);

	public List<User> findUserByName(String name) {
		List<User> userList = repository.findUserByName(name);
		logger.info("Fetched user details by name : " + userList);
		return userList;
	}

	public List<User> findUserByNameID(UserNameID inputUser) {
		List<User> user = repository.searchByNameOrId(inputUser.getName(), inputUser.getId());
		logger.info("Fetched user details by name and/or id : " + user);
		return user;
	}

	public User findUserByID(Long id) {
		User user = repository.findById(id).orElse(new User());
		logger.info("Fetched user details by id : " + user);
		return user;
	}

	public void deleteUserById(Long id) {
		repository.deleteById(id);
		logger.info("Deleting user by id successful.");
	}

}
