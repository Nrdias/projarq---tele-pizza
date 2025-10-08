package com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.FoodMenu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.FoodMenu.FoodMenuRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Products.ProductsRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Menu;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.MenuHeader;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Product;

@Service
public class FoodMenuService {
    private final FoodMenuRepository foodMenuRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public FoodMenuService(FoodMenuRepository foodMenuRepository, ProductsRepository productsRepository){
        this.foodMenuRepository = foodMenuRepository;
        this.productsRepository = productsRepository;
    }

    public Menu getFoodMenu(long id){
        Menu menu = foodMenuRepository.getById(id);
        if (menu != null) {
            List<Product> products = productsRepository.getMenuProducts(id);
            menu.setProducts(products);
        }
        return menu;
    }

    public List<MenuHeader> getFoodMenus(){
        return foodMenuRepository.availableMenus();
    }

    public List<Product> getFoodMenuRecommendations(){
        return List.of(productsRepository.getProductById(2L));
    }
}
