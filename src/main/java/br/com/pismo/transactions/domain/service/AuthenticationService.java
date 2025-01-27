package br.com.pismo.transactions.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pismo.transactions.adapter.in.rest.request.RegisterRequest;
import br.com.pismo.transactions.domain.exception.UserAlreadyExistsException;
import br.com.pismo.transactions.domain.model.Role;
import br.com.pismo.transactions.domain.model.User;
import br.com.pismo.transactions.port.in.UserUC;
import br.com.pismo.transactions.port.out.UserPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService, UserUC {

    private final UserPort userPort;

    @Override 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userPort.findByLogin(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " não encontrado"));
    }

    @Override
    public void registerUser(RegisterRequest request) {
        if(userPort.findByLogin(request.login()).isPresent()){
            throw new UserAlreadyExistsException(request.login());
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.password());
        userPort.save(User.builder()
            .login(request.login())
            .password(encryptedPassword)
            .role(Role.fromText(request.role()))
        .build());
    }

}
