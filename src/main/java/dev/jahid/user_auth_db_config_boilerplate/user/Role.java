package dev.jahid.user_auth_db_config_boilerplate.user;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN( "admin" ),
    USER( "user" );

    final String name;

    Role( String name ) {
        this.name = name;
    }
}
