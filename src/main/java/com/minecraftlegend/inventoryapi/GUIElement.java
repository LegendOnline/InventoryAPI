package com.minecraftlegend.inventoryapi;


import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.inventory.ItemStack;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public interface GUIElement extends GUIComponent {

    /**
     *
     * @return the parent this element belongs to
     */
    GUIComponent getParent();

    /**
     *
     * @param parent sets a new parent component
     */
    void setParent( GUIComponent parent );

    /**
     *
     * @return the size of this element, you can have mulit-slot elements such as {@link com.legend.cloud.clientapi.api.gui.elements.GUIProgressBar}
     */
    Vector2i getSize();

    /**
     *
     * @param dimension sets a new size
     */
    void setSize( Vector2i dimension );

    /**
     *
     * @return the current position in the parent component
     */
    Vector2i getPosition();

    /**
     *
     * @param dimension sets a new position - parent should be updated
     */
    void setPosition( Vector2i dimension );

    /**
     * draws the element to the parent - sets the item into the native minecraft inventory
     */
    void draw();

    /**
     * disposes the element from the parent - removes the item from the native minecraft inventory
     */
    void dispose();

    /**
     *
     * @return the minecraft {@link ItemStack} this element represents
     */
    ItemStack getNative();

}
