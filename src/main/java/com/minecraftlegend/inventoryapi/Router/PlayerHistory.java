package com.minecraftlegend.inventoryapi.Router;

import org.bukkit.entity.Player;

import java.net.URI;
import java.util.Stack;

/**
 * <h1>InventoryAPI</h1>
 * <h2>Class heading</h2>
 * <p>
 * Class description.
 * </p>
 *
 * @author Drayke
 * @version 1.0
 * @since 01.09.2017.
 */
public class PlayerHistory {

    private Player player;

    private Stack< URI > history;

    public PlayerHistory( Player player ) {
        this.player = player;
        this.history = new Stack<>();
    }

    public Player getPlayer() {
        return player;
    }

    public boolean canPop()
    {
        return !history.empty();
    }

    public URI pop() {
        return history.pop();
    }

    public URI current() {
        return history.peek();
    }

    public boolean openLastAndPop() {
        if(!history.empty()) {
            Router.getInstance().open( player, pop().toString() );
            return true;
        }
        return false;
    }

    public void push(URI uri)
    {
        int max = Navigation.getInstance().getHistoryLength();

        if(history.size() >= max)
            history.remove( 0 );

        history.push( uri );
    }

    public int length()
    {
        return history.size();
    }

}

/***********************************************************************************************
 *
 *                  All rights reserved, MadDev (c) copyright 2017
 *
 ***********************************************************************************************/