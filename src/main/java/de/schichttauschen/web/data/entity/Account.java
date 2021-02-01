package de.schichttauschen.web.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String login;

    @Column
    private String password;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Set<AccountDepartment> departments;
}