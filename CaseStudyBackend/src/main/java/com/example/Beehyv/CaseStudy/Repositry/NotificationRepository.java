package com.example.Beehyv.CaseStudy.Repositry;

import com.example.Beehyv.CaseStudy.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
  public   void deleteByEmployeeId(long id);
}
