package com.example.Beehyv.CaseStudy.Service;


import com.example.Beehyv.CaseStudy.Repositry.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var appUser = employeeRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Email not found"));


        // - A list of granted authorities, where the user is assigned either the "ADMIN" or "Employee" role
        return new User(appUser.getEmail(), appUser.getPassword(),
                List.of(new SimpleGrantedAuthority(appUser.isAdmin() ? "ADMIN" : "Employee")));
    }
}
