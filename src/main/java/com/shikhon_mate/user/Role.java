package com.shikhon_mate.user;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN( "ADMIN" ),
    SUPERADMIN( "SUPERADMIN" ),
    STUDENT( "STUDENT" );

    final String name;

    Role( String name ) {
        this.name = name;
    }
}
