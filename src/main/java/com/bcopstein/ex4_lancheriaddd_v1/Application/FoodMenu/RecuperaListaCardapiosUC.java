package com.bcopstein.ex4_lancheriaddd_v1.Application.FoodMenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.CabecalhoCardapioResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.FoodMenu.FoodMenuService;

@Component
public class RecuperaListaCardapiosUC {
    private FoodMenuService cardapioService;

    @Autowired
    public RecuperaListaCardapiosUC(FoodMenuService cardapioService){
        this.cardapioService = cardapioService;
    }

    public CabecalhoCardapioResponse run(){
        return new CabecalhoCardapioResponse(cardapioService.getFoodMenus());
    }    
}
