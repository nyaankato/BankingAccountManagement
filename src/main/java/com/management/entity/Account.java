package com.management.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String title;
    private int balance; //actually here should be separate object - with currency type and smth like this
    @ManyToOne
    private User user;
    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL
    )
    private List<Operation> operations;
}
