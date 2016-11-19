package com.company.model;

import javax.persistence.*;

/**
 * Created by Next on 16.11.2016.
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    protected Integer id;

    protected String name;
    public BaseEntity() {}

    public BaseEntity(String name) {
        this.name=name;
    }

    public BaseEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
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
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id=" + id +
                   ", '" + name + '\'';
    }
}
