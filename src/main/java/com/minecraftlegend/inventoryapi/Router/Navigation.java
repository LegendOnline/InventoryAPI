package com.minecraftlegend.inventoryapi.Router;

import org.bukkit.entity.Player;

import java.net.URI;
import java.util.HashSet;
import java.util.Optional;

/**
 * <h1>InventoryAPI</h1>
 * <h2>Class heading</h2>
 * <p>
 * Class description.
 * </p>
 *
 * @author Drayke
 * @version 1.0
 * @since 31.08.2017.
 */
public final class Navigation {

    private final static Navigation instance = new Navigation();

    public static Navigation getInstance() {
        return instance;
    }

    private Navigation(){}

    private boolean enableHistory = true;

    private int historyLength = 3;

    private HashSet<PlayerHistory> histories = new HashSet<>( );

    public void push( Player player, String route ){
        //Open gui
        Router.getInstance().open( player,route );

        //Save URI to players history
        if(enableHistory)
            getHistory( player ).push( URI.create(route) );
    }

    public void pop( Player player ){

        if(enableHistory)
        {
            PlayerHistory history = getHistory( player );
            if(history.canPop())
            {
                //Open last URI
                String lastRoute = history.pop().toString();
                Router.getInstance().open( player, lastRoute);
                return;
            }
        }

        //Default
        player.closeInventory();

    }

    public PlayerHistory getHistory( Player player )
    {
        Optional< PlayerHistory > first = histories.stream().filter( playerHistory -> playerHistory.getPlayer().equals( player ) ).findFirst();
        if(first.isPresent())
            return  first.get();

        PlayerHistory playerHistory = new PlayerHistory( player );
        histories.add( playerHistory );
        return playerHistory;
    }

    public boolean isEnableHistory() {
        return enableHistory;
    }

    public void setEnableHistory( boolean enableHistory ) {
        this.enableHistory = enableHistory;
    }

    public int getHistoryLength() {
        return historyLength;
    }

    public void setHistoryLength( int historyLength ) {
        this.historyLength = historyLength;
    }
}

/***********************************************************************************************
 *
 *                  All rights reserved, MadDev (c) copyright 2017
 *
 ***********************************************************************************************/