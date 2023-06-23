package com.bills.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bills.backend.model.Users;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
    
    public Optional<Users> findByUsername(String username);

    public Optional<Users> findByEmail(String email);

    public Optional<Users> findByUsernameAndPassword(String username, String password);

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE users u SET u.username = ?1 WHERE u.id = ?2", nativeQuery = true)
    public void setUserUsername(String username, Long id);

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE users u SET u.password = :password WHERE u.id = :userId", nativeQuery = true)
    public void updateUserPassword(@Param("userId") Long id, @Param("password") String password);

//    @Modifying(flushAutomatically = true)
//    @Transactional
//    @Query("update Student s set s.firstName = ?1 where s.lastName= ?2")
//    public void updateFirstNameByLastName(String firstName, String lastName)
}
