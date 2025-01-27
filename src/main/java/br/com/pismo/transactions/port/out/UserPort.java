package br.com.pismo.transactions.port.out;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import br.com.pismo.transactions.domain.model.User;

public interface UserPort {

    Optional<UserDetails> findByLogin(String login);

    void save(User user);
    
}