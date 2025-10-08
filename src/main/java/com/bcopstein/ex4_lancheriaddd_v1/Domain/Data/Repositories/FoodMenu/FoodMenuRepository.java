
package com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.FoodMenu;

import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.MenuHeader;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Menu;

public interface FoodMenuRepository {
    List<MenuHeader> availableMenus();
    Menu getById(long id);
}
