package main.java.com.minecraftlegend.inventoryapi.Events;

import org.bukkit.entity.Player;
import main.java.com.minecraftlegend.inventoryapi.Elements.GUIAnvil;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class AnvilResultEvent extends AnvilIngredient1Event{
    public AnvilResultEvent( GUIAnvil anvil, Player player) {
        super(anvil,player);
    }
}
