package com.company.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Next on 16.11.2016.
 */
@Entity
public class FamilyMember extends BaseEntity {
    private String name;
    @ManyToOne
    private Family family;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "familyMember")
    private List<Task> tasks;

    public FamilyMember() {}

    public FamilyMember(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    @Override
    public String toString() {
        return "FamilyMember{" +
                   "name='" + name + '\'' +
                   ", family=" + family +
                   "} " + super.toString();
    }
}
