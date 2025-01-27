package br.com.pismo.transactions.adapter.out.persistence.entity;

import java.util.UUID;

import br.com.pismo.transactions.domain.model.Role;
import br.com.pismo.transactions.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "document_number")
    private String documentNumber;

    public User toDomain(){
        return User.builder()
            .id(this.id)
            .login(this.login)
            .password(this.password)
            .role(Role.fromText(this.role))
            .documentNumber(documentNumber)
        .build();
    }

    public static UserEntity fromDomain(User domain){
        return UserEntity.builder()
            .id(domain.getId())
            .login(domain.getLogin())
            .password(domain.getPassword())
            .role(domain.getRole().getText())
            .documentNumber(domain.getDocumentNumber())
        .build();
    }
    
}