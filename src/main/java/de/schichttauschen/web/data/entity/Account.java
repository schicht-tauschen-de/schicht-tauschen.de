package de.schichttauschen.web.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Set<AccountDepartment> departments;
}