package com.ncgroup.marketplaceserver.service;

import java.util.List;

import com.ncgroup.marketplaceserver.exception.domain.EmailExistException;
import com.ncgroup.marketplaceserver.exception.domain.EmailNotFoundException;
import com.ncgroup.marketplaceserver.exception.domain.UserNotFoundException;
import com.ncgroup.marketplaceserver.exception.domain.UsernameExistException;
import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;

public interface UserService {
	User register(String name, String surname, String email, String password) 
			throws UserNotFoundException, UsernameExistException, EmailExistException;
	List<User> getUsers();
    User findUserByEmail(String email);
    User findUserById(long id);
    User addUser(String name, String surname, String email, Role role, String phone) 
    		throws UserNotFoundException, UsernameExistException, EmailExistException;
    User updateUser(long id, String newName, String newSurname, String newEmail, String newPhone, boolean isEnabled) 
    		throws UserNotFoundException, UsernameExistException, EmailExistException;
    void deleteUser(long id);
    void resetPassword(String email, String newPassword) throws EmailNotFoundException;
}
