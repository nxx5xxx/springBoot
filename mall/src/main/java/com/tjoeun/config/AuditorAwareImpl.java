package com.tjoeun.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String>{
	@Override
	public Optional<String> getCurrentAuditor() {
		// Auditing 기능은 인증 정보를 가져온 후 사용한다
		
		//인증 정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String userId="";
		
		// 인증정보를 갖고온 후 userId를 얻어오기
		if(authentication !=null) {
			userId = authentication.getName();
			//로그4j랑 똑같다
			log.info(">>>>>>회원 : "+userId);
		}
		
		// 널포인터 익셉션을 방지하기 위해 옵셔널을 사용
		//이렇게하면 userId가 있는 경우에는 userId를 갖고가고
		// 없다고 해도 Optional 객체가 반환되므로 널포인터익셉션이 발생하지 않는다
		//이 부분에서 옵셔널을 사용하지않고 이프문으로 처리해도되나 코드가 길어진다
		return Optional.of(userId);
	}
}
