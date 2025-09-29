package com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities;

import java.util.List;

public class Recipe {
    private long id;
    private String title;
    private List<Ingredient> ingredients;

    public Recipe(long id, String title, List<Ingredient> ingredients) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
    }

    public long getId() { return id; }
    public String getTitle(){ return title; }
    public List<Ingredient> getIngredients() { return ingredients; }
}
