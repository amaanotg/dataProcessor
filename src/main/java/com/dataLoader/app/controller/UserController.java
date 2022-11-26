package com.dataLoader.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dataLoader.app.entity.UserNameID;
import com.dataLoader.app.service.UserService;
import com.google.gson.Gson;

@RestController
public class UserController {

	@Value("${input.file.directory}")
	private String inputFileDirectory;

	@Autowired
	private UserService service;

	Gson gson = new Gson();

	@GetMapping(value = "/", produces = MediaType.ALL_VALUE)
	public String home() {
		return "Welcome to Spring Boot";
	}

	@GetMapping(value = "/v1/user/name/{name}", produces = "application/json;charset=UTF-8")
	public String getUserByName(@PathVariable String name) {
		return gson.toJson(service.findUserByName(name));
	}

	@GetMapping(value = "/v1/user/id/{id}", produces = "application/json;charset=UTF-8")
	public String getUserById(@PathVariable String id) {
		return gson.toJson(service.findUserByID(Long.parseLong(id)));
	}

	@DeleteMapping(value = "/v1/user/id/{id}", produces = "application/json;charset=UTF-8")
	public void deleteUserById(@PathVariable String id) {
		service.deleteUserById(Long.parseLong(id));
	}

	@PostMapping(value = "/v1/user", produces = "application/json;charset=UTF-8")
	public String findByNameOrId(@RequestBody UserNameID user) {
		return gson.toJson(service.findUserByNameID(user));
	}

}
