package com.bills.backend.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bills.backend.model.Bills;
import com.bills.backend.model.Person;

@Service
public interface PersonService {

    public ResponseEntity<Person> savePerson(Person person);

    public ResponseEntity<Person> deletePerson(Person person);

    //public ResponseEntity<Person> updatePerson(Person person);

    public List<Person> findAllByUser(Long userId);

    public List<Person> getPersonsByBill(Bills bill);

    public Person getPersonById(long id);

    public List<Person> getPersonsByBillId(Long userId, Long billId);

}
