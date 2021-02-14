package de.schichttauschen.web.data.vo.rest;

import de.schichttauschen.web.data.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestAccountInfo {
    private String login;
    private String email;
    private String name;

    public static RestAccountInfo fromAccount(Account account) {
        return RestAccountInfo.builder()
                .login(account.getLogin())
                .email(account.getEmail())
                .name(account.getName())
                .build();
    }
}
