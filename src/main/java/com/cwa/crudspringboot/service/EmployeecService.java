package com.cwa.crudspringboot.service;

import com.cwa.crudspringboot.dto.EmployeecRequestDTO;
import com.cwa.crudspringboot.entity.Employeec;

import java.util.List;

public interface EmployeecService {
    List<Employeec> saveEmployeecs(EmployeecRequestDTO employeecRequestDTO);
    List<Employeec> getAllEmployeecs();
}
