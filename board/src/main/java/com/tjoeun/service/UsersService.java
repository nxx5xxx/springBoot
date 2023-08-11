package com.tjoeun.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tjoeun.entity.Users;
import com.tjoeun.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {
	
	private final UsersRepository usersRepository;
	
	//방법2
	private final PasswordEncoder passwordEncoder2;
	
	public Users createUsers(String username,String password,String email) {
		Users users = new Users();
		users.setUsername(username);
		users.setEmail(email);
		
		//비밀번호 암호화해서 DB에 저장하기
		//방법1
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		users.setPassword(passwordEncoder.encode(password));
		
		usersRepository.save(users);
		
		
		return users;
	}
	
	public Users getUsers(String username) {
		Users users = usersRepository.findByusername(username).orElseThrow(()-> new EntityNotFoundException("해당 회원이 없습니다"));
		
		
//		Optional<Users> users = usersRepository.findByusername(username);
		
//		if(users.isPresent()) {
//			return users.get();
//		}else {
//			throws new EntityNotFoundException();
//		}
		return users;
	}
}
