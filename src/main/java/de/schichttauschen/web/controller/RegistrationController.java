package de.schichttauschen.web.controller;

import de.schichttauschen.web.data.vo.rest.AccountRegistration;
import de.schichttauschen.web.service.AccountRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/public/account")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {
    private final AccountRegistrationService accountRegistrationService;

    @PostMapping("/register")
    public boolean register(@RequestBody AccountRegistration accountRegistration) {
        return accountRegistrationService.register(accountRegistration);
    }

    @GetMapping("/activate/{accountId}/{activationKey}")
    public boolean activate(@PathVariable UUID accountId, @PathVariable UUID activationKey) {
        return accountRegistrationService.activate(accountId, activationKey);
    }
}