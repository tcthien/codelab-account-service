package com.tts.codelab.account.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.tts.codelab.account.client.CodelabAuthServiceClient;
import com.tts.codelab.account.domain.Account;
import com.tts.codelab.account.domain.User;
import com.tts.codelab.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private CodelabAuthServiceClient authClient;

	@Autowired
	private AccountRepository repository;

	@Override
	public Account findByName(String accountName) {
		Assert.hasLength(accountName);
		return repository.findByName(accountName);
	}

	@Override
	public Account create(Account account) {

		Account existing = repository.findByName(account.getUserName());
		Assert.isNull(existing, "account already exists: " + account.getUserName());

		// Extract user information from account & create new user
		User user = new User();
		user.setEmail(account.getEmail());
		user.setFullName(account.getFullName());
		user.setPassword("123456");
		user.setUsername(account.getUserName());
		authClient.createUser(user);

		repository.save(account);

		log.info("new account has been created: " + account.getUserName());

		return account;
	}

	@Override
	public void saveChanges(String name, Account update) {

		Account account = repository.findByName(name);
		Assert.notNull(account, "can't find account with name " + name);

        User user = new User();
        user.setEmail(update.getEmail());
        user.setUsername(update.getUserName());
        user.setFullName(update.getFullName());
        authClient.updateUser(user);

		account.setFullName(update.getFullName());
		account.setProject(update.getProject());
		account.setNote(update.getNote());
		account.setLastSeen(new Date());
		repository.save(account);

		log.debug("account {} changes has been saved", name);
	}
}
