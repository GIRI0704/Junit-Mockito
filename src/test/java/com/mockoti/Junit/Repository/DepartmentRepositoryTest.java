package com.mockoti.Junit.Repository;

import com.mockoti.Junit.Enitity.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class DepartmentRepositoryTest {
    @Mock
    private DepartmentRepository departmentRepository;

    Department department = Department.builder()
            .departmentId(1L)
            .departmentAddress("SALEM")
            .departmentCode("CSE101")
            .departmentName("COMPUTER SCIENCE")
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); // ensures that Mockito processes the @Mock and @InjectMocks annotations to initialize and inject mock dependencies before running the tests.
    }

    @Test
    public void findDDeptById(){
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.ofNullable(department));
        String deptName = "COMPUTER SCIENCE";
        String found = departmentRepository.findById(1L).get().getDepartmentName();
        assertEquals(deptName,found);
    }


}