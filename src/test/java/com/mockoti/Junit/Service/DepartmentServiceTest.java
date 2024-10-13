package com.mockoti.Junit.Service;

import com.mockoti.Junit.Enitity.Department;
import com.mockoti.Junit.Repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepartmentServiceTest {
    @Autowired
    DepartmentService departmentService;

    @Mock
    DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        Department department = Department.builder()
                .departmentId(1L)
                .departmentAddress("SALEM")
                .departmentCode("IT101")
                .departmentName("IT")
                .build();
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.ofNullable(department));
    }

    @Test
    @DisplayName("get by ID")
//    @Disabled
    void getById() {
        String deptName = "IT";
        Department department = departmentService.getById(1L);
        String found = department.getDepartmentName();
        assertEquals(deptName,found);
    }
}