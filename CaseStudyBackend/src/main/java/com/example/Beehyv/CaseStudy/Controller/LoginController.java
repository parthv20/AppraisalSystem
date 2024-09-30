package com.example.Beehyv.CaseStudy.Controller;


import com.example.Beehyv.CaseStudy.DTO.EmployeeDTO;
import com.example.Beehyv.CaseStudy.DTO.LoginDTO;
import com.example.Beehyv.CaseStudy.DTO.LoginResponseDTO;
import com.example.Beehyv.CaseStudy.Service.EmployeeService;
import com.example.Beehyv.CaseStudy.Service.LoginService;
import com.example.Beehyv.CaseStudy.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(originPatterns = "*")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
   public LoginResponseDTO getLoginInfo(@RequestBody LoginDTO loginDTO){
        return loginService.checkValidation(loginDTO);
   }
    @PostMapping("/signup")
    public EmployeeDTO saveEmployee(@RequestBody Employee employee) throws Exception {
      return employeeService.saveEmployees(employee);

    }


}
