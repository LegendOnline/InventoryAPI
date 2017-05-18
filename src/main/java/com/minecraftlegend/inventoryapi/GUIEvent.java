package com.minecraftlegend.inventoryapi;


import com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import com.minecraftlegend.inventoryapi.Events.ComponentDragEvent;
import com.minecraftlegend.inventoryapi.Events.ComponentMoveEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerCloseEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerOpenEvent;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public interface GUIEvent {

    /**
     * This event is triggered when ever a player clicks on a {@link GUIElement}
     * It is only fired if the {@link GUIElement} this event haven been applied to is clicked
     * therefor you do not have to evaluate, if the clicked {@link GUIElement} is the one this event has been attached to
     * @param event Object where you can get information about the triggered event
     */
    default void onClick( ComponentClickEvent event ) {
    }

    /**
     * This event is triggered when ever a player opens a {@link GUIContainer}
     * It is only fired if the {@link GUIContainer} this event haven been applied to is opened
     * therefor you do not have to evaluate, if the opened {@link GUIContainer} is the one this event has been attached to
     * @param event Object where you can get information about the triggered event
     */
    default void onOpen( ContainerOpenEvent event ) {
    }

    /**
     * This event is triggered when ever a player closes a {@link GUIContainer}
     * It is only fired if the {@link GUIContainer} this event haven been applied to is closed
     * therefor you do not have to evaluate, if the closed {@link GUIContainer} is the one this event has been attached to
     * @param event Object where you can get information about the triggered event
     */
    default void onClose( ContainerCloseEvent event ) {
    }

    /**
     * This event is triggered when ever a player drags a {@link GUIElement}
     * It is only fired if the {@link GUIElement} this event haven been applied to is dragged
     * therefor you do not have to evaluate, if the dragged {@link GUIElement} is the one this event has been attached to
     * @param event Object where you can get information about the triggered event
     */
    default void onDrag( ComponentDragEvent event ) {
    }

    /**
     * This event is triggered when ever a player moves a {@link GUIElement}
     * It is only fired if the {@link GUIElement} this event haven been applied to is moved
     * therefor you do not have to evaluate, if the moved {@link GUIElement} is the one this event has been attached to
     * @param event Object where you can get information about the triggered event
     */
    default void onMove( ComponentMoveEvent event ) {
    }
}
