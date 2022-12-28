package com.example.doan2.service.Impl;


import com.example.doan2.chain.UserContractConnector;
import com.example.doan2.entity.User;
import com.example.doan2.repository.UserRepository;
import com.example.doan2.service.AdminContractExecutionService;
import com.example.doan2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserDetailsService, UserService, UserDetails {

    private User user;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminContractExecutionService adminContractExecutionService;

    public UserServiceImp(User user) {
        this.user = user;
    }

    public UserServiceImp() {
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User findUserByName(String username) {
        return userRepository.findByName(username);
    }


    public User findUserById(int Id) {
        return
                userRepository.findById(Id);
    }


    // function for load User by Email

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserServiceImp(user);
    }

    public boolean checkEmail(String email) {
        User result = userRepository.findByEmail(email);
        if (result != null) {
            if (email.equals(result.getEmail())) {
                System.out.println("Email have already Existed");
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public boolean checkUserName(String username) {
        User result = userRepository.findByName(username);
        if (result != null) {
            if (username.equals(result.getName())) {
                System.out.println("Name have already Existed");
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    /*
     *  Methods for Login User below
     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    public String getName() {
        return user.getName();
    }

    public User getUser() {
        return this.user;
    }
    /*
     *  End Methods for Login User
     * */


    public String getBalance() throws Exception {
        UserContractConnector u = new UserContractConnector(user);
//        BigInteger balance = adminContractExecutionService.GetBalance(user);
        BigInteger balance = u.GetBalance();
        String userBalance = String.valueOf(balance);
        return userBalance;
    }
}
