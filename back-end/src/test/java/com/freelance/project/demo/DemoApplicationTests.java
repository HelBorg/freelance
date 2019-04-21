package com.freelance.project.demo;

import com.freelance.project.demo.repository.TaskRepository;
import com.freelance.project.demo.service.TaskService;
import com.freelance.project.demo.service.impl.TaskServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class  DemoApplicationTests {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService  = new TaskServiceImpl(taskRepository);

    @Test
    public void contextLoads() {
        assertThat(taskService).isNotNull();
    }

}
