package com.minecraftlegend.inventoryapi.Layouts;


import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUILayout;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.event.inventory.InventoryType;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class PlainLayout implements GUILayout {

    private Vector2i size = new Vector2i( -1, -1 );
    private int invSize;
    private InventoryType type;

    public PlainLayout() {
    }

    public PlainLayout( InventoryType type ) {
        this.type = type;
    }

    public PlainLayout( Vector2i size ) {
        this.size = size;
        invSize = size.getX() * size.getY();
    }

    public PlainLayout( Vector2i size, InventoryType type ) {
        this.size = size;
        this.type = type;
        invSize = size.getX() * size.getY();
    }

    @Override
    public void apply( GUIContainer container ) {
        invSize = container.getInventory().getSize();
        type = container.getInventory().getType();
    }

    @Override
    public Vector2i getSize() {
        return size;
    }

    @Override
    public int getInventorySize() {
        return invSize;
    }

    @Override
    public InventoryType getInventoryType() {
        return type;
    }
}
