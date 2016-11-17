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
public class Family extends BaseEntity{
    private String name;
    @OneToMany(mappedBy = "family", cascade = CascadeType.REMOVE)
    private List<FamilyMember> members;

    public Family() {}

    public Family(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FamilyMember> getMembers() {
        return members;
    }

    public void setMembers(List<FamilyMember> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Family{" +
                   "name='" + name + '\'' +
                   "} " + super.toString();
    }
}
