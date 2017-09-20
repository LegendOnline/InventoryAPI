package com.minecraftlegend.inventoryapi.Router;

import com.minecraftlegend.inventoryapi.McGui;
import org.bukkit.entity.Player;

import java.net.URI;
import java.util.HashSet;
import java.util.Optional;

/**
 * <h1>Navigation Singleton</h1>
 * <p>
 * This singleton class is used to open a McGui by path.
 * By default this class remembers the last 3 URI paths used
 * to open a Gui.
 * Use push() to navigate forward and pop() to go one page back.
 * </p>
 *
 * @author Drayke
 * @version 1.0
 * @since 31.08.2017.
 */
public final class Navigation {

    private final static Navigation instance = new Navigation();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Navigation getInstance() {
        return instance;
    }

    private Navigation() {
    }

    private boolean enableHistory = true;

    private int historyLength = 3;

    private HashSet< PlayerHistory > histories = new HashSet<>();


    /**
     * Push a route to player history (if enabled) and
     * opens the corresponding gui.
     *
     * @param player   the player
     * @param guiClazz the gui clazz
     *
     * @return the mc gui
     *
     * @see Navigation#enableHistory Navigation#enableHistory
     */
    public McGui push( Player player, Class< ? extends McGui > guiClazz ) {
        return push( player, Router.getInstance().getRouterPath( guiClazz ).toString() );
    }

    /**
     * Push a route to player history (if enabled) and
     * opens the corresponding gui.
     *
     * @param player the player
     * @param route  the route
     *
     * @return the gui instance
     *
     * @see Navigation#enableHistory Navigation#enableHistory
     */
    public McGui push( Player player, String route ) {
        //Open gui
        McGui ini = Router.getInstance().open( player, route );

        //Save URI to players history
        if ( enableHistory && ini != null )
            getHistory( player ).push( URI.create( route ) );

        return ini;
    }

    /**
     * Pop. Pops last gui path. The poped route will
     * be opened. If the history is empty, the current player
     * inventory will be closed.
     *
     * @param player the player
     */
    public void pop( Player player ) {

        if ( enableHistory ) {
            PlayerHistory history = getHistory( player );
            if ( history.canPop() ) {
                //Open last URI
                String lastRoute = history.pop().toString();
                Router.getInstance().open( player, lastRoute );
                return;
            }
        }

        //Default
        player.closeInventory();

    }

    /**
     * Checks if the player can pop. If there is no page
     * to go back, false will be returned.
     *
     * @param player the player
     *
     * @return true if player can go back in history, else false
     */
    public boolean canPop( Player player ) {
        if(!enableHistory) return false;
        return getHistory( player ).canPop();
    }

    /**
     * Gets the history of a player.
     *
     * @param player the player
     *
     * @return the history
     */
    public PlayerHistory getHistory( Player player ) {
        Optional< PlayerHistory > first = histories.stream().filter( playerHistory -> playerHistory.getPlayer().equals( player ) ).findFirst();
        if ( first.isPresent() )
            return first.get();

        PlayerHistory playerHistory = new PlayerHistory( player );
        histories.add( playerHistory );
        return playerHistory;
    }

    /**
     * Is history enabled.
     *
     * @return the boolean
     */
    public boolean isEnableHistory() {
        return enableHistory;
    }

    /**
     * Enable history tracking.
     *
     * @param enableHistory enable history
     */
    public void setEnableHistory( boolean enableHistory ) {
        this.enableHistory = enableHistory;
    }

    /**
     * Gets history length.
     *
     * @return the history length
     */
    public int getHistoryLength() {
        return historyLength;
    }

    /**
     * Sets history length.
     *
     * @param historyLength the history length
     */
    public void setHistoryLength( int historyLength ) {
        this.historyLength = historyLength;
    }
}
