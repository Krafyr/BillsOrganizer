package com.bills.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bills.backend.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

//    @Query(value = "SELECT * FROM person p" +
//            "WHERE p.user_id = :userId", nativeQuery = true)
    public List<Person> findAllByUserId(Long userId);

    @Query(value = "SELECT * FROM person p " +
            "LEFT JOIN bills b ON p.user_id = b.user_id " +
            "WHERE p.user_id = ?1 AND b.id = ?2 " +
            "ORDER BY received_date ASC", nativeQuery = true)
    public List<Person> findAllByBillsId(Long userId, Long billId);


}
