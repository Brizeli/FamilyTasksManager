package com.company.service;

import com.company.FamilyTaskLogger;
import com.company.FamilyTaskLoggerImpl;
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
    private final static FamilyTaskLogger LOGGER = new FamilyTaskLoggerImpl();
    private final DateFormat df = new SimpleDateFormat("hh:mm:ss");

    @Autowired
    private FamilyTasksRepository repository;

    @Override
    public List<FamilyMember> getAllFamilyMembers() {
        LOGGER.log("Getting all family members");
        return repository.getAllFamilyMembers();
    }

    @Override
    public List<Task> getAllTasks() {
        LOGGER.log("Getting all tasks");
        return repository.getAllTasks();
    }

    @Override
    public List<Family> getAllFamilies() {
        LOGGER.log("Getting all families");
        return repository.getAllFamilies();
    }

    @Override
    public Task addTask(FamilyMember member, Task task) {
        task.setFamily(member.getFamily());
        task.setMemberAdded(member);
        task = repository.save(task);
        LOGGER.log(df.format(new Date()), String.format("%s has added %s", member, task));
        return task;
    }

    @Override
    public Task deleteTask(FamilyMember member) {
        Task task = repository.deleteTask(member);
        if (task == null)
            LOGGER.log(member.getFamily() + " has no tasks");
        else LOGGER.log(df.format(new Date()), String.format("%s has finished %s", member, task));
        return task;
    }

    @Override
    public <T extends BaseEntity> void save(T entity) {
        entity = repository.save(entity);
        LOGGER.log(df.format(new Date()), "Saving " + entity);
    }
}
