package com.bills.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bills.backend.model.Users;




@Service
public interface UserService {
    
    public ResponseEntity<Users> saveUser(Users user);

    public ResponseEntity<Users> deleteUser(Users user);

    public List<Users> getAllUsers();

    public Users getUserById(long id);

    public Optional<Users> getUserByUsername(String username);

    public Optional<Users> getUserByEmail(String email);

    public Boolean isEmailValid(String email);

    public Optional<Users> getUserByUsernameAndPassword(String username, String password);

    public void setUserUsername (String username, Long id);

    public void updateUserPassword(Long id, String password);
}
