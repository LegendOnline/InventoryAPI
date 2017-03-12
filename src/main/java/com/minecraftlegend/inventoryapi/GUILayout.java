package main.java.com.minecraftlegend.inventoryapi;

import main.java.com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.event.inventory.InventoryType;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public interface GUILayout {

     void apply(GUIContainer container);
     Vector2i getSize();
     int getInventorySize();
     InventoryType getInventoryType();
}
