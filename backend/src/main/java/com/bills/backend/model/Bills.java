package com.bills.backend.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.DynamicUpdate;

import com.bills.backend.dtos.BillsDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class Bills {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    // private String type;
    // private String category;

    @Column(nullable = false)
    private Double price;

    @Column(name = "price_per_person")
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

    @JsonBackReference(value = "bills-user")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "person_bills_table",
        joinColumns = {@JoinColumn(name = "bills_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "person_id", referencedColumnName = "id")})
    private Set<Person> persons;

    public Bills(String name, Double price, Double pricePerPerson,Boolean paid, Users user) {
        this.name = name;
        this.price = price;
        this.pricePerPerson = pricePerPerson;
        this.paid = false;
        this.user = user;
    }

    public Bills(BillsDto billsDto) {
        this.name = billsDto.getName();
        this.price = billsDto.getPrice();
        this.pricePerPerson = billsDto.getPrice();
        this.paid = billsDto.getPaid();
        this.user = new Users(billsDto.getUser());
    }

    // @Override
    // public int hashCode() {
    //     return getClass().hashCode();
    // }

     public void addPerson(Person person) {
         persons.add(person);
         person.getBills().add(this);
     }

    // public void removePerson(Person person) {
    //     persons.remove(person);
    //     person.setBills(null);
    // }

    // public Bills(String name, String type, String category, Double price, LocalDate receivedDate, LocalDate dueDate, Boolean paid, Users user) {
    //     this.name = name;
    //     this.type = type;
    //     this.category = category;
    //     this.price = price;
    //     this.receivedDate = LocalDate.now();
    //     this.dueDate = dueDate;
    //     this.paid = false;
    //     this.user = user;
    // }


    

}
