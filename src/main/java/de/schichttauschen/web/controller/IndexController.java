package de.schichttauschen.web.controller;

import de.schichttauschen.web.data.vo.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IndexController {
    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserPrincipal user) {
        return user == null
                ? "index"
                : "search";
    }
}