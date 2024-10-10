package com.example.Beehyv.CaseStudy.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long behaviour=0;
    private long CodeReadability=0;
    private long communication=0;
    private long  TimeManagement=0;
    private long TeamPlayer=0;
    private long Efficiency=0;
    private long java=0;
    private long angular=0;
    private long python=0;
    private long react=0;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="employee_id")
    private Employee employee;



}
