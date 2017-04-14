package com.minecraftlegend.inventoryapi.EventListeners;


import com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import com.minecraftlegend.inventoryapi.Events.ComponentDragEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerCloseEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerOpenEvent;
import com.minecraftlegend.inventoryapi.GUIComponent;
import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUIElement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class McGuiListener implements Listener {

    private GUIContainer gui;

    public McGuiListener(GUIContainer gui){
        this.gui = gui;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getInventory().equals(gui.getInventory())){
            event.setCancelled(gui.isLocked());
            for (GUIComponent guiComponent : gui.getComponents()) {
                if(guiComponent instanceof GUIElement ){
                    event.setCancelled(guiComponent.isLocked());
                    if(((GUIElement) guiComponent).getNative().equals(event.getCurrentItem())){
                        guiComponent.getEvents().forEach(e -> e.onClick(new ComponentClickEvent(gui,guiComponent, event.getWhoClicked(), event.getClick())));
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event){
        if(event.getInventory().equals(gui.getInventory())){
            event.setCancelled(gui.isLocked());
            for (GUIComponent guiComponent : gui.getComponents()) {
                if(guiComponent instanceof GUIElement){
                    event.setCancelled(guiComponent.isLocked());
                    if(((GUIElement) guiComponent).getNative().equals(event.getCursor())){
                        guiComponent.getEvents().forEach(e -> e.onClick(new ComponentDragEvent(gui,guiComponent,event.getWhoClicked(),null)));
                        return;
                    }
                }
            }
        }
    }


    @EventHandler
    public void onOpen(InventoryOpenEvent event){
        if(event.getInventory().equals(gui.getInventory()))
         gui.getEvents().forEach(e -> e.onOpen(new ContainerOpenEvent(gui, event.getPlayer())));
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(event.getInventory().equals(gui.getInventory())){
            gui.getEvents().forEach(e -> e.onClose(new ContainerCloseEvent(gui,event.getPlayer())));
        }
    }


}
