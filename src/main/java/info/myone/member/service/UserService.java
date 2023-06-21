package info.myone.member.service;

import java.util.List;

import info.myone.member.domain.dto.AccountDto;
import info.myone.member.domain.entity.Account;

public interface UserService {
	void createUser(Account account);

    void modifyUser(AccountDto accountDto);

    List<Account> getUsers();

    AccountDto getUser(Long id);

    void deleteUser(Long idx);
}
