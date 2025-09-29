package com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Ingredients;

import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Ingredient;

public interface IngredientsRepository {
    List<Ingredient> getAll();
    List<Ingredient> getRecipeIngredients(long id);
}
