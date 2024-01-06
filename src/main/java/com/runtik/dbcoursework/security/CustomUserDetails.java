package com.runtik.dbcoursework.security;

import com.runtik.dbcoursework.enums.Role;
import com.runtik.dbcoursework.tables.pojos.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final Person person;


    public CustomUserDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = person.getPersonRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getLiteral()));
        authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getLiteral().toUpperCase()));
        return authorities;
    }



    @Override
    public String getPassword() {
        return person.getPersonPassword();
    }

    @Override
    public String getUsername() {
        return person.getPersonName();
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
        return true;
    }
}
