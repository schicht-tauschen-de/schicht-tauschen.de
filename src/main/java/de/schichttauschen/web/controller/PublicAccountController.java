package de.schichttauschen.web.controller;

import de.schichttauschen.web.aspect.RateLimited;
import de.schichttauschen.web.data.entity.Account;
import de.schichttauschen.web.data.vo.UserPrincipal;
import de.schichttauschen.web.data.vo.rest.AccountInfo;
import de.schichttauschen.web.data.vo.rest.AccountRegistration;
import de.schichttauschen.web.data.vo.rest.BooleanResponse;
import de.schichttauschen.web.data.vo.rest.LoginResponse;
import de.schichttauschen.web.service.AccountRegistrationService;
import de.schichttauschen.web.service.BooleanResponseBuilderFactory;
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
    private final BooleanResponseBuilderFactory booleanResponseBuilderFactory;

    @RateLimited(requests = 5, interval = RateLimited.Interval.Minutes)
    @PostMapping("/register")
    public BooleanResponse register(@RequestBody AccountRegistration accountRegistration) {
        boolean status = accountRegistrationService.register(accountRegistration);
        return booleanResponseBuilderFactory.get()
                .status(status)
                .trueMessage("action.register.success")
                .falseMessage("action.register.failed")
                .build();
    }

    @RateLimited(requests = 3, interval = RateLimited.Interval.Minutes)
    @GetMapping("/activate/{accountId}/{activationKey}")
    public BooleanResponse activate(@PathVariable UUID accountId, @PathVariable UUID activationKey) {
        boolean status = accountRegistrationService.activate(accountId, activationKey);
        return booleanResponseBuilderFactory.get()
                .status(status)
                .trueMessage("action.activate.success")
                .falseMessage("action.activate.failed")
                .build();
    }

    @RateLimited(requests = 5, interval = RateLimited.Interval.Minutes)
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