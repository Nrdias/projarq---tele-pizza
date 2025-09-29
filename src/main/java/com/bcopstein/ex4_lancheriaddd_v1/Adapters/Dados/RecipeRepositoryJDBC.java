
package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Dados;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Ingredients.IngredientsRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Recipe.RecipesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Ingredient;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Recipe;

@Repository
public class RecipeRepositoryJDBC implements RecipesRepository {
	private JdbcTemplate jdbcTemplate;
    private IngredientsRepository ingredientsRepository;

	@Autowired
	public RecipeRepositoryJDBC(JdbcTemplate jdbcTemplate,IngredientsRepository ingredientsRepository) {
		this.jdbcTemplate = jdbcTemplate;
        this.ingredientsRepository = ingredientsRepository;
	}

    @Override
    public Recipe getRecipe(long id) {
        String sql = "SELECT r.id, r.titulo FROM receitas r WHERE r.id = ?";
        List<Recipe> receitas = this.jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, id),
            (rs, rowNum) -> {
                long receitaId = rs.getLong("id");
                String titulo = rs.getString("titulo");
                List<Ingredient> ingredientes = ingredientsRepository.getRecipeIngredients(receitaId);
                return new Recipe(receitaId, titulo, ingredientes); 
            }
        );
        return receitas.isEmpty() ? null : receitas.get(0);
    }

}


