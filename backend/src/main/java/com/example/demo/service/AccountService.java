package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.MyException;
import com.example.demo.model.Account;
import com.example.demo.model.BillStatus;
import com.example.demo.repository.AccountRepository;

@Service
@Transactional(readOnly = true)
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResonerService resonserService;
		
	public Page<Account> findAll(Pageable pageable, String search) {
		return this.accountRepository.findAll(pageable, search);
	}

	@Transactional(readOnly = false)
	public Account save(Account account) {
		this.userService.save(account.getUser());
		return this.accountRepository.save(account);
	}

	@Transactional(readOnly = false)
	public void delete(long id) {
		Account account = this.accountRepository.findById(id).get();
		if (account.getBills().stream().filter(bill -> bill.getStatus().equals(BillStatus.ACTIVE)).count() > 0) {
			throw new MyException();
		}
		this.accountRepository.deleteById(id);
	}

	public Account myAccount() {
		return this.accountRepository.findByUserId(this.userService.currentUser().getId());
	}
	
	public List<Account> advancedReport(int reportNumber){
		return this.resonserService.advancedReport(this.accountRepository.findAll(), reportNumber);
	}

}
