package com.minecraftlegend.inventoryapi.Events;


import com.minecraftlegend.inventoryapi.GUIContainer;
import org.bukkit.entity.HumanEntity;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class ContainerCloseEvent extends ContainerOpenEvent {

    /**
     * This event is triggered whenever a container is closed
     * @param container the closed container
     * @param player the player who triggered this event
     */
    public ContainerCloseEvent( GUIContainer container, HumanEntity player ) {
        super( container, player );
    }
}
