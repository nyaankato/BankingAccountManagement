package com.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.dto.AccountDto;
import com.management.dto.OperationDto;
import com.management.dto.PaymentDto;
import com.management.entity.Account;
import com.management.entity.Operation;
import com.management.service.AccountService;
import com.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@ResponseStatus(HttpStatus.OK)
public class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/{userId}/accounts")
    public List<AccountDto> getAllAccountsForUser(@PathVariable Long userId) {
        return accountService.getAllAccountsByUserId(userId).stream().map(AccountDto::new).collect(Collectors.toList());
    }

    @DeleteMapping("/{userId}/accounts/{accountId}")
    public void deleteAccountById(@PathVariable Long accountId) {
        accountService.removeAccount(accountId);
    }

    @PostMapping("/{userId}/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccountForUser(@RequestBody String accountTitle, @PathVariable Long userId) {
        return new AccountDto(accountService.createAccountForUser(userId, accountTitle));
    }

    @PostMapping("/accounts/{accountId}/deposit")
    public double depositFunds(@RequestBody PaymentDto payment, @PathVariable Long accountId) {
        return accountService.depositFundsToAccount(accountId, payment.getAmount());
    }

    @PostMapping("/accounts/{accountId}/withdraw")
    public double withdrawFunds(@RequestBody PaymentDto payment, @PathVariable Long accountId) {
        return accountService.withdrawFundsFromAccount(accountId, payment.getAmount());
    }

    @GetMapping("/accounts/{accountId}/balance")
    public double getBalanceForAccount(@PathVariable Long accountId) {
        return accountService.getAccountBalance(accountId);
    }

    @GetMapping("/{userId}/accounts/balance")
    public double getAccountsTotal(@PathVariable Long userId) {
        return accountService.getTotalBalanceByUserId(userId);
    }

    @GetMapping("/{userId}/accounts/{accountId}/operations")
    public List<OperationDto> getOperationHistoryForAccount(@PathVariable Long accountId) {
        return accountService.getAccountOperations(accountId).stream().map(OperationDto::new).collect(Collectors.toList());
    }

}
