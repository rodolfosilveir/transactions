package br.com.pismo.transactions.config.security;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import lombok.Generated;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.pismo.transactions.domain.service.TokenService;
import br.com.pismo.transactions.port.out.UserPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Generated
public class SecurityFilter extends OncePerRequestFilter{

    private final TokenService tokenService;

    private final UserPort userPort;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        String token = this.recoverToken(request);
        if(Objects.nonNull(token)){
            String login = tokenService.validateToken(token);
            Optional<UserDetails> userOp = userPort.findByLogin(login);
            if(userOp.isPresent()){
                UserDetails user = userOp.get();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(Objects.isNull(authHeader)){
            return null;
        }

        return authHeader.replace("Bearer", "").trim();
    }
}