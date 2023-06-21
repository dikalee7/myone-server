package info.myone.member.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
@Table(name = "tb_account_role")
@Entity
@Data
public class AccountRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_role_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
}
