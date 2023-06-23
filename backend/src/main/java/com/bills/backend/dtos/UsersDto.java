package com.bills.backend.dtos;

import java.util.Set;

import com.bills.backend.model.Users;

import com.bills.backend.service.UserServiceImpl;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersDto {
    
    private Long id; 

    private String username;

    private String email;

    private String password;

    // @Enumerated(EnumType.STRING)
    // private Role role;

    private Set<BillsDto> bills;

    private Set<PersonDto> persons;

    private UserServiceImpl userSI;

    public UsersDto(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UsersDto(Users user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
