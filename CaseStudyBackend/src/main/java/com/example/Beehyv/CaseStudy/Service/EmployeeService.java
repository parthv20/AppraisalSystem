package com.example.Beehyv.CaseStudy.Service;



import com.example.Beehyv.CaseStudy.DTO.AppraisalTaskDTO;
import com.example.Beehyv.CaseStudy.DTO.AttributeDTO;
import com.example.Beehyv.CaseStudy.DTO.EmployeeDTO;
import com.example.Beehyv.CaseStudy.DTO.EmployeeDetailsDTO;
import com.example.Beehyv.CaseStudy.Repositry.EmployeeRepository;
import com.example.Beehyv.CaseStudy.model.Attribute;
import com.example.Beehyv.CaseStudy.model.Employee;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private  EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }



    public List<EmployeeDetailsDTO> getAllEmployees() {
        List<Employee> emp = employeeRepository.findAllByOrderByIdAsc().stream()
                .filter(employee ->   !"admin".equalsIgnoreCase(employee.getDesignation()))
                .toList();
        return emp.stream().map(this::mapEmployeeWithSortedTasks)
                .collect(Collectors.toList());
    }




























    private EmployeeDetailsDTO mapEmployeeWithSortedTasks(Employee employees) {
        EmployeeDetailsDTO employeeDTO = this.modelMapper.map(employees, EmployeeDetailsDTO.class);

        List<AppraisalTaskDTO> sortedTasks = employeeDTO.getTasks().stream()
                .sorted(Comparator.comparingLong(AppraisalTaskDTO::getId))
                .toList();

        employeeDTO.setTasks(new ArrayList<>(sortedTasks));

        return employeeDTO;
    }

    public List<EmployeeDetailsDTO> searchEmployees(String s) {
        List<Employee> emp = employeeRepository.searchEmployee(s)
                .stream()
                .filter(employee -> !"admin".equalsIgnoreCase(employee.getDesignation()))
                .toList();
        return emp.stream().map(this::mapEmployeeWithSortedTasks)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployee(long id) {
        Optional<Employee> emp1 = employeeRepository.findById(id);
        if (emp1.isPresent()) {
            Employee employee = emp1.get();
            return modelMapper.map(employee, EmployeeDTO.class);
        }else{
            throw new EntityNotFoundException("Employee not found with id "+ id);
        }
    }

    public EmployeeDetailsDTO getEmployeeDetails(long id) {
        Optional<Employee> emp2 =employeeRepository.findById(id);
        if(emp2.isPresent()){
            Employee employee=emp2.get();

            return modelMapper.map(mapEmployeeWithSortedTasks(employee),EmployeeDetailsDTO.class);
        }else{
            throw new EntityNotFoundException("Employee not found with id "+ id);
        }


    }

    public boolean updateNotifyByAdmin(long id, Employee employee) {
        Optional<Employee> emp=employeeRepository.findById(id);

        if(emp.isPresent()){
            Employee emp1 =emp.get();
            emp1.setNoifybyadmin(true);
            employeeRepository.save(emp1);
            return true;
        }else{
            throw new EntityNotFoundException("Employee not found with id"+ id);
        }
    }

    public EmployeeDTO saveEmployees(Employee employees) throws Exception {

        Optional<Employee> emp1= employeeRepository.findByEmail(employees.getEmail());

        if(emp1.isEmpty()){
            String password = passwordEncoder.encode(employees.getPassword());
            employees.setPassword(password);
            Employee emp =  employeeRepository.save(employees);
            return modelMapper.map(emp, EmployeeDTO.class);
        }else{
            throw new Exception("Email already Exists...");
        }
    }

    public AttributeDTO saveAttribute(long id, AttributeDTO attributeDTO) {
        Optional<Employee> optionalEmployees = employeeRepository.findById(id);

        if(optionalEmployees.isPresent()){
            Employee employee = optionalEmployees.get();
            if (employee.getAttributes() != null) {
                Attribute existingAttributes = employee.getAttributes();
                modelMapper.map(attributeDTO, existingAttributes);
                employeeRepository.save(employee);
                return attributeDTO;
            } else {

                Attribute newAttributes = modelMapper.map(attributeDTO, Attribute.class);
                newAttributes.setEmployee(employee);
                employee.setAttributes(newAttributes);
                Employee updatedEmployee = employeeRepository.save(employee);
                Attribute updatedAttributes = updatedEmployee.getAttributes();
                return modelMapper.map(updatedAttributes, AttributeDTO.class);
            }
        }else{
            throw new EntityNotFoundException("Employee Not found with Id : " + id);
        }
    }
}
