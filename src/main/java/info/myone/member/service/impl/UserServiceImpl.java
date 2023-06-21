package info.myone.member.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.myone.member.domain.dto.AccountDto;
import info.myone.member.domain.entity.Account;
import info.myone.member.domain.entity.AccountRole;
import info.myone.member.domain.entity.Role;
import info.myone.member.repository.RoleRepository;
import info.myone.member.repository.UserRepository;
import info.myone.member.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public void createUser(Account account) {

		Role role = roleRepository.findByRoleName("ROLE_USER");
		Set<AccountRole> roles = new HashSet<>();
		roles.add(AccountRole.builder().account(account).role(role).build());
		account.setUserRoles(roles);
		userRepository.save(account);
	}

	@Transactional
	@Override
	public void modifyUser(AccountDto accountDto) {

		ModelMapper modelMapper = new ModelMapper();
		Account account = modelMapper.map(accountDto, Account.class);

		if (accountDto.getRoles() != null) {
			Set<AccountRole> roles = new HashSet<>();
			accountDto.getRoles().forEach(role -> {
				Role r = roleRepository.findByRoleName(role);
				roles.add(AccountRole.builder().account(account).role(r).build());
			});
			account.setUserRoles(roles);
		}
		account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		userRepository.save(account);

	}

	@Transactional
	public AccountDto getUser(Long id) {

		Account account = userRepository.findById(id).orElse(new Account());
		ModelMapper modelMapper = new ModelMapper();
		AccountDto accountDto = modelMapper.map(account, AccountDto.class);

		List<String> roles = account.getUserRoles().stream().map(role -> role.getRole().getRoleName())
				.collect(Collectors.toList());

		accountDto.setRoles(roles);
		return accountDto;
	}

	@Transactional
	public List<Account> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
