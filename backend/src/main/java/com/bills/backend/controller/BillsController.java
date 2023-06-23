package com.bills.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bills.backend.dtos.BillsDto;
import com.bills.backend.model.Bills;
import com.bills.backend.repository.BillsRepository;
import com.bills.backend.service.BillsServiceImpl;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin
public class BillsController {

    @Autowired
    private BillsRepository bRepo;

    @Autowired
    private BillsServiceImpl bServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<Bills> add(@RequestBody BillsDto billsDto) {
        try {
            System.out.println("controller\n" + billsDto.getPersons().toString());
            bServiceImpl.saveBill(new Bills(billsDto));
            return new ResponseEntity<Bills>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Bills>(HttpStatus.BAD_REQUEST);
        }
            
    }

    @PostMapping("/addteste")
    public ResponseEntity<Bills> addteste(@RequestBody Bills bills) {
        try {
            bServiceImpl.saveBill(bills);
            return new ResponseEntity<Bills>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Bills>(HttpStatus.BAD_REQUEST);
        }

    }
    
     @DeleteMapping("/user/{user}/delete/{id}")
     public ResponseEntity<?> delete(@PathVariable("user") Long userId, @PathVariable("id") Long billId) {
        Bills bill = bServiceImpl.getBillById(userId, billId);
         try {
             return ResponseEntity.ok(bServiceImpl.deleteBill(bill));
         } catch (Exception e) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
     }

//     @PutMapping("/edit/{id}")
//     public ResponseEntity<Bills> update(@PathVariable("id") Bills targetBills,  @RequestBody Bills sourceBills) {
//         try {
//             BeanUtils.copyProperties(sourceBills, targetBills, "id");
//             return ResponseEntity.ok(bServiceImpl.saveBill(targetBills));
//         } catch (Exception e) {
//             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//         }
//     }

    @GetMapping("/user/{user}/id/{id}")
    public ResponseEntity<?> getById(@PathVariable("user") Long userId, @PathVariable("id") Long id) {
        Bills bills = bServiceImpl.getBillById(userId, id);

        if(bills != null) {
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{user}")
    public ResponseEntity<List<Bills>> getAll(@PathVariable("user") Long user) {
        List<Bills> bills = bServiceImpl.getAllBills(user);
        System.out.println(bills.size());
        if(bills.size() != 0) {
            return new ResponseEntity<List<Bills>>(bills, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<Bills>> getAlls() {
        List<Bills> bills = bRepo.findAll();

        if(bills.isEmpty()) {
            return new ResponseEntity<List<Bills>>(bills, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<Bills>>(bills, HttpStatus.OK);
        }
    }
}
