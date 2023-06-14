package info.myone.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EncryptTest {

	@Test
	void 비밀번호_암호_salt() {
		Encrypt ec = new Encrypt();
		String salt = ec.getSalt();

//		assertEquals(ec.getEncrypt("dikalee", salt), ec.getEncrypt("dikalee", salt), "동일한 salt 적용시 raw가 동일하면 같아야함");
//		assertNotEquals(ec.getEncrypt("dikalee", salt), ec.getEncrypt("dikalee7", salt), "동일한 salt 적용시 raw값이 다르면 같지 않아야함");

		// 동일한 salt 적용시 raw가 동일하면 암호문은 같아야함
		assertThat(ec.getEncrypt("dikalee", salt)).isEqualTo(ec.getEncrypt("dikalee", salt));

		// 동일한 salt 적용시 raw값이 다르면 암호문은 같지 않아야함
		assertThat(ec.getEncrypt("dikalee", salt)).isNotEqualTo(ec.getEncrypt("dikalee7", salt));
	}

}
