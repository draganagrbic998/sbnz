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

import com.example.demo.dto.PasswordChangeDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.Constants;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('ADMIN')")	
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll(Pageable pageable, @RequestParam String search, HttpServletResponse response){
		Page<User> users = this.userService.findAll(pageable, search);
		response.setHeader(Constants.ENABLE_HEADER, Constants.FIRST_PAGE + ", " + Constants.LAST_PAGE);
		response.setHeader(Constants.FIRST_PAGE, users.isFirst() + "");
		response.setHeader(Constants.LAST_PAGE, users.isLast() + "");
		return new ResponseEntity<>(this.userMapper.map(users.toList()), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO){
		userDTO.setId(null);
		return new ResponseEntity<>(this.userMapper.map(this.userService.save(this.userMapper.map(userDTO))), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable long id, @Valid @RequestBody UserDTO userDTO){
		userDTO.setId(id);
		return new ResponseEntity<>(this.userMapper.map(this.userService.save(this.userMapper.map(id, userDTO))), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		this.userService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PutMapping(value = "/password-change")
	public ResponseEntity<Void> changePassword(@Valid @RequestBody PasswordChangeDTO passwordChangeDTO){
		this.userService.changePassword(passwordChangeDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
