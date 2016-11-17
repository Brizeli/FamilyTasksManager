package com.company;

import com.company.controller.FamilyTasksController;
import com.company.model.FamilyMember;

/**
 * Created by Next on 15.11.2016.
 */
public class MemberManager extends Thread {

    private FamilyMember member;
    private FamilyTasksController controller;

    public MemberManager(FamilyMember member, FamilyTasksController controller) {
        this.member = member;
        this.controller = controller;
    }

    @Override
    public void run() {
        while (true) {
            if (controller.noMoreUnfinishedTasks()) break;
            int addOrDelete = (int) (Math.random()*3);
            if (addOrDelete > 0) {
                controller.addTask(member);
            } else {
                controller.deleteTask(member);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
