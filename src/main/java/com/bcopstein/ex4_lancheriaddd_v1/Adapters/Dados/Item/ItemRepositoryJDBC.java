package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Dados.Item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Item.ItemRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.OrderItem;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Product;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Recipe;

@Component
public class ItemRepositoryJDBC implements ItemRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemRepositoryJDBC(JdbcTemplate jdbcTemplate){
      this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<OrderItem> getOrderItems(long id) {
        String sql = "SELECT ip.quantidade, p.id as produto_id, p.descricao, p.preco " +
                   "FROM itens_pedido ip " +
                   "JOIN produtos p ON ip.produto_id = p.id " +
                   "WHERE ip.pedido_id = ?";

      return jdbcTemplate.query(sql, ps -> ps.setLong(1, id), (rs, rowNum) -> {
        int quantidade = rs.getInt("quantidade");
        long produtoId = rs.getLong("produto_id");
        String descricao = rs.getString("descricao");
        int preco = rs.getInt("preco");
        
        Recipe receita = new Recipe(produtoId, "Receita para " + descricao, java.util.Collections.emptyList());
        Product produto = new Product(produtoId, descricao, receita, preco);
        return new OrderItem(produto, quantidade);
      });
    }
    
}
