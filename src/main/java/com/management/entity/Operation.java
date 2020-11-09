package com.management.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public enum OperationType {
        DEPOSIT, WITHDRAWAL
    }
    private OperationType type;
    private Date date;
    @ManyToOne
    private Account account;
}
