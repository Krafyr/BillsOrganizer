package com.bills.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bills.backend.model.Bills;
import com.bills.backend.model.Person;
import com.bills.backend.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository pRepository;

    @Override
    public ResponseEntity<Person> savePerson(Person person) {
        try {
            pRepository.saveAndFlush(person);
            return new ResponseEntity<Person>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
        }
        
    }

    @Override
    public ResponseEntity<Person> deletePerson(Person person) {
        try {
            pRepository.delete(person);
            return new ResponseEntity<Person>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
        }
        
    }

    @Override
    public Person getPersonById(long id) {
        return pRepository.findById(id).orElse(null);
    }

    public List<Person> findAllByUser(@Param("userId") Long userId) {
        return pRepository.findAllByUserId(userId);
    }

    @Override
    public List<Person> getPersonsByBill(Bills bill) {
        return null;
    }

    @Override
    public List<Person> getPersonsByBillId(Long userId, Long billId) {
        return pRepository.findAllByBillsId(userId, billId);
    }
}
