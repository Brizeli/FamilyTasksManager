package com.company;

import com.company.controller.FamilyTasksController;
import com.company.model.FamilyMember;
import com.company.model.Task;

/**
 * Created by Next on 15.11.2016.
 */
public class MemberManager extends Thread {
    private static int duration;
    private static int minSleep;
    private static int maxSleep;
    private FamilyMember member;
    private FamilyTasksController controller;

    public MemberManager(FamilyMember member, FamilyTasksController controller) {
        this.member = member;
        this.controller = controller;
    }

    public static void setDuration(int duration) {
        MemberManager.duration = duration;
    }

    public static void setMaxSleep(int maxSleep) {
        MemberManager.maxSleep = maxSleep;
    }

    public static void setMinSleep(int minSleep) {
        MemberManager.minSleep = minSleep;
    }

    @Override
    public void run() {
        for (int i = 0; i < duration; i++) {
            int addOrDelete = (int) (Math.random()*3);
            if (addOrDelete > 0) {
                controller.addTask(member, new Task(String.format("Task%03d", FamilyGenerator.getRandom(0, 1000))));
            } else {
                controller.deleteTask(member);
            }
            try {
                Thread.sleep(FamilyGenerator.getRandom(minSleep, maxSleep));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
