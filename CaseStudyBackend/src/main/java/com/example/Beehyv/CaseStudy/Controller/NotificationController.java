package com.example.Beehyv.CaseStudy.Controller;


import com.example.Beehyv.CaseStudy.Service.EmployeeService;
import com.example.Beehyv.CaseStudy.Service.NotificationService;
import com.example.Beehyv.CaseStudy.model.Employee;
import com.example.Beehyv.CaseStudy.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("/notification")
public class NotificationController {

     @Autowired
     private NotificationService notificationService;

       @Autowired
      private EmployeeService employeeService ;


    @GetMapping("/getNotifications")
      public List<Notification> getALlNotification(){
         return notificationService.getAllNotification();
      }

    @PostMapping("/saveNotification")
      public Notification saveNotification(@RequestBody Notification notification){
        System.out.println(notification);
          return notificationService.saveNotification(notification);
      }

    @DeleteMapping("/deleteAllNotification")
    public void deleteAllNotifications(){
     notificationService.deleteAllNotifications();

    }


    @DeleteMapping("/{id}/deleteNotification")
    public void deleteNotification(@PathVariable("id") long id){
         notificationService.deleteNotification(id);
    }

    @PutMapping("/{id}/updateNotification")
    public boolean updateNotifyByAdmin(@PathVariable("id") long id, @RequestBody Employee employee){
             return employeeService.updateNotifyByAdmin(id, employee);
    }


}
