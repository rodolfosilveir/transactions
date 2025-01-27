package br.com.pismo.transactions.domain.exception;

import br.com.pismo.transactions.domain.model.Role;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(final String text) {
        super(String.format("Role '%s' não permitida. Roles permitidas: %s", text, Role.getDescriptions()));
    }
    
}
