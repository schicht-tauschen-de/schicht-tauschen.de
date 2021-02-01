package de.schichttauschen.web.service;

import de.schichttauschen.web.data.entity.Account;
import de.schichttauschen.web.data.repository.AccountRepository;
import de.schichttauschen.web.data.vo.rest.AccountRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountRegistrationService {
    private final AccountRepository accountRepository;

    public boolean register(AccountRegistration accountRegistration) {
        if (accountRepository.existsByLoginOrEmail(accountRegistration.getLogin(), accountRegistration.getEmail()))
            return false;
        accountRepository.save(Account.builder()
                .login(accountRegistration.getLogin())
                .name(accountRegistration.getName())
                .email(accountRegistration.getEmail())
                .pendingActionKey(UUID.randomUUID())
                .active(false)
                .build());
        return true;
    }

    public boolean activate(UUID accountId, UUID activationKey) {
        var account = accountRepository.getById(accountId);
        if (account == null) return false;
        if (account.isActive()) return true;
        if (account.getPendingActionKey().equals(activationKey)) {
            account.setPendingActionKey(null);
            account.setActive(true);
            accountRepository.save(account);
            return true;
        } else return false;
    }
}
