package info.myone.member.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.myone.common.Encrypt;
import info.myone.member.controller.form.MemberForm;
import info.myone.member.domain.Member;
import info.myone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberRepository memberRepository;
	private final ObjectProvider<Encrypt> encryptProvider;

	@Transactional
	public Member join(MemberForm form) {
		Encrypt enc = encryptProvider.getObject();
		String salt = enc.getSalt();
		String encPassword = enc.getEncrypt(form.getPassword(), salt);
		Member member = Member.createMember(form.getMemberId(), form.getName(), encPassword, salt, form.getBirthday());
		memberRepository.save(member);
		return member;
	}
	
	//영속성 컨텍스트가 자동 변경
	@Transactional
	public Member updateMember(MemberForm form) {
		
		Member member = memberRepository.findOne(form.getMemberId()).get();
		
		if(vaildPassword(member.getSalt(), member.getPassword(), form.getPassword())) {
			//정보 변경
			member.setName(form.getName());
			member.setBirthday(form.getBirthday());
			member.setUpdDate(LocalDateTime.now());
		} else {
			throw new IllegalStateException("입력된 회원 비밀번호가 올바르지 않습니다.");
		}
		return member;
	}
	
	//수정 시 입력한 비밀번호 확인
	private boolean vaildPassword(String salt, String memberPassword, String formPassword) {
		Encrypt enc = encryptProvider.getObject();		
		return memberPassword.equals(enc.getEncrypt(formPassword, salt));
	}

	public Optional<Member> findByMemberId(String memberId) {
		return memberRepository.findOne(memberId);
	}
	
	public List<Member> findMember() {
		return memberRepository.findAll();
	}
}
