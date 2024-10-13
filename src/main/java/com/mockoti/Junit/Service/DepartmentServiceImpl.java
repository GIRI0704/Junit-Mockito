package com.mockoti.Junit.Service;

import com.mockoti.Junit.Enitity.Department;
import com.mockoti.Junit.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getById(Long leadId) {
        return departmentRepository.findById(leadId).get();
    }

    @Override
    public String deleteDept(Long leadId) {
        departmentRepository.deleteById(leadId);
        return "success";
    }

    @Override
    public String updateDept(Long leadId) {
        Optional<Department> optionalDepartment = departmentRepository.findById(leadId);
        if(optionalDepartment.isPresent()){
            Department department = optionalDepartment.get();
            department.setDepartmentAddress("giri");
            return "success";
        }
        return "failed";
    }
}
