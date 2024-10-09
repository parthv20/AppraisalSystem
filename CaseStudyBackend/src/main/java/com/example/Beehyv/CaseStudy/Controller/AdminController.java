package com.example.Beehyv.CaseStudy.Controller;


import com.example.Beehyv.CaseStudy.DTO.AppraisalTaskDTO;
import com.example.Beehyv.CaseStudy.DTO.AttributeDTO;
import com.example.Beehyv.CaseStudy.DTO.EmployeeDetailsDTO;
import com.example.Beehyv.CaseStudy.Service.EmployeeService;
import com.example.Beehyv.CaseStudy.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private EmployeeService employeeService;



    @GetMapping("/getTask")
    public List<AppraisalTaskDTO> taskdto(){
        return taskService.getTasks();
    }

    @GetMapping("/getEmployee")
    public List<EmployeeDetailsDTO>  getEmployee(){
        return employeeService.getAllEmployees();
    }

    @PostMapping("/{id}/saveAttribute")
    public ResponseEntity<AttributeDTO> saveAttribute(@PathVariable("id") long id, @RequestBody AttributeDTO attributeDTO){
        AttributeDTO attributeDTO1 = employeeService.saveAttribute(id, attributeDTO);
        return new ResponseEntity<>(attributeDTO1, HttpStatus.CREATED);
    }





}
