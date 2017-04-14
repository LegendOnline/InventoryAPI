package com.minecraftlegend.inventoryapi.EventListeners;


import com.minecraftlegend.inventoryapi.Elements.GUIAnvil;
import com.minecraftlegend.inventoryapi.Events.AnvilEvent;
import com.minecraftlegend.inventoryapi.Events.AnvilIngredient1Event;
import com.minecraftlegend.inventoryapi.Events.AnvilIngredient2Event;
import com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import com.minecraftlegend.inventoryapi.Events.ComponentDragEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerCloseEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerOpenEvent;
import com.minecraftlegend.inventoryapi.GUIComponent;
import com.minecraftlegend.inventoryapi.GUIElement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class GuiAnvilListener implements Listener {
    private GUIAnvil gui;

    public GuiAnvilListener(GUIAnvil gui){
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
                    }
                }
            }



        }


        if(event.getInventory().getType() == InventoryType.ANVIL){
            //event.setCancelled(true);

            Bukkit.getScheduler().runTaskTimer(gui.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    System.out.println("item 0: " + (event.getInventory().getItem(0) == null ? "null" : event.getInventory().getItem(0).getItemMeta().getDisplayName()));
                    System.out.println("item 1: " + (event.getInventory().getItem(1) == null ? "null" : event.getInventory().getItem(1).getItemMeta().getDisplayName()));
                    System.out.println("item 2: " + (event.getInventory().getItem(2) == null ? "null" : event.getInventory().getItem(2).getItemMeta().getDisplayName()));
                    //event.getInventory().getViewers().forEach(v -> ((Player)v).updateInventory());
                }
            },5,5);

            gui.getEvents().forEach(e -> {

                if(event.getRawSlot() == 0 && e instanceof AnvilEvent ){
                    ((AnvilEvent)e).onIngredient1Click(new AnvilIngredient1Event(gui, (Player) event.getWhoClicked()));
                }else if(event.getRawSlot() == 1 && e instanceof AnvilEvent){
                    ((AnvilEvent)e).onIngredient2Click(new AnvilIngredient2Event(gui, (Player) event.getWhoClicked()));
                }else if(event.getRawSlot() == 2 && e instanceof AnvilEvent){

                    //  gui.setResult(new GUILabel(event.getCurrentItem().getItemMeta().getDisplayName(),event.getCurrentItem().getType()));
                    // ((AnvilEvent)e).onResultClick(new AnvilResultEvent(gui, (Player) event.getWhoClicked()));
                }
            });
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
        System.out.println("call event: " + event.getInventory().getType());
        if(event.getInventory().equals(gui.getInventory())){
            gui.getEvents().forEach(e -> e.onClose(new ContainerCloseEvent(gui,event.getPlayer())));
        }
    }

}
