package com.company.repository;

import com.company.model.BaseEntity;
import com.company.model.Family;
import com.company.model.FamilyMember;
import com.company.model.Task;

import java.util.List;

/**
 * Created by Next on 16.11.2016.
 */
public interface FamilyTasksRepository {
    List<FamilyMember> getAllFamilyMembers();

    List<Family> getAllFamilies();

    Task deleteTask(FamilyMember member);

    List<Task> getAllTasks();

    <T extends BaseEntity> T save(T o);

}
