package main.java.com.minecraftlegend.inventoryapi.Events;

import main.java.com.minecraftlegend.inventoryapi.GUIComponent;
import main.java.com.minecraftlegend.inventoryapi.GUIContainer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class ComponentClickEvent implements EventWrapper{

    private GUIContainer gui;
    private GUIComponent component;
    private HumanEntity player;
    private ClickType click;

    public ComponentClickEvent(GUIContainer gui, GUIComponent component, HumanEntity player, ClickType click) {
        this.gui = gui;
        this.component = component;
        this.player = player;
        this.click = click;
    }

    public GUIContainer getGui() {
        return gui;
    }

    public GUIComponent getComponent() {
        return component;
    }

    public HumanEntity getPlayer() {
        return player;
    }

    public ClickType getClick() {
        return click;
    }
}
