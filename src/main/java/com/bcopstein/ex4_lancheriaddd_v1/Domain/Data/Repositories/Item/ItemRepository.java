package com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Item;

import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.OrderItem;

public interface ItemRepository {
    public List<OrderItem> getOrderItems(long id);
}
