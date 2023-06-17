package info.myone.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {
	
	/*
	 BCrypt 암호화에 대한 정리
	 1. 암호화 방식의 분류
	   1) 양방향 : 복호화 가능
	     - 대칭키 
	       => 암/복호화 비밀키 동일
	     - 비대칭키(공개키)
	       => 암/복호화 비밀키 같지 않음
	   2) 단방향 : 복호화 불가능
	   
	 2. BCrypt
	   - SHA 알고리즘과 마찬가지로 복호화가 불가능한 단방향 알고리즘
	   - 해시 알고리즘 중 가장 강력한 암호화 방식
	   - salt를 적용하여 암호화 함
	 */

	private PasswordEncoder passwordEncoder;

	@BeforeEach
	public void setUp() throws Exception {
		// bcrypt 암호화 방식
		passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Test
	public void encode() {
		String password = "password1234";

		String encPassword1 = passwordEncoder.encode(password);

		assertThat(passwordEncoder.matches(password, encPassword1)).isTrue();
		assertThat(encPassword1).contains("{bcrypt}");

		String encPassword2 = passwordEncoder.encode(password);

		assertThat(passwordEncoder.matches(password, encPassword2)).isTrue();
		assertThat(encPassword2).contains("{bcrypt}");

		// salt가 적용되어 암호화 하므로 동일한 raw 값이라도 인코딩한 값은 달라야함
		assertThat(encPassword1).isNotEqualTo(encPassword2);
		System.out.println(encPassword1);
		System.out.println(encPassword2);

	}
}
