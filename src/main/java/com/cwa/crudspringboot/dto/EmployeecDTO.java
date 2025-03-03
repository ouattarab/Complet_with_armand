package com.cwa.crudspringboot.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EmployeecDTO {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String hireDate;
    private String jobId;
    private Long salary;
}
