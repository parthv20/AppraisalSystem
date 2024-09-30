package com.example.Beehyv.CaseStudy.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

//import jakarta.validation.constraints.Size;  Install Vlidation dependancyy fro this

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"tasks", "attributes"})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotBlank(message = "name is required")
    @Size(min = 3, message = "minimum 3 character required")
    private String name;

    @Column(nullable = false, unique = true)
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password should be atleast 8 character")
    private String password;

    private String phoneNumber;

    @Column(nullable = false)
    @NotNull(message = "Date of Joining required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfJoining;

    @Column(nullable = false)
    @NotBlank(message = "Designation is required")
    private String designation;

    @Column(nullable = false)
    private boolean isAdmin = false;

    @Transient
    private long tenure;

    private boolean notifybyemployee = false;

    private boolean noifybyadmin = false;


    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AppraisalTask> tasks = new HashSet<>();

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Attribute attributes;


    @PostLoad
    @PrePersist
    private void calculateAndSetTenure() {
        LocalDate currentDate= LocalDate.now();
        Period period = Period.between(dateOfJoining,currentDate);
        this.tenure= period.toTotalMonths();
    }

}
