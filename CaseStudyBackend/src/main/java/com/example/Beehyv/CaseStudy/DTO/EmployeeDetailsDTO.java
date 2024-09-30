package com.example.Beehyv.CaseStudy.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeDetailsDTO {


    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfJoining;
    private String designation;
    private long tenure;


    private boolean notifyByemployee = false;

    private boolean noifybyadmin = false;

    private List<AppraisalTaskDTO> tasks= new ArrayList<>();

    private AttributeDTO attributes = new AttributeDTO();

}
