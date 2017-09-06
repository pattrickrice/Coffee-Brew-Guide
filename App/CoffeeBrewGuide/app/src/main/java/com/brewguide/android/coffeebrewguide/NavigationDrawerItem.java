package com.brewguide.android.coffeebrewguide;

/**
 * This object is for each item in the navigation drawer. Each item will be
 * passed through a custom array adapter
 */

class NavigationDrawerItem {
    int icon;
    String name;

    /**
     * @param icon is the icon next to the item
     * @param name is the title of the menu item
     * */
    public NavigationDrawerItem(int icon, String name){
        this.icon = icon;
        this.name = name;
    }
}
