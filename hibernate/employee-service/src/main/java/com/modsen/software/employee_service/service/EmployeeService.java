package com.modsen.software.employee_service.service;

import com.modsen.software.employee_service.domain.Employee;
import com.modsen.software.employee_service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public List<Employee> getEmployees() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return employeeRepository.findAll();
    }

    // TODO: Way 3 fix
    //    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Transactional
    public void incrementSalaryByUserId(Long id) {
        Employee employee = employeeRepository.findById(id).get();
        employee.setSalary(employee.getSalary().add(BigDecimal.TEN));
        employeeRepository.save(employee);

    }

    @Transactional
    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}
