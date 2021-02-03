package de.schichttauschen.web.service;

import de.schichttauschen.web.data.entity.Account;
import de.schichttauschen.web.data.repository.AccountRepository;
import de.schichttauschen.web.data.vo.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.getByLogin(username);
        if (account == null || !account.isActive()) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(account);
    }
}