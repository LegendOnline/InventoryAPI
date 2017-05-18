package com.minecraftlegend.inventoryapi;


import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.event.inventory.InventoryType;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public interface GUILayout {

    /**
     * Main method to apply the defined layout as {@link GUIElement}'s in the container
     * @param container
     */
    void apply( GUIContainer container );

    /**
     *
     * @return the size, the container should have -> the layout has to specify the size
     */
    Vector2i getSize();

    /**
     *
     * @return the amount of slots the container can handle
     * -> width * height of {@link GUILayout#getSize()}
     */
    int getInventorySize();

    /**
     *
     * @return the inventory type of the container
     */
    InventoryType getInventoryType();
}
