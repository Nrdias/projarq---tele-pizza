package com.bcopstein.ex4_lancheriaddd_v1.Application.Responses;

import java.util.List;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Menu;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Product;

public class FoodMenuResponse {
    private Menu cardapio;
    private List<Product> sugestoesDoChef;
    
    public FoodMenuResponse(Menu cardapio, List<Product> sugestoesDoChef) {
        this.cardapio = cardapio;
        this.sugestoesDoChef = sugestoesDoChef;
    }

    public Menu getCardapio() {
        return cardapio;
    }

    public List<Product> getSugestoesDoChef() {
        return sugestoesDoChef;
    }
}
