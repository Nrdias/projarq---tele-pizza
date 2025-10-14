package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Presenters.cardapio.CabecalhoCardapioPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Presenters.cardapio.FoodMenuPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Application.FoodMenu.RecuperaListaCardapiosUC;
import com.bcopstein.ex4_lancheriaddd_v1.Application.FoodMenu.RecuperarCardapioUC;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.FoodMenuResponse;

@RestController
@RequestMapping("/menu")
public class FoodMenuController {
    private RecuperarCardapioUC getMenuUC;
    private RecuperaListaCardapiosUC getMenuListUC;

    public FoodMenuController(RecuperarCardapioUC getMenuUC,
                          RecuperaListaCardapiosUC getMenuListUC) {
        this.getMenuUC = getMenuUC;
        this.getMenuListUC = getMenuListUC;
    }

    @GetMapping("/{id}")
    @CrossOrigin("*")
    public ResponseEntity<FoodMenuPresenter> getMenu(@PathVariable(value="id") long id) {
        try {
            FoodMenuResponse menuResponse = getMenuUC.run(id);
            
            if (menuResponse == null || menuResponse.getCardapio() == null) {
                return ResponseEntity.notFound().build();
            }
            
            Set<Long> chefSuggestionIds = new HashSet<>(menuResponse.getSugestoesDoChef().stream()
                .map(product -> product.getId())
                .toList());
            FoodMenuPresenter menuPresenter = new FoodMenuPresenter(menuResponse.getCardapio().getMenuHeader().title());
            for (var product : menuResponse.getCardapio().getProducts()) {
                boolean isChefSuggestion = chefSuggestionIds.contains(product.getId());
                menuPresenter.insereItem(product.getId(), product.getDescription(), product.getPrice(), isChefSuggestion);
            }
            return ResponseEntity.ok(menuPresenter);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/list")
    @CrossOrigin("*")
    public ResponseEntity<List<CabecalhoCardapioPresenter>> getMenuList() {
        try {
            List<CabecalhoCardapioPresenter> menuHeaders =
                getMenuListUC.run().cabecalhos().stream()
                .map(header -> new CabecalhoCardapioPresenter(header.id(), header.title()))
                .toList();
            return ResponseEntity.ok(menuHeaders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
