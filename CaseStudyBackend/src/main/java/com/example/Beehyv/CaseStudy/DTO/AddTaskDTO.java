package com.example.Beehyv.CaseStudy.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddTaskDTO {
     private String name ;
     private String description;
    private LocalDate startDate;

    private LocalDate endDate;
    private boolean isAppraisable;

}
