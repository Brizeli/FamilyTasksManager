package com.company.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Next on 16.11.2016.
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @SequenceGenerator(name = "global_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private int id;

    public BaseEntity() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
