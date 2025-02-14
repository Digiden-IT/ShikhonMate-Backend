package dev.jahid.user_auth_db_config_boilerplate.user.model;

import dev.jahid.user_auth_db_config_boilerplate.user.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table( name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( name = "name")
    private String name;

    @Column( name = "email" )
    private String email;

    @Column( name = "password" )
    private String password;

    @Enumerated( EnumType.STRING )
    @Column( name = "role" )
    private Role role;
}
