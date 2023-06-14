package info.myone.member.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
@Table(name = "tb_member")
public class Member {
	@Id
//	@GeneratedValue
	private String memberId;

	//이름
	private String name;
	
	// 비밀번호 암호화 
	private String password;
	
	// 비밀번호 암호화 
	private String salt;
	
	//생년월일
	private String birthday;
	
	// 등록일(가입일)
	private LocalDateTime regDate;
	
	// 수정일(마지막 정보 변경일)
	private LocalDateTime updDate;
	
	public static Member createMember(String memberId, String name, String password, String salt, String birthday) {
		Member member = new Member();
		member.setMemberId(memberId);
		member.setName(name);
		member.setPassword(password);
		member.setSalt(salt);
		member.setBirthday(birthday);
		member.setRegDate(LocalDateTime.now());
		return member;
	}
}
