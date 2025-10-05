package com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Item.ItemRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order.OrderRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.OrderItem;

@Service
public class OrderService {
  private OrderRepository pedidoRepository;
  private ItemRepository itemRepository;

  @Autowired
  public OrderService(OrderRepository pedidoRepository, ItemRepository itemRepository) {
    this.pedidoRepository = pedidoRepository;
    this.itemRepository = itemRepository;
  }

  public Order getOrder(long id){
    return pedidoRepository.getOrder(id);
  }

  public List<OrderItem> getOrderItems(long id){
    return itemRepository.getOrderItems(id);
  }

}
