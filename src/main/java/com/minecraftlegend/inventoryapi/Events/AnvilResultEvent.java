package com.minecraftlegend.inventoryapi.Events;


import com.minecraftlegend.inventoryapi.Elements.GUIAnvil;
import org.bukkit.entity.Player;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class AnvilResultEvent extends AnvilIngredient1Event {

    private String text;

    public AnvilResultEvent( GUIAnvil anvil, Player player, String text ) {
        super( anvil, player );
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
