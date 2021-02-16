package de.schichttauschen.web.data.vo.rest;

import de.schichttauschen.web.data.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestAccountInfo {
    private String login;
    private String email;
    private String name;
    private Set<RestAccountDepartment> companies;

    public static RestAccountInfo fromAccount(Account account) {
        return RestAccountInfo.builder()
                .login(account.getLogin())
                .email(account.getEmail())
                .name(account.getName())
                .companies(account.getDepartments()
                        .parallelStream()
                        .map(RestAccountDepartment::fromAccountDepartment)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static RestAccountInfo fromAccountPublic(Account account) {
        return RestAccountInfo.builder()
                .name(account.getName())
                .build();
    }
}
