package com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities;

public class Product {
    private long id;
    private String description;
    private Recipe recipe;
    private int price;

    public Product(long id,String description, Recipe recipe, int price) {
        if (!Product.priceValid(price))
            throw new IllegalArgumentException("Invalid price: " + price);
        if (description == null || description.length() == 0)
            throw new IllegalArgumentException("Invalid description");
        if (recipe == null)
            throw new IllegalArgumentException("Invalid recipe");
        this.id = id;
        this.description = description;
        this.recipe = recipe;
        this.price = price;
    }

    public long getId(){
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if (!Product.priceValid(price))
            throw new IllegalArgumentException("Invalid price: " + price);
        this.price = price;
    }

    public static boolean priceValid(int price) {
        return price > 0;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", description=" + description + ", recipe=" + recipe + ", price=" + price + "]";
    }
    
}
