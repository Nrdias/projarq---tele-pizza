package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Dados.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Products.ProductsRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Recipe.RecipesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Product;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Recipe;

@Component
public class ProductsRepositoryJDBC implements ProductsRepository {
    private JdbcTemplate jdbcTemplate;
    private RecipesRepository receitasRepository;

    @Autowired
    public ProductsRepositoryJDBC(JdbcTemplate jdbcTemplate,RecipesRepository receitasRepository){
        this.jdbcTemplate = jdbcTemplate;
        this.receitasRepository = receitasRepository;
    }

    @Override
    public List<Product> getMenuProducts(long id) {
        String sql = "SELECT p.id, p.descricao, p.preco, pr.receita_id " +
                     "FROM produtos p " +
                     "JOIN cardapio_produto cp ON p.id = cp.produto_id " +
                     "JOIN produto_receita pr ON p.id = pr.produto_id " +
                     "WHERE cp.cardapio_id = ?";
        List<Product> produtos = this.jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, id),
            (rs, rowNum) -> {
                long produtoId = rs.getLong("id");
                String descricao = rs.getString("descricao");
                int preco = rs.getInt("preco");
                long receitaId = rs.getLong("receita_id");
                Recipe receita = receitasRepository.getRecipe(receitaId);
                return new Product(produtoId, descricao, receita, preco);
            }
        );
        return produtos;
    }

    @Override
    public Product getProductById(long id) {
        String sql = "SELECT p.id, p.descricao, p.preco, pr.receita_id " +
                     "FROM produtos p " +
                     "JOIN produto_receita pr ON p.id = pr.produto_id " +
                     "WHERE p.id = ?";
        List<Product> produtos = this.jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, id),
            (rs, rowNum) -> {
                long produtoId = rs.getLong("id");
                String descricao = rs.getString("descricao");
                int preco = rs.getInt("preco");
                long receitaId = rs.getLong("receita_id");
                Recipe receita = receitasRepository.getRecipe(receitaId);
                return new Product(produtoId, descricao, receita, preco);
            }
        );
        return produtos.isEmpty() ? null : produtos.getFirst();        
    }
    
}
