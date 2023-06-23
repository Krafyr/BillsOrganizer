package com.bills.backend.dtos;


import java.util.Set;

import com.bills.backend.model.Bills;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BillsDto {
    
    private Long id;

    private String name;
    // private String type;
    // private String category;

    private Double price;

    private Double pricePerPerson;

    // @Column(name = "received_date", nullable = false)
    // private LocalDate receivedDate;

    // @Column(name = "due_date")
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy")
    // private LocalDate dueDate;

    //Unique para que o usuário não coloque o mesmo código 2 vezes
    // @Column(name = "qr_code_number", unique = true)
    // private Long qrCodeNumber;

    private Boolean paid;

    private UsersDto user;
    
    private Set<PersonDto> persons;

    public BillsDto(Long id, String name, Double price, Double pricePerPerson, Boolean paid, UsersDto user) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.pricePerPerson = pricePerPerson;
        this.paid = false;
        this.user = user;
    }

    public BillsDto(Bills bills) {
        this.id = bills.getId();
        this.name = bills.getName();
        this.price = bills.getPrice();
        this.pricePerPerson = bills.getPricePerPerson();
        this.paid = bills.getPaid();
        this.user = new UsersDto(bills.getUser());
    }
}
