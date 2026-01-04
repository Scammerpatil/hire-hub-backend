package com.hirehub.SpringServer.Services;

import com.hirehub.SpringServer.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long userId);
    Optional<User> getUserByEmail(String email);
    boolean existsByEmail(String email);
    List<User> getAllUsers();
}
