package com.yoga.atm.app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.yoga.atm.app.model.Account;

public interface AccountRepository extends CrudRepository<Account, String> {

	List<Account> findByAccountNumberAndPin(String accountNumber, String pin);

	List<Account> findByAccountNumber(String accountNumber);

}