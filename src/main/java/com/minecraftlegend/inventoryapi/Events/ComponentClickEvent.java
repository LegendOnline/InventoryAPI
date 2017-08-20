package com.minecraftlegend.inventoryapi.Events;


import com.minecraftlegend.inventoryapi.GUIComponent;
import com.minecraftlegend.inventoryapi.GUIContainer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class ComponentClickEvent implements EventWrapper {

    private GUIContainer gui;
    private GUIComponent component;
    private ItemStack item, cursor;
    private HumanEntity player;
    private Inventory clickedInventory;
    private ClickType click;
    private int slot, rawSlot;

    public ComponentClickEvent(GUIContainer gui, GUIComponent component, ItemStack item, ItemStack cursor, Inventory clickedInventory, int slot, int rawSlot, HumanEntity player, ClickType click) {
        this.gui = gui;
        this.component = component;
        this.item = item;
        this.cursor = cursor;
        this.clickedInventory = clickedInventory;
        this.slot = slot;
        this.player = player;
        this.click = click;
        this.rawSlot = rawSlot;
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

    public ClickType getClick() {
        return click;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getCursor(){
        return cursor;
    }

    public int getRawSlot(){
        return rawSlot;
    }

    public Inventory getClickedInventory(){
        return clickedInventory;
    }
}
