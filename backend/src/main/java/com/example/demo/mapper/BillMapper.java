package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.dto.BillDTO;
import com.example.demo.model.Bill;

@Component
public class BillMapper {
	
	public List<BillDTO> map(List<Bill> bills) {
		return bills.stream().map(BillDTO::new).collect(Collectors.toList());
	}
	
}
