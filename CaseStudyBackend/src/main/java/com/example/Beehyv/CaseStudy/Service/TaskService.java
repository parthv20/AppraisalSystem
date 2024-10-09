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
    private ModelMapper modelMapper;
    @Autowired
    private AppraisalTaskRepository taskRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    // Method to fetch and return all tasks in ascending order by ID
    public List<AppraisalTaskDTO> getTasks() {
        // Fetch all tasks ordered by their ID
        List<AppraisalTask> tsk = taskRepository.findAllByOrderByIdAsc();
        List<AppraisalTaskDTO> appraisalTaskDTOS = new ArrayList<>();

        // Map each AppraisalTask entity to a DTO and add to the list
        for (AppraisalTask task : tsk) {
            AppraisalTaskDTO appraisalTaskDTO = this.modelMapper.map(task, AppraisalTaskDTO.class);
            appraisalTaskDTOS.add(appraisalTaskDTO);
        }

        // Return the list of mapped DTOs
        return appraisalTaskDTOS;
    }

    // Method to delete a task by its ID
    public void deleteTasks(long id) {
        // Check if the task with the given ID exists
        Optional<AppraisalTask> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            // If the task exists, delete it from the repository
            taskRepository.deleteById(id);
        }
    }

    // Method to save a task associated with an employee
    public AddTaskDTO saveTask(AppraisalTask task, long id) {
        // Find the employee by their ID
        Optional<Employee> addtaskemployee = employeeRepository.findById(id);

        // If the employee exists, associate the task with the employee and save it
        if (addtaskemployee.isPresent()) {
            Employee emp = addtaskemployee.get();
            task.setEmployee(emp);  // Set the employee for the task
            Set<AppraisalTask> tasks = emp.getTasks();  // Get the existing tasks of the employee
            tasks.add(task);  // Add the new task
            emp.setTasks(tasks);  // Update the employee's task set

            // Save the updated employee and return the mapped task DTO
            Employee savedEmployee = employeeRepository.save(emp);
            return modelMapper.map(task, AddTaskDTO.class);
        } else {
            // Throw an exception if the employee is not found
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
    }

    // Method to update an existing task with new details
    public AppraisalTaskDTO updateTasks(long id, AppraisalTaskDTO appraisalTaskDTO) throws Exception {
        // Find the task by its ID
        Optional<AppraisalTask> optionalTask = taskRepository.findById(id);

        // If the task exists, update the relevant fields with the provided data
        if (optionalTask.isPresent()) {
            AppraisalTask task = optionalTask.get();

            // Update the task's name if it is provided
            if (Objects.nonNull(appraisalTaskDTO.getName()) && !"".equalsIgnoreCase(task.getName())) {
                task.setName(appraisalTaskDTO.getName());
            }

            // Update the task's description if it is provided
            if (Objects.nonNull(appraisalTaskDTO.getDescription()) && !"".equalsIgnoreCase(appraisalTaskDTO.getDescription())) {
                task.setDescription(appraisalTaskDTO.getDescription());
            }

            // Update the task's start date if it is provided
            if (Objects.nonNull(appraisalTaskDTO.getStartDate())) {
                task.setStartDate(appraisalTaskDTO.getStartDate());
            }

            // Update the task's end date if it is provided
            if (Objects.nonNull(appraisalTaskDTO.getEndDate())) {
                task.setEndDate(appraisalTaskDTO.getEndDate());
            }

            // Update the appraisable flag
            task.setAppraisable(appraisalTaskDTO.isAppraisable());

            // Update the admin rating
            task.setAdminrating(appraisalTaskDTO.getAdminrating());

            // Save the updated task to the repository
            AppraisalTask updatedTask = taskRepository.save(task);

            // Return the updated task as a DTO
            return modelMapper.map(updatedTask, AppraisalTaskDTO.class);
        } else {
            // Throw an exception if the task is not found
            throw new Exception("Task not found with id: " + id);
        }
    }
}
