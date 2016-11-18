package com.company.model;

import javax.persistence.*;

/**
 * Created by Next on 15.11.2016.
 */
@Entity
public class Task extends BaseEntity {
    private boolean isFinished = false;
    @ManyToOne
    private Family family;
    @OneToOne
    private FamilyMember memberAdded;
    @OneToOne
    private FamilyMember memberDeleted;

    public Task() {}

    public Task(String name) {
        super(name);
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    @Override
    public String toString() {
        return "Task{" + super.toString() + ", isFinished=" + isFinished + '}';
    }

    public FamilyMember getMemberAdded() {
        return memberAdded;
    }

    public void setMemberAdded(FamilyMember memberAdded) {
        this.memberAdded = memberAdded;
    }

    public FamilyMember getMemberDeleted() {
        return memberDeleted;
    }

    public void setMemberDeleted(FamilyMember memberDeleted) {
        this.memberDeleted = memberDeleted;
    }
}
