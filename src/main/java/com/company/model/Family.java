package com.company.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Next on 16.11.2016.
 */
@Entity
public class Family extends BaseEntity {
    @OneToMany(mappedBy = "family", cascade = CascadeType.REMOVE)
    private List<FamilyMember> members;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "family")
    private List<Task> tasks;

    public Family() {}

    public Family(String name) {
        super(name);
    }

    public Family(int id, String name) {
        super(id,name);
    }

    public List<FamilyMember> getMembers() {
        return members;
    }

    public void setMembers(List<FamilyMember> members) {
        this.members = members;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Family{" + super.toString() + '}';
    }
}
