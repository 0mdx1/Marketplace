package com.ncgroup.marketplaceserver.repository;

import java.util.List;
import java.util.Optional;

import com.ncgroup.marketplaceserver.model.User;

public interface UserRepository {
	User findById(long id);
	User findByEmail(String email);
	User save(User user);
	List<User> findAll();
	//void updateLastFailedAuth(long id, int lastFailedAuth);
	void updateLastFailedAuth(String email, int lastFailedAuth);
	void deleteById(long id);
}
