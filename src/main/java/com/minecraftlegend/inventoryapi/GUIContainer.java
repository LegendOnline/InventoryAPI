package com.minecraftlegend.inventoryapi;


import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public interface GUIContainer extends GUIComponent {

    /**
     * Add other gui components to this one such as sub inventories like: {@link com.legend.cloud.clientapi.api.gui.elements.GUISubContainer}
     * or gui elements like: {@link com.legend.cloud.clientapi.api.gui.elements.GUILabel}
     * <br>
     * The parent component (this) should handle the drawing and management of the given component
     * see: {@link GUIElement#draw()}
     * @param component the component that should be added to this component
     */
    void add( GUIComponent component );

    /**
     * Removes the given component from the parent component.
     * This should also manage the removal of the native item from the Inventory
     * see: {@link GUIElement#dispose()}
     * @param component the component to remove
     */
    void remove( GUIComponent component );

    /**
     *
     * @return the list of all components, that are applied to this one
     */
    List<GUIComponent> getComponents();

    /**
     *
     * @param position where the wanted {@link GUIElement} can be found
     * @return GUIElement if there is at least one at the given position
     * Note: there can be multiple Elements / Components applied to one position!
     */
    GUIElement getElement( Vector2i position );

    /**
     * This can be used to separate gui and logic
     * @param name displayname of the wanted {@link GUIElement}
     * @return GUIElement if found, else null
     */
    GUIElement getElementByName( String name );

    /**
     *
     * @return the size of this container corresponding to the width and height of the minecraft inventory
     */
    Vector2i getSize();

    /**
     * Updates the size of this container.
     * This should also redraw this container and calculate all its components relatively towards the new dimension
     * @param dimension the container should have
     */
    void setSize( Vector2i dimension );

    /**
     *
     * @return the applied layout
     */
    GUILayout getLayout();

    /**
     * Updates the layout that designs this container
     * Rather than just setting the layout, this should also update the containers components
     * to be designed by the new layout
     * @param layout the new layout this container should be designed by
     */
    void setLayout( GUILayout layout );

    /**
     *
     * @return the native minecraft inventory
     */
    Inventory getInventory();

    /**
     * Switches out the native minecraft inventory
     * @param inventory the new inventory that should be displayed on {@link GUIContainer#draw(Player)}
     */
    void setInventory( Inventory inventory );

    /**
     * Displays the native minecraft inventory to the given player
     * @param player to whom the container should be opened
     */
    void draw( Player player );

    /**
     * Closes the native minecraft inventory and unregisters all previously registered events used by this container
     * @param player thats inevtory should be closed
     */
    void dispose( Player player );

    JavaPlugin getPlugin();
}
