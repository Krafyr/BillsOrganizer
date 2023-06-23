package com.bills.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bills.backend.model.Users;
import com.bills.backend.repository.UserRepository;
import com.bills.backend.service.UserServiceImpl;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository uRepo;

    @Autowired
    private UserServiceImpl uServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<Users> add(@RequestBody Users user) {
        try {
            if (uServiceImpl.getUserByUsername(user.getUsername()).isEmpty()) {
                uServiceImpl.saveUser(user);
                return new ResponseEntity<Users>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Users>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Users> get(@PathVariable("id") long id) {
        Users user = uServiceImpl.getUserById(id);
        
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/get/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
        Optional<Users> user = uServiceImpl.getUserByUsername(username);

        if(user.isPresent()) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = uServiceImpl.getAllUsers();

        if(users.isEmpty()) {
            return new ResponseEntity<List<Users>>(users, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<Users>>(users, HttpStatus.OK);
        }
    }

    @GetMapping("/get/all")
    public void getAll() {
        List<Users> users = uServiceImpl.getAllUsers();

        for (Users user : users) {
           System.out.println(user); 
        }
        
        
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Users user) {
        try {
            return ResponseEntity.ok(uServiceImpl.deleteUser(user));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/username/{username}")
    public ResponseEntity<?> deleteByUsername(@PathVariable("username") Users user) {
        try {
            return ResponseEntity.ok(uServiceImpl.deleteUser(user));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PutMapping("/edit/{id}")
//    public ResponseEntity<Users> update(@PathVariable("id") Long sourceUser,  @RequestBody Users targetUser) {
//        try {
//            BeanUtils.copyProperties(uServiceImpl.getUserById(sourceUser), targetUser, "username");
//            return ResponseEntity.ok(uRepo.saveAndFlush(targetUser));
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @PutMapping("/edit/{id}/{username}")
    public void update(@PathVariable("id") Long id,  @PathVariable("username") String username) {
        uServiceImpl.setUserUsername(username, id);
    }

    @PutMapping("/edit/{id}/pass/{password}")
    public void updatePassword(@PathVariable("id") Long id,  @PathVariable("password") String password) {
        uServiceImpl.updateUserPassword(id, password);
    }
}
