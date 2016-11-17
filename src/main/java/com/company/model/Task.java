package com.company.model;

import javax.persistence.*;

/**
 * Created by Next on 15.11.2016.
 */
@Entity
public class Task extends BaseEntity{
    private String description;
    private boolean isFinished = false;
    @ManyToOne
    private FamilyMember familyMember;
    public Task() {}

    public Task(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }


    public FamilyMember getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(FamilyMember familyMember) {
        this.familyMember = familyMember;
    }

    @Override
    public String toString() {
        return "Task{" +
                   "description='" + description + '\'' +
                   ", isFinished=" + isFinished +
                   ", familyMember=" + familyMember +
                   "} " + super.toString();
    }
}
