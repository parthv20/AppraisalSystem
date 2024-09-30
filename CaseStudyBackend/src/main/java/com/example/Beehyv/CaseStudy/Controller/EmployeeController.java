package com.example.Beehyv.CaseStudy.Controller;

import com.example.Beehyv.CaseStudy.DTO.AddTaskDTO;
import com.example.Beehyv.CaseStudy.DTO.AppraisalTaskDTO;
import com.example.Beehyv.CaseStudy.DTO.EmployeeDTO;
import com.example.Beehyv.CaseStudy.DTO.EmployeeDetailsDTO;
import com.example.Beehyv.CaseStudy.Service.EmployeeService;
import com.example.Beehyv.CaseStudy.Service.TaskService;
import com.example.Beehyv.CaseStudy.model.AppraisalTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
   EmployeeService  employeeService;

    @Autowired
    TaskService taskService ;

    @GetMapping("/{id}/getEmployee")
    public EmployeeDTO getEmployee(@PathVariable("id") long Id){
        return employeeService.getEmployee(Id);
    }

    @GetMapping("/{id}/getEmployeeDetails")
     public EmployeeDetailsDTO getEmployeeDetails(@PathVariable("id") long Id){
        return employeeService.getEmployeeDetails(Id);
     }


     // save task

    @PostMapping("/{id}/saveTask")
    public AddTaskDTO saveTask(@RequestBody AppraisalTask task ,@PathVariable("id") long id){

        return taskService.saveTask(task,id);
    }

    @PutMapping("/{id}/updateTask")
    public  AppraisalTaskDTO updateTask (@PathVariable("id") long id,@RequestBody AppraisalTaskDTO appraisalTaskDTO) throws Exception {
        return taskService.updateTasks(id,appraisalTaskDTO);
    }
     // update task


    @DeleteMapping("/{id}/deleteTask")
    public void deleteTasks(@PathVariable("id") long id ){
         taskService.deleteTasks(id);

    }

}
