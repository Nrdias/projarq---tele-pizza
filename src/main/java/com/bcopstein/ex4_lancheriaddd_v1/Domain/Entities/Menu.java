package com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities;

import java.util.List;

public class Menu {
    private MenuHeader menuHeader;
    private List<Product> products;

    public Menu(long id, String title, List<Product> products) {
        this.menuHeader = new MenuHeader(id,title);
        this.products = products;
    }

    public long getId() { return menuHeader.id(); }
    public String getTitle(){ return menuHeader.title(); }
    public MenuHeader getMenuHeader(){ return menuHeader; }
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products){this.products = products;}
}
