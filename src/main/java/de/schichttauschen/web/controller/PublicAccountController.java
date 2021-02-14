package de.schichttauschen.web.controller;

import de.schichttauschen.web.aspect.RateLimited;
import de.schichttauschen.web.data.entity.Account;
import de.schichttauschen.web.data.vo.UserPrincipal;
import de.schichttauschen.web.data.vo.rest.RestAccountInfo;
import de.schichttauschen.web.data.vo.rest.RestAccountRegistration;
import de.schichttauschen.web.data.vo.rest.RestBooleanResponse;
import de.schichttauschen.web.data.vo.rest.RestLoginResponse;
import de.schichttauschen.web.service.AccountRegistrationService;
import de.schichttauschen.web.service.BooleanResponseBuilderFactory;
import de.schichttauschen.web.service.TranslationService;
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
    private final TranslationService translationService;

    @RateLimited(requests = 5, interval = RateLimited.Interval.Minutes)
    @PostMapping("/register")
    public RestBooleanResponse register(@RequestBody RestAccountRegistration restAccountRegistration) {
        boolean status = accountRegistrationService.register(restAccountRegistration);
        return booleanResponseBuilderFactory.get()
                .status(status)
                .trueMessage("action.register.success")
                .falseMessage("action.register.failed")
                .build();
    }

    @RateLimited(requests = 3, interval = RateLimited.Interval.Minutes)
    @GetMapping("/activate/{accountId}/{activationKey}")
    public RestBooleanResponse activate(@PathVariable UUID accountId, @PathVariable UUID activationKey) {
        boolean status = accountRegistrationService.activate(accountId, activationKey);
        return booleanResponseBuilderFactory.get()
                .status(status)
                .trueMessage("action.activate.success")
                .falseMessage("action.activate.failed")
                .build();
    }

    @RateLimited(requests = 5, interval = RateLimited.Interval.Minutes)
    @PostMapping("/login")
    public RestLoginResponse login(@RequestParam String login, @RequestParam String password) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            Account account = ((UserPrincipal) auth.getPrincipal()).getAccount();
            return RestLoginResponse.builder()
                    .authenticated(true)
                    .message(translationService.get("action.login.success", account.getName()))
                    .account(RestAccountInfo.fromAccount(account))
                    .build();
        } catch (BadCredentialsException e) {
            return RestLoginResponse.builder()
                    .authenticated(false)
                    .message(translationService.get("action.login.failed"))
                    .build();
        }
    }
}