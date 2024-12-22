package org.example.demo.bookingservice.security;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.model.User;
import org.example.demo.bookingservice.model.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final User user;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getEmail()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Objects.equals(user.getUserStatus(), UserStatus.NEW) || Objects.equals(user.getUserStatus(), UserStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(user.getUserStatus(), UserStatus.NEW) || Objects.equals(user.getUserStatus(), UserStatus.ACTIVE);
    }
}
