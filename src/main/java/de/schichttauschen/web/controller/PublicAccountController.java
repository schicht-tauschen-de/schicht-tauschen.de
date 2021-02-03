package de.schichttauschen.web.controller;

import de.schichttauschen.web.data.entity.Account;
import de.schichttauschen.web.data.vo.UserPrincipal;
import de.schichttauschen.web.data.vo.rest.AccountInfo;
import de.schichttauschen.web.data.vo.rest.AccountRegistration;
import de.schichttauschen.web.data.vo.rest.LoginResponse;
import de.schichttauschen.web.service.AccountRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/public/account")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublicAccountController {
    private final AccountRegistrationService accountRegistrationService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public boolean register(@RequestBody AccountRegistration accountRegistration) {
        return accountRegistrationService.register(accountRegistration);
    }

    @GetMapping("/activate/{accountId}/{activationKey}")
    public boolean activate(@PathVariable UUID accountId, @PathVariable UUID activationKey) {
        return accountRegistrationService.activate(accountId, activationKey);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestParam String login, @RequestParam String password) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            Account account = ((UserPrincipal) auth.getPrincipal()).getAccount();
            return LoginResponse.builder()
                    .authenticated(true)
                    .account(AccountInfo.buildFrom(account))
                    .build();
        } catch (BadCredentialsException e) {
            return LoginResponse.builder()
                    .authenticated(false)
                    .build();
        }
    }
}