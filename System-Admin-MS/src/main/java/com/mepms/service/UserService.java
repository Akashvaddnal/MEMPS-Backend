package com.mepms.service;

import java.util.List;
import java.util.Optional;

import com.mepms.entity.UserEO;

public interface UserService {
	UserEO createUser(UserEO user);

    List<UserEO> getAllUsers();

    Optional<UserEO> getUserById(String id);

    UserEO updateUser(String id, UserEO user);

    void deleteUser(String id);
}
