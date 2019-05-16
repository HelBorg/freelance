package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.dto.TaskSkillDTO;
import com.freelance.project.demo.dto.UserSkillDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.models.TaskSkill;
import com.freelance.project.demo.repository.SkillRepository;
import com.freelance.project.demo.repository.TaskRepository;
import com.freelance.project.demo.repository.TaskSkillRepository;
import com.freelance.project.demo.service.TaskSkillService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskSkillServiceImpl implements TaskSkillService {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskSkillRepository taskSkillRepository;

    @Autowired
    private DozerBeanMapper mapper;

    public List<TaskSkillDTO> findAllForTask (int taskId){
        return taskSkillRepository.findAllByTaskId(taskRepository.findByTaskId(taskId)).stream()
                .map(entity -> mapper.map(entity, TaskSkillDTO.class))
                .collect(Collectors.toList());
    };

    public void deleteTaskSkill(int id){
        taskSkillRepository.delete(taskSkillRepository.findByTaskSkillId(id));
    }

    public void addNewTaskSkill(TaskSkillDTO taskSkillDTO, int taskId) {

        TaskSkill newTaskSkill = new TaskSkill();
        newTaskSkill.setTaskId(taskRepository.findByTaskId(taskId));
        newTaskSkill.setLevel(taskSkillDTO.getLevel());
        newTaskSkill.setSkillId(skillRepository.findSkillByName(taskSkillDTO.getSkillName().getName()));

        taskSkillRepository.save(newTaskSkill);

    }
}
