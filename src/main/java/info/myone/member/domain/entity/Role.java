package info.myone.member.domain.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Table(name = "tb_role")
@Entity
@Data
@ToString(exclude = { "accounts", "resourcesSet" })
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "role_id")
	private Long id;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "role_desc")
	private String roleDesc;

	@OneToMany(mappedBy = "role")
	@OrderBy("ordernum desc")
	private Set<RoleResources> resourcesSet = new LinkedHashSet<>();

	@OneToMany(mappedBy = "role")
	private Set<AccountRole> accounts = new HashSet<>();

}
