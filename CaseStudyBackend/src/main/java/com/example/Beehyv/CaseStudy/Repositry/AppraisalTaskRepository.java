package com.example.Beehyv.CaseStudy.Repositry;



import com.example.Beehyv.CaseStudy.model.Employee;
import com.example.Beehyv.CaseStudy.model.AppraisalTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppraisalTaskRepository extends JpaRepository<AppraisalTask, Long> {
    public List<AppraisalTask> findAllByOrderByIdAsc();
}
