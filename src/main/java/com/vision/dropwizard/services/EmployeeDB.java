package com.vision.dropwizard.services;

/**
 * @author sonveer.narwaria on 06/10/19
 * @project dropwizard-pipeline
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vision.dropwizard.model.Employee;

public class EmployeeDB {

    public static HashMap<Integer, Employee> employees = new HashMap<>();

    static {
        employees.put(1, new Employee(1, "Sanjana", "Singh", "IND"));
        employees.put(2, new Employee(2, "Sapna", "Sharma", "IND"));
        employees.put(4, new Employee(4, "Shakshi", "Bhadouriya", "IND"));
    }

    public static List<Employee> getEmployees() {
        return new ArrayList<>(employees.values());
    }

    public static Employee getEmployee(Integer id) {
        return employees.get(id);
    }

    public static void updateEmployee(Integer id, Employee employee) {
        employees.put(id, employee);
    }

    public static void removeEmployee(Integer id) {
        employees.remove(id);
    }
}