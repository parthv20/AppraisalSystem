package com.example.Beehyv.CaseStudy.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTO {
    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfJoining;
    private String designation;
    private long tenure;
}
