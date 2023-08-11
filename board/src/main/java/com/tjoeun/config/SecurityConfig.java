package com.tjoeun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	// @Bean : Spring Framework가 메모리에 미리 객체를 생성해놓음 - 서버 시작시
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		//.loginPage(null) 로그인패턴
		//.defaultSuccessUrl(null) 로그인 성공시 나타낼 페이지
		//.usernameParameter("email") user라고 정해져있는걸 email로 바꾼것
		//.failureUrl("/members/login/error")로그인 실패시 이동할 url
		//and는 조건추가
		//.logoutRequestMatcher(new AntPathRequestMatcher("")) 로그아웃했을때 이동할 url패턴
		//.logoutSuccessUrl("/"); 로그아웃 성공시 이동할
		
		
		http.formLogin()
			.loginPage("/users/login")
			.defaultSuccessUrl("/")
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
			.logoutSuccessUrl("/");
			//logoutRequestMatcher(new AntPathRequestMatcher 이게 로그아웃 구현안해도 저 맵핑값을 받으면
		//로그아웃이 되도록한다
		
		//이것을 안하면 시큐리티 토큰값 에러가 날 수 있다
		http.authorizeHttpRequests()
			.mvcMatchers("/**").permitAll()
			.mvcMatchers("/css/**","/js/**","/images/**").permitAll()
			.mvcMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated();
		
		http.exceptionHandling()
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		
		return http.build();
	}
	
	//회원 가입시 입력한 비밀번호 암호화처리하기
	//PasswordEncoder - 조상 . new BCryptPasswordEncoder() - 자손
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
