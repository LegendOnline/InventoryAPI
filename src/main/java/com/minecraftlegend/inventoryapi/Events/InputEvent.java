package com.minecraftlegend.inventoryapi.Events;


import com.minecraftlegend.inventoryapi.GUIEvent;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2017 by Jan Hof
 * All rights reserved.
 **/
public interface InputEvent extends GUIEvent {

    /**
     * This event will be triggered, if any change to the input of {@link com.minecraftlegend.inventoryapi.Elements.GUInput}
     * has been made.
     * Furthermore there are 3 different types of changes, declared in {@link com.minecraftlegend.inventoryapi.Events.InputChangeEvent.InputActionType}
     * It is only fired if the {@link com.minecraftlegend.inventoryapi.Elements.GUInput} this event haven been applied to is changed
     * therefor you do not have to evaluate, if the changed input is the one this event has been attached to
     * @param event information about the triggered event
     */
    default void onInputChange(InputChangeEvent event){ }
}
