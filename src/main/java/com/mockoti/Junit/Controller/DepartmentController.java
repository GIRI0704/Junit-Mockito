package com.mockoti.Junit.Controller;

import com.mockoti.Junit.Enitity.Department;
import com.mockoti.Junit.Service.DepartmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class DepartmentController {
    @Autowired   // this annotation will help to map the object in IOC to the below reference
    DepartmentService departmentService;

    @PostMapping("/save/dept")
    public Department save(@Valid @RequestBody Department department){
        log.info("{}",department.getDepartmentAddress());
        return departmentService.save(department);
    }

    @GetMapping("/get/dept")
    public List<Department> getAll(){
        return departmentService.getAll();
    }

    @GetMapping("/get/dept/id/{leadId}")
    public Department getById(@PathVariable("leadId") Long leadId){
        return departmentService.getById(leadId);
    }

    @DeleteMapping("/delete/dept/{leadId}")
    public String deleteDept(@PathVariable("leadId") Long leadId){
        return departmentService.deleteDept(leadId);
    }

    @PutMapping("/update/dept/{leadId}")
    public String updateDept(@PathVariable("leadId") Long leadId){
        return departmentService.updateDept(leadId);
    }
}
