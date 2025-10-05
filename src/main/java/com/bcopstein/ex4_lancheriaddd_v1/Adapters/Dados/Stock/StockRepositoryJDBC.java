package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Dados.Stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Stock.StockRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Ingredient;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.StockItem;

@Component
public class StockRepositoryJDBC implements StockRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public StockRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<StockItem> getAllStockItems() {
        String sql = "SELECT ie.id, ie.quantidade, i.id as ingrediente_id, i.descricao " +
                     "FROM itensEstoque ie " +
                     "JOIN ingredientes i ON ie.ingrediente_id = i.id";
        
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long ingredienteId = rs.getLong("ingrediente_id");
            String descricao = rs.getString("descricao");
            int quantidade = rs.getInt("quantidade");
            
            Ingredient ingrediente = new Ingredient(ingredienteId, descricao);
            return new StockItem(ingrediente, quantidade);
        });
    }

    @Override
    public StockItem getStockItemByIngredientId(long ingredientId) {
        String sql = "SELECT ie.id, ie.quantidade, i.id as ingrediente_id, i.descricao " +
                     "FROM itensEstoque ie " +
                     "JOIN ingredientes i ON ie.ingrediente_id = i.id " +
                     "WHERE i.id = ?";
        
        List<StockItem> items = jdbcTemplate.query(sql, (rs, rowNum) -> {
            long ingredienteId = rs.getLong("ingrediente_id");
            String descricao = rs.getString("descricao");
            int quantidade = rs.getInt("quantidade");
            
            Ingredient ingrediente = new Ingredient(ingredienteId, descricao);
            return new StockItem(ingrediente, quantidade);
        }, ingredientId);
        
        return items.isEmpty() ? null : items.get(0);
    }

    @Override
    public boolean updateStockItem(long ingredientId, int newQuantity) {
        String sql = "UPDATE itensEstoque SET quantidade = ? WHERE ingrediente_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, newQuantity, ingredientId);
        return rowsAffected > 0;
    }
}
