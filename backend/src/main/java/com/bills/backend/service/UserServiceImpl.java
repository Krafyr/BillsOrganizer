package com.bills.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bills.backend.model.Users;
import com.bills.backend.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository uRepo;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public UserServiceImpl() {
    }

    @Override
    public ResponseEntity<Users> saveUser(Users user) {
        try {
            if(isEmailValid(user.getEmail())) {
                uRepo.save(user);
                return new ResponseEntity<Users>(HttpStatus.ACCEPTED);
            } else {
                throw new Exception("Invalid email");
            }
        } catch (Exception e) {
            return new ResponseEntity<Users>(HttpStatus.BAD_REQUEST);
        }
    }



    @Override
    public ResponseEntity<Users> deleteUser(Users user) {
        try {
            uRepo.delete(user);
            return new ResponseEntity<Users>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Users>(HttpStatus.BAD_REQUEST);
        }
        
    }

    

    @Override
    public Optional<Users> getUserByUsernameAndPassword(String username, String password) {
        return uRepo.findByUsernameAndPassword(username, password);
    }

    @Override
    public List<Users> getAllUsers() {
        return uRepo.findAll();
    }

    @Override
    public Users getUserById(long id) {
        return uRepo.findById(id).get();
    }

    @Override
    public Optional<Users> getUserByUsername(String username) {
        return uRepo.findByUsername(username);
    }

    @Override
    public Optional<Users> getUserByEmail(String email) {
        return uRepo.findByEmail(email);
    }

    public void setUserUsername (String username, Long id) {
        uRepo.setUserUsername(username, id);
    }

    public void updateUserPassword(Long id, String password) {
        String newPassword = passwordEncoder().encode(password);
        uRepo.updateUserPassword(id, newPassword);
    }

    @Override
    public Boolean isEmailValid(String email) {
        return Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
            .matcher(email)
            .matches();
    }


}
