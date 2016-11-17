package com.company.controller;

import com.company.MemberManager;
import com.company.model.Family;
import com.company.model.FamilyMember;
import com.company.model.Task;
import com.company.repository.FamilyTasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by Next on 16.11.2016.
 */
@Controller
public class FamilyTasksController {
    @Autowired
    FamilyTasksRepository repository;

    public void run() {
        List<FamilyMember> allFamilyMembers = repository.getAllFamilyMembers();
        Thread[] memberManagers = new Thread[allFamilyMembers.size()];
        for (int i = 0; i < memberManagers.length; i++) {
            memberManagers[i] = new MemberManager(allFamilyMembers.get(i), this);
            memberManagers[i].start();
        }
        for (int i = 0; i < memberManagers.length; i++) {
            try {
                memberManagers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        printStatistics();
    }

    private void printStatistics() {
        List<FamilyMember> familyMembersWithTasks = repository.getAllFamilyMembersWithTasks();
    }

    public void mmm() {
        List<Family> allFamilies = repository.getAllFamilies();
        for (Family family : allFamilies) {
            System.out.println(family);
        }
        List<FamilyMember> allFamilyMembers = repository.getAllFamilyMembers();
        for (FamilyMember familyMember : allFamilyMembers) {
            System.out.println(familyMember);
        }
        List<Task> allTasks = repository.getAllTasks();
        for (Task task : allTasks) {
            System.out.println(task);
        }
    }

    public synchronized Task addTask(FamilyMember member) {
        Task task = repository.addTask(member);
        return task;
    }

    public synchronized Task deleteTask(FamilyMember member) {
        return repository.deleteTask(member);
    }

    public synchronized boolean noMoreUnfinishedTasks() {
        return repository.getAllUnfinishedTasks().size() == 0;
    }
}
