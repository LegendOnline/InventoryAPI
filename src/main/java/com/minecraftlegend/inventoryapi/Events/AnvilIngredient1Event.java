package main.java.com.minecraftlegend.inventoryapi.Events;

import org.bukkit.entity.Player;
import main.java.com.minecraftlegend.inventoryapi.Elements.GUIAnvil;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class AnvilIngredient1Event implements EventWrapper{

    private GUIAnvil anvil;
    private Player player;

    public AnvilIngredient1Event(GUIAnvil anvil, Player player) {
        this.anvil = anvil;
        this.player = player;
    }

    public GUIAnvil getAnvil() {
        return anvil;
    }

    public void setAnvil(GUIAnvil anvil) {
        this.anvil = anvil;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
