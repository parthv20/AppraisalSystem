package com.example.Beehyv.CaseStudy.DTO;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDate;


@Data
public class AppraisalTaskDTO {

    @Nullable
    private long id;
    private String name;
    private String description;
    private LocalDate startDate;

    private LocalDate endDate;
    private boolean isAppraisable;
    private long adminrating ;
}
