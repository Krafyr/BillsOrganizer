package com.bills.backend.dtos;

import java.util.Set;

import com.bills.backend.model.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private Long id;

    private String name;

    private Double totalToPay;

    private UsersDto user;


    private Set<BillsDto> bills;

    // public Person() {
    // }

    public PersonDto(Long id, String name, UsersDto user) {
        this.id = id;
        this.name = name;
        this.totalToPay = 0.0;
        this.user = user;
    }

    public PersonDto(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.totalToPay = person.getTotalToPay();
        this.user = new UsersDto(person.getUser());
    }
}
