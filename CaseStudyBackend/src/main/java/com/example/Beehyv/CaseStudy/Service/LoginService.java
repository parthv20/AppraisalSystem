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

    // Method to validate login credentials and return login response
    public LoginResponseDTO checkValidation(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();  // Extract email from the login DTO
        String password = loginDTO.getPassword();  // Extract password from the login DTO
        System.out.println(email + "  " + password);  // Logging email and password for debugging

        // Create an authentication token with the provided email and password
        var authToken = new UsernamePasswordAuthenticationToken(email, password);

        // Authenticate the token using the authentication manager
        var authenticate = authenticationManager.authenticate(authToken);

        // Fetch employee details based on the authenticated email
        Optional<Employee> employees = employeeRepository.findByEmail(authenticate.getName());

        // If employee exists, map the employee to a LoginResponseDTO and set success response
        if (employees.isPresent()) {
            Employee emp = employees.get();
            LoginResponseDTO loginResponseDTO = modelMapper.map(emp, LoginResponseDTO.class);  // Convert Employee to LoginResponseDTO
            loginResponseDTO.setMessage("success");  // Set success message
            loginResponseDTO.setStatus((long) HttpStatus.FOUND.value());  // Set HTTP status code
            loginResponseDTO.setToken(JwtUtils.generateToken(authenticate.getName()));  // Generate JWT token for authenticated user
            return loginResponseDTO;  // Return the response DTO
        }
        else {
            // If employee is not found, throw an exception
            throw new EntityNotFoundException("User Not Found");
        }
    }
}