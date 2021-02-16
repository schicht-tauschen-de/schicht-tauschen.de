package de.schichttauschen.web.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "departments")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private UUID pendingActionKey;

    @Column
    private boolean active;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false)
    private Set<AccountDepartment> departments;
}