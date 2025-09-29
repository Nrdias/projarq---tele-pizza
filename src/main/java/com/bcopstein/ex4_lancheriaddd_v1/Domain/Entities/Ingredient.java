package com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities;

public class Ingredient {
    private long id;
    private String description;

    public Ingredient(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() { return id; }
    public String getDescription() { return description; }
}
