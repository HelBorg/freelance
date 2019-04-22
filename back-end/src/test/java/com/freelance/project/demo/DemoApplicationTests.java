package com.freelance.project.demo;

import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class  DemoApplicationTests {

    @Autowired
    private TaskService taskService;

    @Test
    public void contextLoads() {
//        assertThat(taskService.findAll()).isNotNull();
        Pager pager;
        pager = taskService.findAll( Optional.of(10), Optional.of(0), Optional.of("taskId"));
        List<Task> list = pager.getTasks();
        System.out.println("==============");
        for (Task task: list) {
            System.out.println(task.getName());
        }
        System.out.println("==============");
    }

}
