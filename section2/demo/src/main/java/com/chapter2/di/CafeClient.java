package com.chapter2.di;

import java.util.List;

public class CafeClient {
    public static void main(String[] args) {
        MenuService menuService = new MenuServiceStub();
        MenuController controller = new MenuController(menuService);
        List<Menu> menuList = controller.getMenus();
    }
}
