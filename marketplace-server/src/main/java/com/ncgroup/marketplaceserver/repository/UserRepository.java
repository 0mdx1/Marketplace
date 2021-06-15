package com.ncgroup.marketplaceserver.repository;

import java.util.List;

import com.ncgroup.marketplaceserver.model.User;

public interface UserRepository {
	User findById(long id);

	User findByEmail(String email);

	User findByAuthLink(String link);

	User save(User user);
	
	User saveWithoutCredentials(User user);

	List<User> findAll();

	//void updateLastFailedAuth(long id, int lastFailedAuth);
	void updateLastFailedAuth(String email, int lastFailedAuth);

	void deleteById(long id);

	void enableUser(String link);

	void updateAuthLink(String email, String link);

	void updatePassword(String email, String password);

	List<User> allCouriersManagers();
}
