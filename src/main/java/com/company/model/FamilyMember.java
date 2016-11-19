package com.company.model;

import javax.persistence.*;

/**
 * Created by Next on 16.11.2016.
 */
@Entity
public class FamilyMember extends BaseEntity {
    @ManyToOne
    private Family family;

    public FamilyMember() {}

    public FamilyMember(String name) {
        super(name);
    }

    public FamilyMember(int id, String name) {
        super(id, name);
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    @Override
    public String toString() {
        return "FamilyMember{" + super.toString() + ", " + family + '}';
    }
}
