package de.schichttauschen.web.service;

import de.schichttauschen.web.data.repository.AccountRepository;
import de.schichttauschen.web.data.vo.rest.AccountRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

@SpringBootTest
public class AccountRegistrationServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AccountRegistrationService accountRegistrationService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testCreateAndActivateAccount() {
        String randomString = UUID.randomUUID().toString();
        Assert.assertFalse(accountRepository.existsByLoginOrEmail(randomString, randomString));

        // register account
        accountRegistrationService.register(AccountRegistration.builder()
                .email(randomString)
                .login(randomString)
                .name(randomString)
                .build());

        // verify that the account is initially not active
        var account1 = accountRepository.getByLogin(randomString);
        Assert.assertEquals(account1.getEmail(), randomString);
        Assert.assertFalse(account1.isActive());
        Assert.assertNotNull(account1.getPendingActionKey());

        // verify that we can't activate the account with a wrong action key
        Assert.assertFalse(accountRegistrationService.activate(account1.getId(), UUID.randomUUID()));
        var account2 = accountRepository.getByLogin(randomString);
        Assert.assertFalse(account2.isActive());
        Assert.assertNotNull(account2.getPendingActionKey());

        // verify that the account is active after we activated it with the correct action key
        Assert.assertTrue(accountRegistrationService.activate(account1.getId(), account1.getPendingActionKey()));
        var account3 = accountRepository.getByLogin(randomString);
        Assert.assertTrue(account3.isActive());
        Assert.assertNull(account3.getPendingActionKey());
    }
}
