package com.company.repository;

import com.company.model.Family;
import com.company.model.FamilyMember;
import com.company.model.Task;
import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Next on 16.11.2016.
 */
@Repository
public class FamilyTasksRepositoryImpl implements FamilyTasksRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<FamilyMember> getAllFamilyMembers() {
        return em.createQuery("from FamilyMember", FamilyMember.class).getResultList();
    }

    @Override
    public List<Family> getAllFamilies() {
        return em.createQuery("from Family", Family.class).getResultList();
    }

    @Override
    @Transactional
    public Task addTask(FamilyMember member) {
        List<Task> freeTasks = em.createQuery("from Task where isFinished=false and familyMember=null", Task.class)
                                 .getResultList();
        if (freeTasks.isEmpty()) return null;
        Task task = freeTasks.get(0);
        task.setFamilyMember(member);
        System.out.printf(new Date() + "Member %s from Family %s added a Task %s\n", member.getName(),
            member.getFamily().getName(), task.getDescription());
        return task;
    }

    @Override
    @Transactional
    public Task deleteTask(FamilyMember member) {
        List<Task> memberTasks = em.createQuery("from Task t where t.familyMember.id=:memberId", Task.class)
                                   .setParameter("memberId", member.getId())
                                   .getResultList();
        Task memberTask = null;
        for (Task task : memberTasks) {
            if (!task.isFinished())
                memberTask = task;
        }
        if (memberTask == null) {
            System.out.printf("Member %s from Family %s has no tasks\n",
                member.getName(), member.getFamily().getName());
            return null;
        }
        Task task = em.find(Task.class, memberTask.getId());
        task.setFinished(true);
        System.out.printf(new Date() + "Member %s from Family %s finished a Task %s\n", member.getName(),
            member.getFamily().getName(), task.getDescription());
        return task;
    }

    @Override
    public List<Task> getAllUnfinishedTasks() {
        return em.createQuery("from Task where isFinished=false", Task.class).getResultList();
    }

    @Override
    public List<Task> getAllTasks() {
        return em.createQuery("from Task", Task.class).getResultList();
    }

    @Override
    public List<FamilyMember> getAllFamilyMembersWithTasks() {
        em.createQuery("from FamilyMember m left join fetch m.tasks");
        return null;
    }
}
