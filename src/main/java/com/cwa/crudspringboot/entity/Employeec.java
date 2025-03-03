package com.cwa.crudspringboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employeecopy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employeec {
    @Id
    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE_NUMBER")
    private String phone;
    @Column(name = "HIRE_DATE")
    private String hireDate;
    @Column(name = "JOB_ID")
    private String jobId;
    @Column(name = "SALARY")
    private Long salary;




}
