package info.myone.member.domain.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "tb_member")
@Entity
@Data
@ToString(exclude = {"userRoles"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    private String userid;

    @Column
    private String accname;

    @Column
    private String email;

    @Column
    private int age;

    @Column
    private String password;

    @Column
    private String role;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<AccountRole> userRoles = new HashSet<>();
}
