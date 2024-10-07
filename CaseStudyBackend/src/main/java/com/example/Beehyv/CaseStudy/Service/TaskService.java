package com.example.Beehyv.CaseStudy.Service;




import com.example.Beehyv.CaseStudy.DTO.AddTaskDTO;
import com.example.Beehyv.CaseStudy.DTO.AppraisalTaskDTO;
import com.example.Beehyv.CaseStudy.Repositry.EmployeeRepository;
import com.example.Beehyv.CaseStudy.Repositry.AppraisalTaskRepository;
import com.example.Beehyv.CaseStudy.model.AppraisalTask;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import com.example.Beehyv.CaseStudy.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class TaskService {





    @Autowired
    private  ModelMapper modelMapper;
    @Autowired
    private  AppraisalTaskRepository taskRepository;
    @Autowired
    private  EmployeeRepository employeeRepository;


    public List<AppraisalTaskDTO> getTasks() {


        List<AppraisalTask> tsk = taskRepository.findAllByOrderByIdAsc();
        List<AppraisalTaskDTO> appraisalTaskDTOS = new ArrayList<>();

        for ( AppraisalTask task : tsk) {
            AppraisalTaskDTO appraisalTaskDTO = this.modelMapper.map(task, AppraisalTaskDTO.class);
            appraisalTaskDTOS.add(appraisalTaskDTO);
        }

        return appraisalTaskDTOS;
    }

    // cchng as used  in his project
    public void deleteTasks(long id) {

        Optional<AppraisalTask>  optionalTask= taskRepository.findById(id);
        if(optionalTask.isPresent())
        {
            taskRepository.deleteById(id);
//       Optional<AppraisalTask> apptask=taskRepository
        }

    }

    public AddTaskDTO saveTask(AppraisalTask task, long id) {
        Optional<Employee> addtaskemployee= employeeRepository.findById(id);

        if(addtaskemployee.isPresent()){
            Employee emp= addtaskemployee.get();

            task.setEmployee(emp);
            Set<AppraisalTask> tasks  = emp.getTasks();
            tasks.add(task);
            emp.setTasks(tasks);
            Employee savedEmployee = employeeRepository.save(emp);

            return modelMapper.map(task,AddTaskDTO.class);
        } else {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
    }

    public AppraisalTaskDTO updateTasks(long id, AppraisalTaskDTO appraisalTaskDTO) throws Exception {
        Optional<AppraisalTask> optionalTask = taskRepository.findById(id);
        System.out.println(id);
        if(optionalTask.isPresent()){
            AppraisalTask task = optionalTask.get();
            if(Objects.nonNull(appraisalTaskDTO.getName()) && !"".equalsIgnoreCase(task.getName())){
                task.setName(appraisalTaskDTO.getName());
            }

            if(Objects.nonNull(appraisalTaskDTO.getDescription()) && !"".equalsIgnoreCase(appraisalTaskDTO.getDescription())){
                task.setDescription(appraisalTaskDTO.getDescription());
            }

            if(Objects.nonNull(appraisalTaskDTO.getStartDate())){
                task.setStartDate(appraisalTaskDTO.getStartDate());
            }

            if(Objects.nonNull(appraisalTaskDTO.getEndDate())){
                task.setEndDate(appraisalTaskDTO.getEndDate());
            }

            task.setAppraisable(appraisalTaskDTO.isAppraisable());

            System.out.println(appraisalTaskDTO.getName());
            task.setAdminrating(appraisalTaskDTO.getAdminrating());
            AppraisalTask updatedTask = taskRepository.save(task);

            return modelMapper.map(updatedTask, AppraisalTaskDTO.class);

        }
        else {
            throw new Exception("Task not found with id: " + id);
        }
    }
}

