package com.ncgroup.marketplaceserver.repository;



import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;

import java.util.List;

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

	void updateUserByEmail(UserDto user, String email);

	List<User> allCouriersManagers();
}
