package com.minecraftlegend.inventoryapi.Elements;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class GUIButton extends GUILabel {

    private boolean lock = true;

    public GUIButton( String title ) {
        super( title );
    }

    public GUIButton( ItemStack item ) {
        super( item );
    }

    public GUIButton( String title, Material icon ) {
        super( title, icon );
    }

    public GUIButton( String title, Material icon, byte type ) {
        super( title, icon, type );
    }

    @Override
    public void lock() {
        this.lock = true;
    }

    @Override
    public void unlock() {
        this.lock = false;
    }

    @Override
    public boolean isLocked() {
        return lock;
    }
}
