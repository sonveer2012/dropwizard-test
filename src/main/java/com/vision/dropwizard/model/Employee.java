package com.vision.dropwizard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sonveer.narwaria on 06/10/19
 * @project dropwizard-pipeline
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer id;
    private String firstName;
    private String lastName;
    private String country;
}

