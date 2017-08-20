package com.minecraftlegend.inventoryapi;


import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public interface GUIComponent extends Listener, Cloneable, Serializable{


    default void registerNativeListeners( JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents( this, plugin );
    }


    default void init() {
    }

    default void postInit() {
    }

    /**
     * Adds gui events to this component.
     * All Events will be specified for this component
     * @param event to register
     */
    void addEvent( GUIEvent event );

    /**
     * Removes an earlier registered event
     * @param event to remove
     */
    void removeEvent( GUIEvent event );

    /**
     *
     * @param event is fired globalized (like a normal event listener) therefor gui components will be null
     */
    void addGlobalEvent( GUIEvent event );

    /**
     * Removes an earlier registered global event
     * @param event to remove
     */
    void removeGlobalEvent( GUIEvent event );

    /**
     *
     * @return all global events
     */
    List<GUIEvent> getGlobalEvents();

    /**
     *
     * @return all registered events for this component
     */
    List<GUIEvent> getEvents();

    void setEvents(List<GUIEvent> events);
    void setGlobalEvents(List<GUIEvent> events);

    /**
     * Locks this component
     * -> if this is an Inventory, the whole inventory will be locked and no items can be moved
     * -> if this is an gui element, this specific element will be locked
     * Note: in most cases components are already locked by default
     */
    void lock();

    /**
     * unlocks this component
     */
    void unlock();

    /**
     *
     * @return the current lock state of this component
     */
    boolean isLocked();

    Object clone();

}
