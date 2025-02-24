package dev.jahid.user_auth_db_config_boilerplate.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

import dev.jahid.user_auth_db_config_boilerplate.user.model.User;

@Getter
public class CustomUserDetails implements UserDetails {

    private final String name;
    private final String email;
    private final String role;
    private final String password;
    private final Boolean isActive;

    public CustomUserDetails( User user ) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole().getName();
        this.password = user.getPassword();
        this.isActive = user.getIsActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority( this.role ) );
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}

