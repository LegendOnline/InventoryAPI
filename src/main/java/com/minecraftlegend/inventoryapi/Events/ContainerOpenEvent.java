package com.minecraftlegend.inventoryapi.Events;


import com.minecraftlegend.inventoryapi.GUIContainer;
import org.bukkit.entity.HumanEntity;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class ContainerOpenEvent implements EventWrapper{

    private GUIContainer container;
    private HumanEntity player;

    public ContainerOpenEvent(GUIContainer container, HumanEntity player) {
        this.container = container;
        this.player = player;
    }

    public GUIContainer getContainer() {
        return container;
    }

    public void setContainer(GUIContainer container) {
        this.container = container;
    }

    public HumanEntity getPlayer() {
        return player;
    }
}
