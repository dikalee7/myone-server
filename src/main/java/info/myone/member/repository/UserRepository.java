package info.myone.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import info.myone.member.domain.entity.Account;

public interface UserRepository extends JpaRepository<Account, Long> {
	Account findByUserid(String userid);
	int countByUserid(String userid);
}
