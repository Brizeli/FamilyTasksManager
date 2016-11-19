package com.company.service;

import com.company.model.BaseEntity;
import com.company.model.Family;
import com.company.model.FamilyMember;
import com.company.model.Task;
import com.company.repository.FamilyTasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Next on 18.11.2016.
 */
@Service
public class FamilyTasksServiceImpl implements FamilyTasksService {
    private final DateFormat df = new SimpleDateFormat("hh:mm:ss");

    @Autowired
    private FamilyTasksRepository repository;

    @Override
    public List<FamilyMember> getAllFamilyMembers() {
        System.out.println("Getting all family members");
        return repository.getAllFamilyMembers();
    }

    @Override
    public List<Task> getAllTasks() {
        System.out.println("Getting all tasks");
        return repository.getAllTasks();
    }

    @Override
    public List<Family> getAllFamilies() {
        System.out.println("Getting all families");
        return repository.getAllFamilies();
    }

    @Override
    public Task addTask(FamilyMember member, Task task) {
        task.setFamily(member.getFamily());
        task.setMemberAdded(member);
        task = repository.save(task);
        System.out.printf("%s %s added a task %s\n", df.format(new Date()), member, task);
        return task;
    }

    @Override
    public Task deleteTask(FamilyMember member) {
        Task task = repository.deleteTask(member);
        if (task == null)
            System.out.printf("%s has no tasks\n", member.getFamily());
        else System.out.printf("%s %s has finished %s\n", df.format(new Date()), member, task);
        return task;
    }

    @Override
    public <T extends BaseEntity> void save(T entity) {
        entity = repository.save(entity);
        System.out.printf("%s Saving %s\n", df.format(new Date()), entity);
    }
}
