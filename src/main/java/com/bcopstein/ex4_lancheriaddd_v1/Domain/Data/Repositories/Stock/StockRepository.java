package com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Stock;

import java.util.List;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.StockItem;

public interface StockRepository {
    List<StockItem> getAllStockItems();
    StockItem getStockItemByIngredientId(long ingredientId);
    boolean updateStockItem(long ingredientId, int newQuantity);
}
