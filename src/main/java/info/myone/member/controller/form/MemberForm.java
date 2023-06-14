package info.myone.member.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
	// 회원 아이디
	private String memberId;

	// 이름
	private String name;

	// 비밀번호
	private String password;

	// 생년월일
	private String birthday;
}
