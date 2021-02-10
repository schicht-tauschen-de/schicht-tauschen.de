package de.schichttauschen.web.controller;

import de.schichttauschen.web.data.repository.AccountRepository;
import de.schichttauschen.web.data.vo.UserPrincipal;
import de.schichttauschen.web.data.vo.rest.RestAccountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {
    private final AccountRepository accountRepository;

    @GetMapping("logout")
    public void logout(HttpSession session) {
        SecurityContextHolder.clearContext();
        session.invalidate();
    }

    @GetMapping("details")
    public RestAccountInfo details() {
        return RestAccountInfo.buildFrom(
                ((UserPrincipal) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()).getAccount());
    }
}