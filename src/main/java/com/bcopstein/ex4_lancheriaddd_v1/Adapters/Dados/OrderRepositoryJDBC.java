package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Dados;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order.OrderRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Customer;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.OrderItem;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Product;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Recipe;

@Component
public class OrderRepositoryJDBC implements OrderRepository{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderRepositoryJDBC(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order getOrder(long id) {
      String sql = "SELECT p.id, p.cliente_cpf, p.data_hora_pagamento, p.status, p.valor, p.impostos, p.desconto, p.valor_cobrado, " +
                   "c.nome, c.celular, c.endereco, c.email " +
                   "FROM pedidos p " +
                   "JOIN clientes c ON p.cliente_cpf = c.cpf " +
                   "WHERE p.id = ?";
      
      List<Order> pedidos = jdbcTemplate.query(
        sql,
        ps -> ps.setLong(1, id),
        (rs, rowNum) -> {
          long pedidoId = rs.getLong("id");
          String cpf = rs.getString("cliente_cpf");
          String nome = rs.getString("nome");
          String celular = rs.getString("celular");
          String endereco = rs.getString("endereco");
          String email = rs.getString("email");
          java.time.LocalDateTime dataHoraPagamento = rs.getTimestamp("data_hora_pagamento") != null ? 
              rs.getTimestamp("data_hora_pagamento").toLocalDateTime() : null;
          Order.Status status = Order.Status.valueOf(rs.getString("status"));
          double valor = rs.getDouble("valor");
          double impostos = rs.getDouble("impostos");
          double desconto = rs.getDouble("desconto");
          double valorCobrado = rs.getDouble("valor_cobrado");
          
          Customer  cliente = new Customer(cpf, nome, celular, endereco, email);
          
          List<OrderItem> itens = getItensOrder(pedidoId);
          
          return new Order(pedidoId, cliente, dataHoraPagamento, itens, status, valor, impostos, desconto, valorCobrado);
        }
      );
      return pedidos.isEmpty() ? null : pedidos.get(0);
    }
    
    private List<OrderItem> getItensOrder(long pedidoId) {
      String sql = "SELECT ip.quantidade, p.id as produto_id, p.descricao, p.preco " +
                   "FROM itens_pedido ip " +
                   "JOIN produtos p ON ip.produto_id = p.id " +
                   "WHERE ip.pedido_id = ?";
      
      return jdbcTemplate.query(sql, ps -> ps.setLong(1, pedidoId), (rs, rowNum) -> {
        int quantidade = rs.getInt("quantidade");
        long produtoId = rs.getLong("produto_id");
        String descricao = rs.getString("descricao");
        int preco = rs.getInt("preco");
        
        Recipe receita = new Recipe(produtoId, "Receita para " + descricao, java.util.Collections.emptyList());
        Product produto = new Product(produtoId, descricao, receita, preco);
        return new OrderItem(produto, quantidade);
      });
    }

    @Override
    public Order createOrder(Order order) {
        String orderSql = "INSERT INTO pedidos (id, cliente_cpf, data_hora_pagamento, status, valor, impostos, desconto, valor_cobrado) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(orderSql, 
            order.getId(),
            order.getCustomer().getCpf(),
            order.getPaymentDateTime(),
            order.getStatus().toString(),
            order.getValue(),
            order.getTaxes(),
            order.getDiscount(),
            order.getChargedValue()
        );
        
        String itemSql = "INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade) VALUES (?, ?, ?, ?)";
        String maxItemIdSql = "SELECT COALESCE(MAX(id), 0) FROM itens_pedido";
        long maxItemId = jdbcTemplate.queryForObject(maxItemIdSql, Long.class);
        long itemId = maxItemId + 1;
        
        for (OrderItem item : order.getItems()) {
            jdbcTemplate.update(itemSql, itemId++, order.getId(), item.getItem().getId(), item.getQuantity());
        }
        
        return order;
    }

    @Override
    public long getNextOrderId() {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM pedidos";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    @Override
    public List<Order> getAllOrdersPaginated(int page, int size) {
        int offset = page * size;
        String sql = "SELECT p.id, p.cliente_cpf, p.data_hora_pagamento, p.status, p.valor, p.impostos, p.desconto, p.valor_cobrado, " +
                     "c.nome, c.celular, c.endereco, c.email " +
                     "FROM pedidos p " +
                     "JOIN clientes c ON p.cliente_cpf = c.cpf " +
                     "ORDER BY p.id DESC " +
                     "LIMIT ? OFFSET ?";
        
        return jdbcTemplate.query(
            sql,
            ps -> {
                ps.setInt(1, size);
                ps.setInt(2, offset);
            },
            (rs, rowNum) -> {
                long pedidoId = rs.getLong("id");
                String cpf = rs.getString("cliente_cpf");
                String nome = rs.getString("nome");
                String celular = rs.getString("celular");
                String endereco = rs.getString("endereco");
                String email = rs.getString("email");
                java.time.LocalDateTime dataHoraPagamento = rs.getTimestamp("data_hora_pagamento") != null ? 
                    rs.getTimestamp("data_hora_pagamento").toLocalDateTime() : null;
                Order.Status status = Order.Status.valueOf(rs.getString("status"));
                double valor = rs.getDouble("valor");
                double impostos = rs.getDouble("impostos");
                double desconto = rs.getDouble("desconto");
                double valorCobrado = rs.getDouble("valor_cobrado");
                
                Customer cliente = new Customer(cpf, nome, celular, endereco, email);

                List<OrderItem> itens = getItensOrder(pedidoId);

                return new Order(pedidoId, cliente, dataHoraPagamento, itens, status, valor, impostos, desconto, valorCobrado);
            }
        );
    }

    @Override
    public long getTotalOrdersCount() {
        String sql = "SELECT COUNT(*) FROM pedidos";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0L;
    }
}
