package com.company.controller;

import com.company.FamilyTaskLogger;
import com.company.FamilyTaskLoggerImpl;
import com.company.MemberManager;
import com.company.model.*;
import com.company.service.FamilyTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * Created by Next on 16.11.2016.
 */
@Controller
public class FamilyTasksController {
    private static final int DURATION = 100;
    private static final int MIN_SLEEP = 500;
    private static final int MAX_SLEEP = 1000;
    private static final FamilyTaskLogger LOGGER = new FamilyTaskLoggerImpl();
    @Autowired
    FamilyTasksService service;

    public void run() throws Exception {
        List<FamilyMember> allFamilyMembers = service.getAllFamilyMembers();
        Thread[] memberManagers = new MemberManager[allFamilyMembers.size()];
        MemberManager.setDuration(DURATION);
        MemberManager.setMinSleep(MIN_SLEEP);
        MemberManager.setMaxSleep(MAX_SLEEP);
        for (int i = 0; i < memberManagers.length; i++) {
            memberManagers[i] = new MemberManager(allFamilyMembers.get(i), this);
            memberManagers[i].start();
        }
        for (Thread memberManager : memberManagers)
            memberManager.join();
    }

    public void printStatistics() {
        LOGGER.log("\n\n--------Statisticts-------\n");
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
            LOGGER.log(String.format("%s added %d and finished %d tasks", member, addedTasks, entry.getValue()[1]));
            if (addedTasks > maxAdded) {
                maxAdded = addedTasks;
                memberMaxTasks = member;
            }
        }
        LOGGER.log(String.format("\n%s added maximum %d tasks", memberMaxTasks, maxAdded));
        maxAdded = 0;
        Family familyWithMaxTasks = new Family();
        for (Map.Entry<Family, Integer> entry : familyTaskMap.entrySet()) {
            Integer addedTasks = entry.getValue();
            if (addedTasks > maxAdded) {
                maxAdded = addedTasks;
                familyWithMaxTasks = entry.getKey();
            }
        }
        LOGGER.log(String.format("\nFamily %s has maximum %d tasks", familyWithMaxTasks, maxAdded));
    }

    public Task addTask(FamilyMember member, Task task) {
        return service.addTask(member, task);
    }

    public Task deleteTask(FamilyMember member) {
        synchronized (member.getFamily()) {
            return service.deleteTask(member);
        }
    }

    public void printAll() {
        List<Family> allFamilies = service.getAllFamilies();
        for (Family family : allFamilies) {
            LOGGER.log(family);
        }
        List<FamilyMember> allFamilyMembers = service.getAllFamilyMembers();
        for (FamilyMember familyMember : allFamilyMembers) {
            LOGGER.log(familyMember);
        }
        List<Task> allTasks = service.getAllTasks();
        for (Task task : allTasks) {
            LOGGER.log(task);
        }
    }
}
