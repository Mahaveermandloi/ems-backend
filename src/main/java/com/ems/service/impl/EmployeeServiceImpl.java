package com.ems.service.impl;

import com.ems.dto.EmployeeDto;
import com.ems.entity.Employee;
import com.ems.exception.ResourceNotFoundException;
import com.ems.mapper.EmployeeMapper;
import com.ems.repository.EmployeeRepository;
import com.ems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl  implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);


        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }


    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
      Employee employee =  employeeRepository.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("Employee is doesnot exist with a given ID" + employeeId));
     return EmployeeMapper.mapToEmployeeDto(employee);

    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
     List<Employee> employees =  employeeRepository.findAll();

     return employees.stream().map((employee)->EmployeeMapper.mapToEmployeeDto(employee)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
     Employee employee=    employeeRepository.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("Employee Doesnot Exist with "+ employeeId));

     employee.setFirstName(updatedEmployee.getFirstName());
     employee.setLastName(updatedEmployee.getLastName());
employee.setEmail(updatedEmployee.getEmail());

Employee updatedEmployeeObj = employeeRepository.save(employee);

return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);

    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee=    employeeRepository.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("Employee Doesnot Exist with "+ employeeId));

employeeRepository.deleteById(employeeId);
    }


}
