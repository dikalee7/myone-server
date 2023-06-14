package info.myone.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import info.myone.common.Encrypt;
import info.myone.member.controller.form.MemberForm;
import info.myone.member.domain.Member;
import info.myone.member.service.MemberService;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
//@Rollback(false)
class MemberServiceTest {
	
	@Autowired private MemberService memberService;
	@Autowired private ObjectProvider<Encrypt> encryptProvider;
	
	public MemberForm getBaseMember() {
		MemberForm member = new MemberForm();
		member.setMemberId("dikalee");
		member.setName("이진아");
		member.setBirthday("19760705");
		member.setPassword("10041004");
		return member;
	}
	
	public MemberForm getUpdateMember() {
		MemberForm member = new MemberForm();
		member.setMemberId("dikalee");
		member.setName("디카리");
		member.setBirthday("19760705");
		member.setPassword("10041004");
		return member;
	}

	@Test
	void 회원가입() {
		MemberForm member = getBaseMember();
		Member joinMember = memberService.join(member);
		Member findMember = memberService.findByMemberId(member.getMemberId()).get();
		
		assertThat(joinMember).isEqualTo(findMember);
	}
	
	@Test
	void 회원가입_비밀번호_암호() {
		Encrypt enc = encryptProvider.getObject();
		MemberForm member = getBaseMember();
		Member joinMember = memberService.join(member);
		Member findMember = memberService.findByMemberId(member.getMemberId()).get();
		
		assertThat(joinMember.getMemberId()).isEqualTo(findMember.getMemberId());
		assertThat(findMember.getPassword()).isEqualTo(enc.getEncrypt(member.getPassword(), findMember.getSalt()) );
	}

	@Test
	void 회원정보_수정() {
		MemberForm member = getBaseMember();
		Member joinMember = memberService.join(member);
		MemberForm uMember = getUpdateMember();
		Member updatedMember = memberService.updateMember(uMember);
		
		assertThat(joinMember.getMemberId()).isEqualTo(updatedMember.getMemberId());
	}
	
	@Test
	void 회원정보_수정_비밀번호_오류() {
		MemberForm member = getBaseMember();
		memberService.join(member);
		MemberForm uMember = getUpdateMember();
		uMember.setPassword("111111");
		
		assertThrows(IllegalStateException.class, ()->memberService.updateMember(uMember));
	}

}
