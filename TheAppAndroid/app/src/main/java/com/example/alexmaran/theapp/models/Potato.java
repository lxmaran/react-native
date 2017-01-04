package com.example.alexmaran.theapp.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Potato {
    private String id;
    private String name;

    public Potato(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Potato(String name) {
        this.name = name;
    }

    public Potato() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Potato potato = (Potato) o;

        return id.equals(potato.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
