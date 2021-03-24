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

import com.example.demo.dto.BillDTO;
import com.example.demo.rules.BillRequest;
import com.example.demo.rules.BillResponse;
import com.example.demo.rules.CloseResponse;
import com.example.demo.rules.IncreaseResponse;
import com.example.demo.rules.RenewalResponse;
import com.example.demo.rules.ReportResponse;
import com.example.demo.mapper.BillMapper;
import com.example.demo.model.Bill;
import com.example.demo.service.BillService;
import com.example.demo.utils.Constants;

@RestController
@RequestMapping(value = "/bills", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('KLIJENT')")	
public class BillController {

	@Autowired
	private BillService billService;
	
	@Autowired
	private BillMapper billMapper;
		
	@GetMapping
	public ResponseEntity<List<BillDTO>> findAll(@RequestParam boolean rsd, Pageable pageable, @RequestParam String search, HttpServletResponse response){
		Page<Bill> bills = this.billService.findAll(rsd, pageable, search);
		response.setHeader(Constants.ENABLE_HEADER, Constants.FIRST_PAGE_HEADER + ", " + Constants.LAST_PAGE_HEADER);
		response.setHeader(Constants.FIRST_PAGE_HEADER, bills.isFirst() + "");
		response.setHeader(Constants.LAST_PAGE_HEADER, bills.isLast() + "");
		return new ResponseEntity<>(this.billMapper.map(bills.toList()), HttpStatus.OK);
	}

	@PostMapping(value = "/terms")
	public ResponseEntity<BillResponse> terms(@Valid @RequestBody BillRequest request){
		return new ResponseEntity<>(this.billService.terms(request), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<BillResponse> create(@Valid @RequestBody BillRequest request){
		return new ResponseEntity<>(this.billService.create(request), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}/increase/{amount}")
	public ResponseEntity<IncreaseResponse> increase(@PathVariable long id, @PathVariable int amount){
		return new ResponseEntity<>(this.billService.increase(id, amount), HttpStatus.OK);
	}

	@PutMapping(value = "/{id}/renew/{amount}")
	public ResponseEntity<RenewalResponse> renew(@PathVariable long id, @PathVariable int amount){
		return new ResponseEntity<>(this.billService.renew(id, amount), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<CloseResponse> close(@PathVariable long id){
		return new ResponseEntity<>(this.billService.close(id), HttpStatus.OK);
	}

	@GetMapping(value = "/base-report")
	@PreAuthorize("hasAuthority('SLUZBENIK')")	
	public ResponseEntity<ReportResponse> baseReport(){
		return new ResponseEntity<>(this.billService.baseReport(), HttpStatus.OK);
	}

}
