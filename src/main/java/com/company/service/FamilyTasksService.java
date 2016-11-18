package com.company.service;

import com.company.model.BaseEntity;
import com.company.model.Family;
import com.company.model.FamilyMember;
import com.company.model.Task;

import java.util.List;

/**
 * Created by Next on 18.11.2016.
 */
public interface FamilyTasksService {

    List<FamilyMember> getAllFamilyMembers();

    List<Task> getAllTasks();

    List<Family> getAllFamilies();

    Task addTask(FamilyMember member, Task task);

    Task deleteTask(FamilyMember member);

    <T extends BaseEntity> void save(T entity);
}
