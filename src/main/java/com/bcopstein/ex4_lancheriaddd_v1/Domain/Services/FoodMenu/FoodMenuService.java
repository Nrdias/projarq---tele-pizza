package com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.FoodMenu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.FoodMenu.FoodMenuRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Menu;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.MenuHeader;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Product;

@Service
public class FoodMenuService {
    private FoodMenuRepository foodMenuRepository;

    @Autowired
    public FoodMenuService(FoodMenuRepository foodMenuRepository){
        this.foodMenuRepository = foodMenuRepository;
    }

    public Menu getFoodMenu(long Id){
        return foodMenuRepository.getById(Id);
    }

    public List<MenuHeader> getFoodMenus(){
        return foodMenuRepository.availableMenus();
    }

    public List<Product> getFoodMenuRecommendations(){
        return foodMenuRepository.chefRecommendations();
    }
}
