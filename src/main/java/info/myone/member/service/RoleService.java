package info.myone.member.service;

import java.util.List;

import info.myone.member.domain.entity.Role;

public interface RoleService {
    Role getRole(long id);

    List<Role> getRoles();

    void createRole(Role role);

    void deleteRole(long id);
}
