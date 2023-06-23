package com.bills.backend.service;

import java.time.LocalDate;
import java.util.List;

import com.bills.backend.model.Person;
import com.bills.backend.model.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bills.backend.model.Bills;

@Service
public interface BillsService {


    public ResponseEntity<Bills> saveBill(Bills bill);

    // public ResponseEntity<Bills> deleteBill(Long bill);

    public ResponseEntity<?> deleteBill(Bills bill);

    public List<Bills> getAllBills(Long user);

    public Bills getBillById(Long userId, long id);

    public Bills getBillByQrCodeNumber(Users user, long qrCodeNumber);
    
    public List<Bills> getBillsByNewest(Users user);
    
    public List<Bills> getBillsOfSaidPersonByNewest(Users user, Person person);

    public List<Bills> getBillsByTypeAndNewest(Users user, String type);

    public List<Bills> getBillsByCategoryAndNewest(Users user, String category);

    public List<Bills> getUnpaidBills(Users user);

    public List<Bills> getPaidBills(Users user);
    
    public double monthExpense(Users user, LocalDate month);

    public double unpaidExpense(Users user);

    public int countUnpaid(Users user);

    public Boolean billExists(Users user, Bills bills);

    // public Double setPricePerPerson(Users user, Person person);
    
}
