package com.management.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch=FetchType.LAZY
    )
    private List<Account> accounts;
}
