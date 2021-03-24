package com.example.demo.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.AccountDTO;
import com.example.demo.model.Account;
import com.example.demo.model.Authority;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Constants;

@Component
public class AccountMapper {

	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private UserRepository userRepository;
		
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public Account map(AccountDTO accountDTO) {
		Account account = new Account();
		account.setJmbg(accountDTO.getJmbg());
		account.setBirthDate(accountDTO.getBirthDate());
		account.setAddress(accountDTO.getAddress());
		account.setCity(accountDTO.getCity());
		account.setZipCode(accountDTO.getZipCode());
		account.setBalance(1000000);
		User user = new User();
		Set<Authority> authorities = new HashSet<>();
		authorities.add(this.authorityRepository.findByName(Role.KLIJENT.name()));
		user.setAuthorities(authorities);
		user.setEmail(accountDTO.getEmail());	
		user.setPassword(this.passwordEncoder.encode(Constants.INITIAL_PASSWORD));
		user.setFirstName(accountDTO.getFirstName());
		user.setLastName(accountDTO.getLastName());
		account.setUser(user);
		user.setAccount(account);
		return account;
	}
	
	@Transactional(readOnly = true)
	public Account map(long id, AccountDTO accountDTO) {
		Account account = this.accountRepository.findById(id).get();
		account.setJmbg(accountDTO.getJmbg());
		account.setBirthDate(accountDTO.getBirthDate());
		account.setAddress(accountDTO.getAddress());
		account.setCity(accountDTO.getCity());
		account.setZipCode(accountDTO.getZipCode());
		User user = this.userRepository.findByAccountId(id);
		user.setEmail(accountDTO.getEmail());
		user.setFirstName(accountDTO.getFirstName());
		user.setLastName(accountDTO.getLastName());
		return account;
	}

	public AccountDTO map(Account account) {
		return new AccountDTO(account);
	}

	public List<AccountDTO> map(List<Account> accounts) {
		return accounts.stream().map(AccountDTO::new).collect(Collectors.toList());
	}

}
