package com.minecraftlegend.inventoryapi.Events;


import com.minecraftlegend.inventoryapi.GUIContainer;
import org.bukkit.inventory.ItemStack;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2017 by Jan Hof
 * All rights reserved.
 **/
public class ComponentMoveEvent implements Event {

    private GUIContainer gui;
    private ItemStack item;

    /**
     * This event is triggered, whenever an {@link ItemStack} is moved from one, to another inventory
     * It is fired when the item is shift-clicked or manually transferred to the other inventory
     * @param gui the container which holds both inventories
     * @param component the moved item
     */
    public ComponentMoveEvent( GUIContainer gui, ItemStack component ) {
        this.gui = gui;
        this.item = component;
    }

    /**
     *
     * @return the triggered gui
     */
    public GUIContainer getGui() {
        return gui;
    }

    /**
     *
     * @return the moved item
     */
    public ItemStack getItem() {
        return item;
    }


}
