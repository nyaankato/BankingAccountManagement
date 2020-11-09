package com.management.dto;

import com.management.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private long id;
    private String title;
    private int balance;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.title = account.getTitle();
        this.balance = account.getBalance();
    }
}
