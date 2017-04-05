package com.tts.codelab.account.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.codelab.account.domain.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

	Account findByUserName(String name);
}
