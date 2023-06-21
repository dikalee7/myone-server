package info.myone.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import info.myone.member.domain.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByRoleName(String name);

    @Override
    void delete(Role role);
}
