package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AccountDTO;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import com.example.demo.utils.Constants;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('SLUZBENIK')")	
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@GetMapping
	public ResponseEntity<List<AccountDTO>> findAll(Pageable pageable, @RequestParam String search, HttpServletResponse response){
		Page<Account> accounts = this.accountService.findAll(pageable, search);
		response.setHeader(Constants.ENABLE_HEADER, Constants.FIRST_PAGE_HEADER + ", " + Constants.LAST_PAGE_HEADER);
		response.setHeader(Constants.FIRST_PAGE_HEADER, accounts.isFirst() + "");
		response.setHeader(Constants.LAST_PAGE_HEADER, accounts.isLast() + "");
		return new ResponseEntity<>(this.accountMapper.map(accounts.toList()), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<AccountDTO> create(@Valid @RequestBody AccountDTO accountDTO){
		accountDTO.setId(null);
		return new ResponseEntity<>(this.accountMapper.map(this.accountService.save(this.accountMapper.map(accountDTO))), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<AccountDTO> update(@PathVariable long id, @Valid @RequestBody AccountDTO accountDTO){
		accountDTO.setId(id);
		return new ResponseEntity<>(this.accountMapper.map(this.accountService.save(this.accountMapper.map(id, accountDTO))), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		this.accountService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PreAuthorize("hasAuthority('KLIJENT')")	
	@GetMapping(value = "/my")
	public ResponseEntity<AccountDTO> myAccount(){
		return new ResponseEntity<AccountDTO>(this.accountMapper.map(this.accountService.myAccount()), HttpStatus.OK);
	}

	@GetMapping(value = "/report-1")
	public ResponseEntity<List<AccountDTO>> firstReport(){
		return new ResponseEntity<>(this.accountMapper.map(this.accountService.advancedReport(1)), HttpStatus.OK);
	}
		
	@GetMapping(value = "/report-2")
	public ResponseEntity<List<AccountDTO>> secondReport(){
		return new ResponseEntity<>(this.accountMapper.map(this.accountService.advancedReport(2)), HttpStatus.OK);
	}

	@GetMapping(value = "/report-3")
	public ResponseEntity<List<AccountDTO>> thirdReport(){
		return new ResponseEntity<>(this.accountMapper.map(this.accountService.advancedReport(3)), HttpStatus.OK);
	}

}
