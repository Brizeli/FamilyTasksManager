package com.company.repository;

import com.company.model.BaseEntity;
import com.company.model.Family;
import com.company.model.FamilyMember;
import com.company.model.Task;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Next on 16.11.2016.
 */
@Repository
public class FamilyTasksRepositoryImpl implements FamilyTasksRepository {
    private DateFormat df = new SimpleDateFormat("hh:mm:ss");
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
    public List<Task> getAllTasks() {
        return em.createQuery("from Task", Task.class).getResultList();
    }

    @Override
    @Transactional
    public Task deleteTask(FamilyMember member) {
        List<Task> familyTasks =
            em.createQuery("from Task t where t.isFinished=false and t.family.id=:familyId", Task.class)
              .setParameter("familyId", member.getFamily().getId())
              .getResultList();
        if (familyTasks.isEmpty()) return null;
        Task task = familyTasks.get(0);
        task.setFinished(true);
        task.setMemberDeleted(member);
        return task;
    }

    @Override
    @Transactional
    public <T extends BaseEntity> T save(T entity) {
        if (entity.getId() == null) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
        return entity;
    }
}
