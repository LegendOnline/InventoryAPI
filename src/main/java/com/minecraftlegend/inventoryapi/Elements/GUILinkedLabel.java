package com.minecraftlegend.inventoryapi.Elements;

import org.bukkit.Material;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class GUILinkedLabel<T> extends GUILabel {

    private T element;

    public GUILinkedLabel( String title, T element ) {
        super( title );
        this.element = element;
    }

    public GUILinkedLabel( String title, Material icon, T element ) {
        super( title, icon );
        this.element = element;
    }

    public GUILinkedLabel( String title, Material icon, byte type, T element ) {
        super( title, icon, type );
        this.element = element;
    }

    public T getElement() {
        return element;
    }

    public void setElement( T element ) {
        this.element = element;
    }
}
