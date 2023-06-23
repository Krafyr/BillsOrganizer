package com.bills.backend.repository;

import java.time.LocalDate;
import java.util.List;

import com.bills.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bills.backend.model.Bills;
import com.bills.backend.model.Users;
import org.springframework.transaction.annotation.Transactional;


// public interface ersonBill {
//     String teste();

// }

@Repository
public interface BillsRepository extends JpaRepository<Bills, Long>{

    public Bills findByUserIdAndId(Long userId, Long id);

    
    public void deleteById(Long id);

    @Query(value = "SELECT * FROM bills " +
            "WHERE user_id = ?1", nativeQuery = true)
    public List<Bills> findAllByUserId(Long userId);

    @Query(value = "SELECT * FROM bills " +
            "WHERE user_id = ?1 " +
            "AND qr_code_number = ?2", nativeQuery = true)
    public Bills findByQrCodeNumber(Users user, Long qrCodeNumber);

    @Query(value = "SELECT * FROM bills " +
            "WHERE user_id = ?1 " +
            "AND received_date = ?2 " +
            "ORDER BY received_date ASC", nativeQuery = true)
    public List<Bills> findAllByReceivedDateOrderByReceivedDateAsc(Users user, LocalDate date);

    @Query(value = "SELECT * FROM bills " +
            "WHERE user_id = ?1 " +
            "ORDER BY received_date ASC", nativeQuery = true)
    public List<Bills> findAllBillsOrderByReceivedDateASC(Users user);

    @Query(value = "SELECT * FROM bills b JOIN person p WHERE b.user_id = ?1 AND p.id = ?2 ORDER BY received_date ASC", nativeQuery = true)
    public List<Bills> findAllByPersonOrderByReceivedDateASC(Users user, Person persons);

    @Query(value = "SELECT * FROM bills " +
            "WHERE user_id = ?1 " +
            "AND type = ?2 " +
            "AND paid = false " +
            "ORDER BY received_date ASC", nativeQuery = true)
    public List<Bills> findAllByTypeOrderByReceivedDateASC(Users user, String type);

    @Query(value = "SELECT * FROM bills " +
            "WHERE user_id = ?1 " +
            "AND paid = false " +
            "ORDER BY received_date ASC", nativeQuery = true)
    public List<Bills> findAllByCategoryOrderByReceivedDateASC(Users user, String category);

    @Query(value = "SELECT * FROM bills " +
            "WHERE user_id = ?1 " +
            "AND paid = false", nativeQuery = true)
    public List<Bills> findAllUnpaidBills(Users user);

    @Query(value = "SELECT * FROM bills " +
            "WHERE user_id = ?1 " +
            "AND paid = true", nativeQuery = true)
    public List<Bills> findAllByPaidTrue(Users user);

    @Query(value = "SELECT SUM(b.price) FROM bills b " +
            "WHERE b.user_id = ?1 " +
            "AND b.received_date = ?2", nativeQuery = true)
    public double monthExpense (Users user, LocalDate month);

    @Query(value = "SELECT SUM(b.price) FROM bills b " +
            "WHERE b.user_id = ?1 " +
            "AND b.paid = false", nativeQuery = true)
    public double unpaidExpense(Users user);

    @Query(value = "SELECT COUNT(b.paid) FROM bills b " +
            "WHERE b.user_id = ?1 " +
            "AND b.paid = false", nativeQuery = true)
    public int countUnpaid(Users user);

    @Query(value = "SELECT * FROM bills b " +
            "WHERE b.user_id = ?1", nativeQuery = true)
    public List<Bills> findAll(Users user);

    @Query(value = "SELECT SUM(b.price_per_person) " +
            "AS total_to_pay FROM bills b " +
            "JOIN person p " +
            "WHERE b.user_id = ?1 " +
            "AND b.paid = false " +
            "AND p.id", nativeQuery = true)
    public Double totalToPay(Users user, Person person);

    @Query(value = "SELECT COUNT(person_id) FROM person_bills_table " +
            "WHERE bills_id = ?1", nativeQuery = true)
    public int countPersonsToPay(Bills bills);

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE bills b SET u.password = :password WHERE u.id = :userId", nativeQuery = true)
    public void updateUserPassword(@Param("userId") Long id, @Param("password") String password);

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query(value = "INSERT INTO person_bills (bills_id, person_id) VALUES ( :billsId, :userId)", nativeQuery = true)
    public void addPersonToBill(@Param("billsId") Long billsId, @Param("userId") Long userId);
}



// class personBill {
//     String personName;
//     Double totalToPay;
//     public personBill(String personName, Double totalToPay) {
//         this.personName = personName;
//         this.totalToPay = totalToPay;
//     }
// }