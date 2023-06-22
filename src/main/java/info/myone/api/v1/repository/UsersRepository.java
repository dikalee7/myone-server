package info.myone.api.v1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import info.myone.api.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);
}
