package com.example.alexmaran.theapp.models;

/**
 * Created by Alex on 12/21/2016.
 */

public class Potato {
    private int id;
    private String name;

    public Potato(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Potato(String name) {
        this.name = name;
    }

    public Potato() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
