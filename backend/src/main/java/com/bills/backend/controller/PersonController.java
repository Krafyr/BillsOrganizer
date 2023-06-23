package com.bills.backend.controller;

import com.bills.backend.dtos.PersonDto;
import org.springframework.beans.BeanUtils;
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

import com.bills.backend.model.Person;
import com.bills.backend.repository.PersonRepository;
import com.bills.backend.service.PersonServiceImpl;

import java.util.List;


@RestController
@RequestMapping("/api/person")
@CrossOrigin
public class PersonController {

    @Autowired
    private PersonServiceImpl pServiceImpl;

    @Autowired
    private PersonRepository pRepo;


    @PostMapping("/add")
    public ResponseEntity<Person> add(@RequestBody PersonDto personDto) {
        try {
            pServiceImpl.savePerson(new Person(personDto));
            return new ResponseEntity<Person>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Person person) {
        try {
            return ResponseEntity.ok(pServiceImpl.deletePerson(person));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Person> update(@PathVariable("id") Person targetPerson,  @RequestBody Person sourcePerson) {
        try {
            BeanUtils.copyProperties(sourcePerson, targetPerson, "id");
            return ResponseEntity.ok(pRepo.save(targetPerson));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getById(@PathVariable("userId") Long userId) {
        List<Person> persons = pServiceImpl.findAllByUser(userId);

        if(persons != null) {
            return new ResponseEntity<>(persons, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get/{userId}/id/{id}")
    public ResponseEntity<?> getById(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
        List<Person> persons = pServiceImpl.getPersonsByBillId(userId, id);

        if(persons != null) {
            return new ResponseEntity<>(persons, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
