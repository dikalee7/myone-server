package info.myone.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.myone.member.domain.entity.Account;
import info.myone.member.repository.UserRepository;
import info.myone.member.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void createUser(Account account){
        userRepository.save(account);
    }
}
