package com.minecraftlegend.inventoryapi.Events;


import com.minecraftlegend.inventoryapi.GUIContainer;
import org.bukkit.entity.HumanEntity;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class ContainerOpenEvent implements EventWrapper {

    private GUIContainer container;
    private HumanEntity player;

    /**
     * This event is triggered whenever a player (as {@link HumanEntity}) opens an {@link GUIContainer}
     * @param container the opened container
     * @param player the player who triggered this event
     */
    public ContainerOpenEvent( GUIContainer container, HumanEntity player ) {
        this.container = container;
        this.player = player;
    }

    /**
     *
     * @return the container that has been opened
     */
    public GUIContainer getContainer() {
        return container;
    }

    /**
     * Sets a container that should be opened instead
     * @param container the alternative container
     */
    public void setContainer( GUIContainer container ) {
        this.container = container;
    }

    /**
     *
     * @return the player who triggered this event as a {@link HumanEntity}
     */
    public HumanEntity getPlayer() {
        return player;
    }
}
