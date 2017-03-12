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
public class ComponentDragEvent extends ComponentClickEvent {
    public ComponentDragEvent(GUIContainer gui, GUIComponent component, HumanEntity player, ClickType click) {
        super(gui, component, player, click);
    }
}
