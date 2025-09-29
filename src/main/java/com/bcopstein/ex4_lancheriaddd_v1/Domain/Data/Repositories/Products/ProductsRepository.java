package com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Products;

import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Product;

public interface ProductsRepository {
    Product getProductById(long id);
    List<Product> getMenuProducts(long id);
}
