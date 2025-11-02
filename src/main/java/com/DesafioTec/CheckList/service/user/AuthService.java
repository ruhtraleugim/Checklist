package com.DesafioTec.CheckList.service.user;

import com.DesafioTec.CheckList.model.user.UserModel;
import com.DesafioTec.CheckList.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = repo.findByEmail(username).orElseThrow();

        String role;
        if (user.getRoles() == 2) {
            role = "ROLE_ADMIN";
        } else if (user.getRoles() == 1){
            role = "ROLE_USER";
        }else throw new RuntimeException();

        return new org.springframework.security.core.userdetails.User(
                user.getNameUser(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}
