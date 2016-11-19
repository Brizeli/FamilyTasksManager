package com.company;

import com.company.model.Family;
import com.company.model.FamilyMember;
import com.company.service.FamilyTasksService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Next on 16.11.2016.
 */
public class FamilyGenerator {
    private int numberOfFamilies;
    private int minMembers;
    private int maxMembers;
    private int familyCounter;

    @Autowired
    private FamilyTasksService service;

    public FamilyGenerator(int numberOfFamilies, int minMembers, int maxMembers) {
        this.numberOfFamilies = numberOfFamilies;
        this.minMembers = minMembers;
        this.maxMembers = maxMembers;
    }

    public void generateFamilies() {
        for (int i = 0; i < numberOfFamilies; i++) {
            generateFamily(getRandom(minMembers, maxMembers));
        }
    }
//    @Transactional
//    public void clearDb() {
//        System.out.println(em.createQuery("delete from Task").executeUpdate());
//        System.out.println(em.createQuery("delete from FamilyMember").executeUpdate());
//        System.out.println(em.createQuery("delete from Family").executeUpdate());
//    }

    private void generateFamily(int numberOfMembers) {
        Family family = new Family(String.format("Family%02d", familyCounter++));
        service.save(family);
        for (int i = 0; i < numberOfMembers; i++) {
            FamilyMember familyMember = new FamilyMember(String.format("Name%03d", getRandom(0,1000)));
            familyMember.setFamily(family);
            service.save(familyMember);
        }
    }

    public static int getRandom(int min, int max) {
        return min + (int)(Math.random()*(max - min));
    }
}
