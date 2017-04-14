package com.minecraftlegend.inventoryapi.Events;


import com.minecraftlegend.inventoryapi.GUIEvent;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public interface AnvilEvent extends GUIEvent {

    default void onIngredient1Click(AnvilIngredient1Event event){}
    default void onIngredient2Click(AnvilIngredient2Event event){}
    default void onResultClick(AnvilResultEvent event){}

}
