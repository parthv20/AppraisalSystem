package com.example.Beehyv.CaseStudy.Repositry;



import com.example.Beehyv.CaseStudy.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public Optional<Employee> findByEmail(String email);
    @Query("SELECT p FROM Employee p WHERE " +
            "p.name LIKE CONCAT('%',:s, '%')")
    public List<Employee> searchEmployee(String s);
    public List<Employee> findAllByOrderByIdAsc();
}
