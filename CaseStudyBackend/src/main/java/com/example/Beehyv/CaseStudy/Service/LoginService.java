package com.example.Beehyv.CaseStudy.Service;

import com.example.Beehyv.CaseStudy.DTO.LoginDTO;
import com.example.Beehyv.CaseStudy.DTO.LoginResponseDTO;
import com.example.Beehyv.CaseStudy.Repositry.EmployeeRepository;
import com.example.Beehyv.CaseStudy.Utils.JwtUtils;
import com.example.Beehyv.CaseStudy.model.Employee;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDTO checkValidation(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();
        System.out.println(email + "  " + password);
        var authToken = new UsernamePasswordAuthenticationToken(email, password);
        var authenticate = authenticationManager.authenticate(authToken);

        Optional<Employee> employees = employeeRepository.findByEmail(authenticate.getName());
        if(employees.isPresent()){
            Employee emp = employees.get();
            LoginResponseDTO loginResponseDTO = modelMapper.map(emp, LoginResponseDTO.class);
            loginResponseDTO.setMessage("success");;
            loginResponseDTO.setStatus((long) HttpStatus.FOUND.value());
            loginResponseDTO.setToken(JwtUtils.generateToken(authenticate.getName()));
            return loginResponseDTO;
        }
        else{
            throw new EntityNotFoundException("User Not Found");
        }
    }
}
