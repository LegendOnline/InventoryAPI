package com.minecraftlegend.inventoryapi;


import com.minecraftlegend.inventoryapi.Events.Event;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public interface GUIEvent<E extends Event>{

    void call(E event);

}
