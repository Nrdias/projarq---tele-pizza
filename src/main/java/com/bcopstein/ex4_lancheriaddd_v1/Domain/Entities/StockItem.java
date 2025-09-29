package com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities;

public class StockItem {
    private Ingredient ingredient;
    private int quantidade;

    public StockItem(Ingredient ingredient, int quantidade) {
        this.ingredient = ingredient;
        this.quantidade = quantidade;
    }

    public Ingredient getIngredient() { return ingredient; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
