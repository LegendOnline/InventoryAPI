package main.java.com.minecraftlegend.inventoryapi;

import main.java.com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.inventory.ItemStack;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public interface GUIElement extends GUIComponent{

    void setParent(GUIComponent parent);
    GUIComponent getParent();
    Vector2i getSize();
    void setSize(Vector2i dimension);
    void setPosition(Vector2i dimension);
    Vector2i getPosition();
    void draw();
    void dispose();

    ItemStack getNative();

}
