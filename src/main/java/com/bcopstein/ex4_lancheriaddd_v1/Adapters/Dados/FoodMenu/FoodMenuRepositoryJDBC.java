package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Dados.FoodMenu;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.FoodMenu.FoodMenuRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Products.ProductsRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.MenuHeader;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Menu;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Product;

@Component
public class FoodMenuRepositoryJDBC implements FoodMenuRepository{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public FoodMenuRepositoryJDBC(JdbcTemplate jdbcTemplate,ProductsRepository  productsRepository){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Menu getById(long id) {
        String sql = "SELECT id, titulo FROM cardapios WHERE id = ?";
        List<Menu> menus = this.jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, id),
            (rs, rowNum) -> new Menu(rs.getLong("id"), rs.getString("titulo"), null)
        );
        if (menus.isEmpty()) {
            return null;
        }
        Menu menu = menus.getFirst();
        List<Product> products = productsRepository.getMenuProducts(id);
        menu.setProducts(products);
        return menu;
    }

    @Override
    // For now returns always the cheese and ham pizza as "chef" recommendation
    public List<Product> chefRecommendations() {
        return List.of(productsRepository.getProductById(2L));   
    }

    @Override
    public List<MenuHeader> availableMenus(){
        String sql = "SELECT id, titulo FROM cardapios";
        List<MenuHeader> menuHeaders = this.jdbcTemplate.query(
            sql,
            ps->{},
            (rs, rowNum) -> new MenuHeader(rs.getLong("id"), rs.getString("titulo"))
        );
        return menuHeaders;
    }
    
}
