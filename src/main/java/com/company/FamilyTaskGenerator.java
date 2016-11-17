package com.company;

import com.company.model.Family;
import com.company.model.FamilyMember;
import com.company.model.Task;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Random;

/**
 * Created by Next on 16.11.2016.
 */
public class FamilyTaskGenerator {
    public static final Random rnd = new Random();
    private int numberOfFamilies;
    private int minMembers;
    private int maxMembers;
    private int numberOfTasks;
    private int familyCounter;

    @PersistenceContext(unitName = "familymanager")
    private EntityManager em;

    public FamilyTaskGenerator(int numberOfFamilies, int minMembers, int maxMembers, int numberOfTasks) {
        this.numberOfFamilies = numberOfFamilies;
        this.minMembers = minMembers;
        this.maxMembers = maxMembers;
        this.numberOfTasks = numberOfTasks;
    }
    @Transactional
    public void generateFamilies() {
        for (int i = 0; i < numberOfFamilies; i++) {
            Family family = generateFamily(getRandom(minMembers, maxMembers));
            em.persist(family);
        }
        for (int i = 0; i < numberOfTasks; i++) {
            em.persist(new Task(String.format("Task%03d",getRandom(0,1000))));
        }
    }

    public void clearDb() {
        System.out.println(em.createQuery("delete from Task").executeUpdate());
        System.out.println(em.createQuery("delete from FamilyMember").executeUpdate());
        System.out.println(em.createQuery("delete from Family").executeUpdate());
    }

    @Transactional
    private Family generateFamily(int numberOfMembers) {
        Family family = new Family(String.format("Family%02d", familyCounter++));
        for (int i = 0; i < numberOfMembers; i++) {
            FamilyMember familyMember = new FamilyMember(String.format("Name%03d", getRandom(0,1000)));
            familyMember.setFamily(family);
            em.persist(familyMember);
        }
        return family;
    }

    private int getRandom(int min, int max) {
        return min + rnd.nextInt(max - min);
    }
}
