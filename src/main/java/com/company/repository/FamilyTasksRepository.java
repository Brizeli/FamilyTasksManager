package com.company.repository;

import com.company.model.*;

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
