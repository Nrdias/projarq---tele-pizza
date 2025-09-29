package com.bcopstein.ex4_lancheriaddd_v1.Application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.FoodMenuResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Menu;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Product;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.FoodMenu.FoodMenuService;

@Component
public class RecuperarCardapioUC {
    private FoodMenuService cardapioService;

    @Autowired
    public RecuperarCardapioUC(FoodMenuService cardapioService){
        this.cardapioService = cardapioService;
    }

    public FoodMenuResponse run(long idCardapio){
        Menu cardapio = cardapioService.getFoodMenu(idCardapio);
        List<Product> sugestoes = cardapioService.getFoodMenuRecommendations();
        return new FoodMenuResponse(cardapio,sugestoes);
    }
}
