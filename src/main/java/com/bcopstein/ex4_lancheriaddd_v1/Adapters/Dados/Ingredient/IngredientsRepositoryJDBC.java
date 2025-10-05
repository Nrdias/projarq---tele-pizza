package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Dados.Ingredient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Ingredients.IngredientsRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Ingredient;

@Repository
public class IngredientsRepositoryJDBC implements IngredientsRepository{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public IngredientsRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ingredient> getAll() {
        String sql = "SELECT id, descricao FROM ingredientes";
        List<Ingredient> ingredientes = this.jdbcTemplate.query(
                sql,
                ps -> {},
                (rs, rowNum) -> new Ingredient(rs.getLong("id"), rs.getString("descricao")));
        return ingredientes;
    }

    @Override
    public List<Ingredient> getRecipeIngredients(long id) {
        String sql = "SELECT i.id, i.descricao FROM ingredientes i " +
                "JOIN receita_ingrediente ri ON i.id = ri.ingrediente_id " +
                "WHERE ri.receita_id = ?";
        return jdbcTemplate.query(
                sql,
                ps -> ps.setLong(1, id),
                (rs, rowNum) -> new Ingredient(rs.getLong("id"), rs.getString("descricao")));
    }
}
