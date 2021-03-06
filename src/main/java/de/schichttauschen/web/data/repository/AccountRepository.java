package de.schichttauschen.web.data.repository;

import de.schichttauschen.web.data.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<Account, UUID> {
    Account getByLogin(String login);

    Account getById(UUID id);

    Boolean existsByLoginOrEmail(String login, String email);
}