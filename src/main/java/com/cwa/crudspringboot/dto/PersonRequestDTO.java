package com.cwa.crudspringboot.dto;

import lombok.Data;
import java.util.List;

@Data
public class PersonRequestDTO {
    private List<PersonDTO> persons;
}
