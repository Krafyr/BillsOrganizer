package com.bills.backend.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import com.bills.backend.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bills.backend.model.Bills;
import com.bills.backend.model.Person;
import com.bills.backend.repository.BillsRepository;


@Service
public class BillsServiceImpl implements BillsService{


    @Autowired
    private BillsRepository bRepo;

    @Autowired
    private PersonServiceImpl pService;

    @Override
    public ResponseEntity<Bills> saveBill(Bills bills) {
        try {
            bRepo.save(bills);
            return new ResponseEntity<Bills>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Bills>(HttpStatus.BAD_REQUEST);
        }
        
    }

     @Override
     public ResponseEntity<Bills> deleteBill(Bills bills) {
         try {
             bRepo.delete(bills);
             return new ResponseEntity<Bills>(HttpStatus.OK);
         } catch (Exception e) {
             return new ResponseEntity<Bills>(HttpStatus.BAD_REQUEST);
         }
     }

    @Override
    public List<Bills> getAllBills(Long user) {
        return bRepo.findAllByUserId(user);
    }

    @Override
    public Bills getBillById(Long userId, long id) {
        return bRepo.findByUserIdAndId(userId, id);
    }

    @Override
    public Bills getBillByQrCodeNumber(Users user, long qrCodeNumber) {
        return bRepo.findByQrCodeNumber(user, qrCodeNumber);
    }

    @Override
    public List<Bills> getBillsByNewest(Users user) {
        return bRepo.findAllBillsOrderByReceivedDateASC(user);
    }

    @Override
    public List<Bills> getBillsByTypeAndNewest(Users user, String type) {
        return bRepo.findAllByTypeOrderByReceivedDateASC(user, type);
    }

    @Override
    public List<Bills> getBillsByCategoryAndNewest(Users user, String category) {
        return null;
    }

    @Override
    public List<Bills> getUnpaidBills(Users user) {
        return null;
    }

    @Override
    public List<Bills> getBillsOfSaidPersonByNewest(Users user, Person person) {
        return bRepo.findAllByPersonOrderByReceivedDateASC(user, person);
    }

    @Override
    public List<Bills> getPaidBills(Users user) {
        return bRepo.findAllByPaidTrue(user);
    }

    @Override
    public double monthExpense(Users user, LocalDate date) {
        return bRepo.monthExpense(user, date);
    }

    @Override
    public double unpaidExpense(Users user) {
        return bRepo.unpaidExpense(user);
    }

    @Override
    public int countUnpaid(Users user) {
        return bRepo.countUnpaid(user);
    }

    @Override
    public Boolean billExists(Users user, Bills bills) {
        return bRepo.findAll(user).size() > 0;
    }

    public double calculatePricePerPerson(List<Person> persons, Bills bill) {
        DecimalFormat df = new DecimalFormat("0.00");

        double billsPrice = bill.getPrice();
        billsPrice = billsPrice / persons.size();
        billsPrice = Double.parseDouble(df.format(billsPrice));

        return billsPrice;
    }

//    public double calculatePricePerPerson(Bills bill) {
//        DecimalFormat df = new DecimalFormat("0.00");
//
//
//        double billsPrice = bill.getPrice();
//        billsPrice = billsPrice / persons;
//        billsPrice = Double.parseDouble(df.format(billsPrice));
//
//        return billsPrice;
//    }

    // public Double setPricePerPerson(Users user, Person person, Long id) {
    //     DecimalFormat df = new DecimalFormat("0.00");

    //     Double pricePerPerson = bRepo.totalToPay(user, person);

    //     return 10.0;
    // }


    public void setPersonExpenses(Bills bill) {
        List<Person> persons = pService.getPersonsByBill(bill);

        bill.setPricePerPerson(calculatePricePerPerson(persons, bill));

        for(Person person : persons) {
            person.setTotalToPay(person.getTotalToPay() + bill.getPricePerPerson());
        }
    }

    public ResponseEntity<Bills> payBill(Bills bill) {

        if(bill.getPaid().equals(true)) {
            return new ResponseEntity<Bills>(HttpStatus.BAD_REQUEST);
        } else {
            List<Person> persons = pService.getPersonsByBill(bill);

            for(Person person : persons) {
                person.setTotalToPay(person.getTotalToPay() - bill.getPricePerPerson());
            }

            bill.setPaid(true);

            return new ResponseEntity<Bills>(HttpStatus.OK);
        }
    }
}
