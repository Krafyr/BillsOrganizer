package com.bills.backend.model;

import java.util.Set;

import com.bills.backend.dtos.PersonDto;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
@DynamicUpdate
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Person {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double totalToPay;

    @JsonBackReference(value = "person-user")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @JsonIgnore
    @ManyToMany(mappedBy = "persons", fetch = FetchType.EAGER)
    private Set<Bills> bills;

    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // public Person() {
    // }

    public Person(String name, Users user) {
        this.name = name;
        this.totalToPay = 0.0;
        this.user = user;
    }

    public Person(PersonDto personDto) {
        this.name = personDto.getName();
        this.totalToPay = personDto.getTotalToPay();
        this.user = new Users(personDto.getUser());
    }

    // public long getId() {
    //     return id;
    // }

    // public String getName() {
    //     return name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }

    // public double getTotalToPay() {
    //     return totalToPay;
    // }
    
    // public void setTotalToPay(double totalToPay) {
    //     this.totalToPay = totalToPay;
    // }

    // public User getUser() {
    //     return user;
    // }

    // public void setUser(User user) {
    //     this.user = user;
    // }

    // public Bills getBills() {
    //     return bills;
    // }

    // public void setBills(Bills bills) {
    //     this.bills = bills;
    // }

    

}
