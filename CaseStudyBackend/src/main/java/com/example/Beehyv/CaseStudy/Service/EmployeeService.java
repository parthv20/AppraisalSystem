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
    // Method to get a list of all employees (excluding admins), sorted by ID
    public List<EmployeeDetailsDTO> getAllEmployees() {
        // Fetch  employees, filter out "admin" designation, and sort by ID
        List<Employee> emp = employeeRepository.findAllByOrderByIdAsc().stream()
                .filter(employee -> !"admin".equalsIgnoreCase(employee.getDesignation()))
                .toList();
        // Map each employee to a DTO, and return as a list of EmployeeDetailsDTOs
        return emp.stream().map(this::mapEmployeeWithSortedTasks)
                .collect(Collectors.toList());
    }

    // Method to get a single employee by their ID
    public EmployeeDTO getEmployee(long id) {
        // Find employee by ID
        Optional<Employee> emp1 = employeeRepository.findById(id);
        if (emp1.isPresent()) {
            Employee employee = emp1.get();
            // Map entity to EmployeeDTO and return
            return modelMapper.map(employee, EmployeeDTO.class);
        } else {
            // If employee is not found, throw an exception
            throw new EntityNotFoundException("Employee not found with id " + id);
        }
    }

    // Method to get detailed information about an employee by ID
    public EmployeeDetailsDTO getEmployeeDetails(long id) {
        Optional<Employee> emp2 = employeeRepository.findById(id);
        if (emp2.isPresent()) {
            Employee employee = emp2.get();
            // Map the employee with sorted tasks and return
            return modelMapper.map(mapEmployeeWithSortedTasks(employee), EmployeeDetailsDTO.class);
        } else {
            // If not found, throw an exception
            throw new EntityNotFoundException("Employee not found with id " + id);
        }
    }

    // Method to update the notification status for an employee, indicating they've been notified by the admin
    public boolean updateNotifyByAdmin(long id, Employee employee) {
        Optional<Employee> emp = employeeRepository.findById(id);

        if (emp.isPresent()) {
            Employee emp1 = emp.get();
            // Set the notification flag for the employee and save
            emp1.setNoifybyadmin(true);
            employeeRepository.save(emp1);
            return true;
        } else {
            // If employee is not found, throw an exception
            throw new EntityNotFoundException("Employee not found with id " + id);
        }
    }

    // Method to save a new employee, with password encoding and email uniqueness check
    public EmployeeDTO saveEmployees(Employee employees) throws Exception {
        // Check if an employee with the given email already exists
        Optional<Employee> emp1 = employeeRepository.findByEmail(employees.getEmail());

        if (emp1.isEmpty()) {
            // If no employee exists with the email, encode the password and save
            String password = passwordEncoder.encode(employees.getPassword());
            employees.setPassword(password);
            Employee emp = employeeRepository.save(employees);
            // Map the saved employee to a DTO and return
            return modelMapper.map(emp, EmployeeDTO.class);
        } else {
            // If email already exists, throw an exception
            throw new Exception("Email already Exists...");
        }
    }

    // Method to save or update an employee's attribute
    public AttributeDTO saveAttribute(long id, AttributeDTO attributeDTO) {
        Optional<Employee> optionalEmployees = employeeRepository.findById(id);

        if (optionalEmployees.isPresent()) {
            Employee employee = optionalEmployees.get();
            // Check if the employee already has attributes
            if (employee.getAttributes() != null) {
                // If attributes exist, update them
                Attribute existingAttributes = employee.getAttributes();
                modelMapper.map(attributeDTO, existingAttributes);
                return attributeDTO;
            } else {
                // If no attributes exist, create new attributes and assign them to the employee
                Attribute newAttributes = modelMapper.map(attributeDTO, Attribute.class);
                newAttributes.setEmployee(employee);
                employee.setAttributes(newAttributes);
                Employee updatedEmployee = employeeRepository.save(employee);
                // Map the updated attributes to a DTO and return
                Attribute updatedAttributes = updatedEmployee.getAttributes();
                return modelMapper.map(updatedAttributes, AttributeDTO.class);
            }
        } else {
            // If employee is not found, throw an exception
            throw new EntityNotFoundException("Employee not found with Id: " + id);
        }
    }

    // Private helper method to map an Employee entity to EmployeeDetailsDTO with sorted tasks
    private EmployeeDetailsDTO mapEmployeeWithSortedTasks(Employee employees) {
        EmployeeDetailsDTO employeeDTO = this.modelMapper.map(employees, EmployeeDetailsDTO.class);

        // Sort the employee's tasks by their ID
        List<AppraisalTaskDTO> sortedTasks = employeeDTO.getTasks().stream()
                .sorted(Comparator.comparingLong(AppraisalTaskDTO::getId))
                .toList();

        // Set the sorted tasks to the EmployeeDetailsDTO
        employeeDTO.setTasks(new ArrayList<>(sortedTasks));

        return employeeDTO;
    }
}
