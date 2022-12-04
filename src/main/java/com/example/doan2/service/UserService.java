package com.example.doan2.service;

import com.example.doan2.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService extends Serializable {
    boolean checkEmail(String email);

    boolean checkUserName(String username);

    User createUser(User user);

    User updateUser(User user);

    List<User> getUsers();

    User findUserByName(String username);

    Optional<User> findUserById(Integer id);

    User findUserById(int Id);

    UserDetails loadUserByUsername(String email);

    Collection<? extends GrantedAuthority> getAuthorities();

    String getPassword();

    String getUsername();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();

    boolean isEnabled();

}
