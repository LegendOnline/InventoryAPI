package com.minecraftlegend.inventoryapi;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public interface GUIComponent extends Listener, Cloneable {


    default void registerNativeListeners(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    default void init() {
    }

    default void postInit() {
    }

    void addEvent(GUIEvent event);

    void removeEvent(GUIEvent event);

    List<GUIEvent> getEvents();


    void lock();

    void unlock();

    boolean isLocked();
    Object clone();

}
