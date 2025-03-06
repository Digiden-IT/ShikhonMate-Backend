package com.shikhon_mate.user;

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
