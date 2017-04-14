package com.minecraftlegend.inventoryapi;


import com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import com.minecraftlegend.inventoryapi.Events.ComponentDragEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerCloseEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerOpenEvent;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public interface GUIEvent {

    default void onClick(ComponentClickEvent event){}
    default void onOpen(ContainerOpenEvent event){}
    default void onClose(ContainerCloseEvent event){}
    default void onDrag(ComponentDragEvent event){}


}
