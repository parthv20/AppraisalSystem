package com.example.Beehyv.CaseStudy.Service;


import com.example.Beehyv.CaseStudy.Repositry.EmployeeRepository;
import com.example.Beehyv.CaseStudy.Repositry.NotificationRepository;
import com.example.Beehyv.CaseStudy.model.Employee;
import com.example.Beehyv.CaseStudy.model.Notification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;


    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Notification> getAllNotification() {
        return notificationRepository.findAll();
    }

    public Notification saveNotification(Notification notification) {
        Optional<Employee> emp = employeeRepository.findById(notification.getEmployeeId());

        if (emp.isPresent()) {

            Employee employee =emp.get();
            employee.setNotifybyemployee(true);
            employeeRepository.save(employee);
        }
        else{
            throw new EntityNotFoundException("Employee not found with id"+notification.getEmployeeId());
        }
//        return notificationRepository.save(notification);
         return notificationRepository.save(notification);
    }


    public void deleteAllNotifications() {
        notificationRepository.deleteAll();
    }
    @Transactional
    public void deleteNotification(long id) {
        notificationRepository.deleteByEmployeeId(id);
    }
}
