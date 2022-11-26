package com.dataLoader.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dataLoader.app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findUserByName(String User);

	@Query("SELECT u FROM user_details u WHERE u.name = ?1 OR id = ?2")
	List<User> searchByNameOrId(String name, Long id);
}
