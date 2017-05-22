package com.minecraftlegend.inventoryapi.Events;


import com.minecraftlegend.inventoryapi.GUIComponent;
import com.minecraftlegend.inventoryapi.GUIContainer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.DragType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class ComponentDragEvent {
    private GUIContainer gui;
    private GUIComponent component;
    private Map<Integer,ItemStack> items;
    private HumanEntity player;
    private DragType click;

    /**
     *
     * @param gui
     * @param component
     * @param item
     * @param player
     * @param click
     */
    public ComponentDragEvent( GUIContainer gui, GUIComponent component, Map<Integer,ItemStack> item, HumanEntity player, DragType click ) {
        this.gui = gui;
        this.component = component;
        this.items = item;
        this.player = player;
        this.click = click;
    }

    public GUIContainer getGui() {
        return gui;
    }

    public GUIComponent getComponent() {
        return component;
    }

    public HumanEntity getPlayer() {
        return player;
    }

    public DragType getDrag() {
        return click;
    }

    public Map<Integer,ItemStack> getItems() {
        return items;
    }
}
