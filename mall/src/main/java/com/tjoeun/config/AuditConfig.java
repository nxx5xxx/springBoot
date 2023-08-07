package com.tjoeun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// EnableJpaAuditing : Auditing기능을 하게끔하는 어노테이션--감시기능
@Configuration
@EnableJpaAuditing
public class AuditConfig {
	@Bean
	public AuditorAware<String> auditorProvider(){
		// 리턴타입 조상으로 리턴값은 자식으로 할 수 있다
		return new AuditorAwareImpl();
	}
}
