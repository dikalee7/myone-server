package info.myone.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class Encrypt {
	public String getSalt() {
		// 랜덤 바이트 객체 생성
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[20];

		// 난수 생성
		sr.nextBytes(salt);
		
		// byte to String (10진수의 문자열로 변경)
		StringBuffer sb = new StringBuffer();
		for (byte b : salt) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
	
	public String getEncrypt(String raw, String salt) {
		String rawData = (salt.isEmpty()?raw:raw+salt);
		try {
			// 알고리즘 객체 생성
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			
			//salt 적용한 암호화
			md.update(rawData.getBytes());
			return String.format("%0128x", new BigInteger(1, md.digest()));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
