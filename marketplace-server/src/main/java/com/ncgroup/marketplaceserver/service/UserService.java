package com.ncgroup.marketplaceserver.service;

import java.util.List;

import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;

public interface UserService {
    UserDto register(String name, String surname, String email, String password, String phone);

    UserDto enableUser(String link);

    List<UserDto> getUsers();

    User findUserByEmail(String email);

    User findUserById(long id);

    User getUserByLink(String link);

    User addUser(String name, String surname, String email, Role role, String phone);

    User updateUser(long id, String newName, String newSurname, String newEmail, String newPhone, boolean isEnabled);

    void deleteUser(long id);

    void resetPassword(String email);

    User setNewPassword(String link, String newPassword);

    //validation
    User validateNewEmail(String currentEmail, String newEmail);

    boolean validatePasswordPattern(String password);

    String encodePassword(String password);

    User getCurrentUser();

}
