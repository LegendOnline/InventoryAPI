package com.minecraftlegend.inventoryapi.Events;


import com.minecraftlegend.inventoryapi.GUIComponent;
import org.bukkit.entity.HumanEntity;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2017 by Jan Hof
 * All rights reserved.
 **/
public class InputChangeEvent implements Event {

    private InputActionType action;
    private GUIComponent component;
    private HumanEntity player;

    /**
     * This event is triggered whenever a change to a {@link com.minecraftlegend.inventoryapi.Elements.GUInput} element is made
     * @param action the type of action
     * @param component the triggered component
     * @param player who triggered the event
     */
    public InputChangeEvent( InputActionType action, GUIComponent component, HumanEntity player ) {
        this.action = action;
        this.component = component;
        this.player = player;
    }

    /**
     *
     * @return the triggered {@link GUIComponent}
     */
    public GUIComponent getComponent() {
        return component;
    }

    /**
     *
     * @return the player as {@link HumanEntity} who triggered the event
     */
    public HumanEntity getPlayer() {
        return player;
    }

    public enum InputActionType{
        IN, OUT, CHANGE;
    }

    /**
     *
     * @return the type of change that has been made
     */
    public InputActionType getAction() {
        return action;
    }
}
