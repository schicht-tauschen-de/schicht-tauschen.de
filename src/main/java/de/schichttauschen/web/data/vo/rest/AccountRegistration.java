package de.schichttauschen.web.data.vo.rest;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegistration {
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private String name;
    @NonNull
    private String email;
}
