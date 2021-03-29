package com.example.demo.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.Authority;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.UserRepository;

@Component
public class UserMapper {

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private UserRepository userRepository;
		
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public User map(UserDTO userDTO) {
		User user = new User();
		Set<Authority> authorities = new HashSet<>();
		authorities.add(this.authorityRepository.findByName(Role.SLUZBENIK.name()));
		user.setAuthorities(authorities);
		user.setEmail(userDTO.getEmail());
		user.setPassword(this.passwordEncoder.encode(UUID.randomUUID().toString()));
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		return user;
	}
	
	@Transactional(readOnly = true)
	public User map(long id, UserDTO userDTO) {
		User user = this.userRepository.findById(id).get();
		user.setEmail(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		return user;
	}

	public UserDTO map(User user) {
		return new UserDTO(user);
	}

	public List<UserDTO> map(List<User> users){
		return users.stream().map(UserDTO::new).collect(Collectors.toList());
	}
	
}
