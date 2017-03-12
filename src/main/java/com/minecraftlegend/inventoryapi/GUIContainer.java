package main.java.com.minecraftlegend.inventoryapi;

import main.java.com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public interface GUIContainer extends GUIComponent{

    void add(GUIComponent component);
    void remove(GUIComponent component);
    List<GUIComponent> getComponents();

    Vector2i getSize();
    void setSize(Vector2i dimension);

    void setLayout(GUILayout layout);
    GUILayout getLayout();

    Inventory getInventory();
    void setInventory(Inventory inventory);

    void draw(Player player);
    void dispose(Player player);
}
