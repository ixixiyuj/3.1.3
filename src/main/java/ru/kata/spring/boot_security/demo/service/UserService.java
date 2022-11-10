package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService extends UserDetailsService {


 void deleteUser(Long id);
 void saveUser(User user, boolean roleAdmin);
 void saveUser(User user);
 void saveUser(User user, String role);
 User getUserByUsername(String username);
 User getUserById(Long id);

 List<User> getAllUsers();
}