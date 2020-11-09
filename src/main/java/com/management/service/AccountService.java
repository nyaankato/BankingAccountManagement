package com.management.service;

import com.management.entity.Account;
import com.management.entity.Operation;
import com.management.entity.User;
import com.management.exception.CommonException;
import com.management.exception.NotFoundException;
import com.management.repository.AccountRepository;
import com.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Account> getAllAccountsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        return user.getAccounts();
    }

    @Transactional
    public Account createAccountForUser(Long userId, String accountTitle) {
        User user = userRepository.findById(userId).orElseThrow();
        Account account = Account.builder().title(accountTitle).user(user).build();
        return accountRepository.save(account);
    }

    public void removeAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public double depositFundsToAccount(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(NotFoundException::new);
        if (amount * 100 < 0)
            throw new CommonException("Negative deposit");
        int newBalance = (int) (account.getBalance() + amount * 100);
        account.setBalance(newBalance);
        account.getOperations().add(
                Operation.builder()
                        .date(new Date())
                        .type(Operation.OperationType.DEPOSIT)
                        .account(account)
                        .build());
        accountRepository.save(account);
        return account.getBalance() / (double) 100;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public double withdrawFundsFromAccount(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(NotFoundException::new);
        int newBalance = (int) (account.getBalance() - amount * 100);
        if (newBalance < 0)
            throw new CommonException("Balance cannot be negative");
        account.setBalance(newBalance);
        account.getOperations().add(
                Operation.builder()
                        .date(new Date())
                        .type(Operation.OperationType.WITHDRAWAL)
                        .account(account)
                        .build());
        accountRepository.save(account);
        return account.getBalance() / (double) 100;
    }

    public double getAccountBalance(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow().getBalance() / (double) 100;
    }

    public List<Operation> getAccountOperations(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow().getOperations();
    }

    public double getTotalBalanceByUserId(Long userId) {
        List<Account> accounts = getAllAccountsByUserId(userId);
        return accounts.stream().mapToDouble(Account::getBalance).sum() / (double) 100;

    }
}
