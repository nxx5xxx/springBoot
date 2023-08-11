package com.tjoeun.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tjoeun.constant.UserRole;
import com.tjoeun.entity.Users;
import com.tjoeun.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersSecurityService implements UserDetailsService {

	private final UsersRepository usersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//더블콜론은 메소드 참조하는 메소드
//		Users users = usersRepository.findByusername(username).orElseThrow(EntityNotFoundException::new);
//		==
		Optional<Users> tmpUsers = usersRepository.findByusername(username);
		
		//로그인 하려는 회원이 아직 가입 안 한 상태일 경우
		if(tmpUsers.isEmpty()) {
			throw new UsernameNotFoundException("없는 아이디입니다");
		}
		
		//로그인 성공
		Users users = tmpUsers.get(); //db에 있으면 꺼내란의미
		
		//ADMN인지 USER인지 확인하기
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		}else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		UserDetails user = new User(users.getUsername(), users.getPassword(), authorities);
		return user;
	}
}
