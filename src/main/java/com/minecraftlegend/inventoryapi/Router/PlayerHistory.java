package com.minecraftlegend.inventoryapi.Router;

import org.bukkit.entity.Player;

import java.net.URI;
import java.util.Stack;

/**
 * <h1>PlayerHistory</h1>
 * <p>
 * This class stores the last opened gui paths
 * for a player
 * </p>
 *
 * @author Drayke
 * @version 1.0
 * @since 01.09.2017.
 */
public class PlayerHistory {

    private Player player;

    private Stack< URI > history;

    /**
     * Instantiates a new Player history.
     *
     * @param player the player
     */
    public PlayerHistory( Player player ) {
        this.player = player;
        this.history = new Stack<>();
    }

    /**
     * Gets the player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Can pop or is history empty.
     *
     * @return false if empty, else true
     */
    public boolean canPop() {
        return !history.empty();
    }

    /**
     * Pop the history.
     *
     * @return the uri
     */
    public URI pop() {
        return history.pop();
    }

    /**
     * Current uri path.
     *
     * @return the uri
     */
    public URI current() {
        return history.peek();
    }

    /**
     * Open last gui and pop history.
     *
     * @return the boolean
     */
    public boolean openLastAndPop() {
        if ( !history.empty() ) {
            Router.getInstance().open( player, pop().toString() );
            return true;
        }
        return false;
    }

    /**
     * Push an URI in history.
     *
     * @param uri the uri
     */
    public void push( URI uri ) {
        int max = Navigation.getInstance().getHistoryLength();

        if ( history.size() >= max )
            history.remove( 0 );

        history.push( uri );
    }

    /**
     * Length of the history.
     * Limited by the Navigation setting.
     *
     * @return the length
     * @see Navigation#historyLength
     */
    public int length() {
        return history.size();
    }

}

/***********************************************************************************************
 *
 *                  All rights reserved, MadDev (c) copyright 2017
 *
 ***********************************************************************************************/