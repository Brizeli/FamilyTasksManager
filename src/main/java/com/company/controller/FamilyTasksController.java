package com.company.controller;

import com.company.FamilyGenerator;
import com.company.MemberManager;
import com.company.model.Family;
import com.company.model.FamilyMember;
import com.company.model.Task;
import com.company.service.FamilyTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * Created by Next on 16.11.2016.
 */
@Controller
public class FamilyTasksController {
    private static final int DURATION = 10;
    private static final int MIN_SLEEP = 500;
    private static final int MAX_SLEEP = 1000;
    @Autowired
    FamilyTasksService service;

    public void run() {
        List<FamilyMember> allFamilyMembers = service.getAllFamilyMembers();
        Thread[] memberManagers = new MemberManager[allFamilyMembers.size()];
        MemberManager.setDuration(DURATION);
        MemberManager.setMinSleep(MIN_SLEEP);
        MemberManager.setMaxSleep(MAX_SLEEP);
        for (int i = 0; i < memberManagers.length; i++) {
            memberManagers[i] = new MemberManager(allFamilyMembers.get(i), this);
            memberManagers[i].start();
        }
        for (Thread memberManager : memberManagers) {
            try {
                memberManager.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printStatistics() {
        List<Task> allTasks = service.getAllTasks();
        Map<FamilyMember, Integer[]> membersTaskMap = new HashMap<>();
        Map<Family, Integer> familyTaskMap = new HashMap<>();
        for (Task task : allTasks) {
            FamilyMember memberAddedTask = task.getMemberAdded();
            Family family = memberAddedTask.getFamily();
            Integer count = familyTaskMap.get(family);
            if (count == null) count = 0;
            familyTaskMap.put(family, count + 1);
            Integer[] countAddDel = membersTaskMap.get(memberAddedTask);
            if (countAddDel == null) countAddDel = new Integer[]{0, 0};
            countAddDel[0]++;
            membersTaskMap.put(memberAddedTask, countAddDel);
            FamilyMember memberDeletedTask = task.getMemberDeleted();
            if (memberDeletedTask == null) continue;
            countAddDel = membersTaskMap.get(memberDeletedTask);
            if (countAddDel == null) countAddDel = new Integer[]{0, 0};
            countAddDel[1]++;
            membersTaskMap.put(memberDeletedTask, countAddDel);
        }
        int maxAdded = 0;
        FamilyMember memberMaxTasks = new FamilyMember();
        for (Map.Entry<FamilyMember, Integer[]> entry : membersTaskMap.entrySet()) {
            FamilyMember member = entry.getKey();
            Integer addedTasks = entry.getValue()[0];
            System.out.printf("%s added %d and finished %d tasks\n", member, addedTasks, entry.getValue()[1]);
            if (addedTasks > maxAdded) {
                maxAdded = addedTasks;
                memberMaxTasks = member;
            }
        }
        System.out.printf("\n%s added maximum %d tasks\n", memberMaxTasks, maxAdded);
        maxAdded = 0;
        Family familyWithMaxTasks = new Family();
        for (Map.Entry<Family, Integer> entry : familyTaskMap.entrySet()) {
            Integer addedTasks = entry.getValue();
            if (addedTasks > maxAdded) {
                maxAdded = addedTasks;
                familyWithMaxTasks = entry.getKey();
            }
        }
        System.out.printf("\nFamily %s has maximum %d tasks\n", familyWithMaxTasks, maxAdded);
    }

    public void printAll() {
        List<Family> allFamilies = service.getAllFamilies();
        for (Family family : allFamilies) {
            System.out.println(family);
        }
        List<FamilyMember> allFamilyMembers = service.getAllFamilyMembers();
        for (FamilyMember familyMember : allFamilyMembers) {
            System.out.println(familyMember);
        }
        List<Task> allTasks = service.getAllTasks();
        for (Task task : allTasks) {
            System.out.println(task);
        }
    }

    public Task addTask(FamilyMember member) {
        Task task = new Task(String.format("Task%03d", FamilyGenerator.getRandom(0, 1000)));
        return service.addTask(member, task);
    }

    public Task deleteTask(FamilyMember member) {
        synchronized (member.getFamily()) {
            return service.deleteTask(member);
        }
    }
}
