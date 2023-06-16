package info.myone.member.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
//@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_member")
public class Account {

	@Id
//    @GeneratedValue
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
}
