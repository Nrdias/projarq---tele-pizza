package com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Recipe;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Recipe;

public interface RecipesRepository {
    Recipe getRecipe(long id);
}
