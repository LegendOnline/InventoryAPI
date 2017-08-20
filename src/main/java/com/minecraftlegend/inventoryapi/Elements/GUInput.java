package com.minecraftlegend.inventoryapi.Elements;


import com.minecraftlegend.inventoryapi.Events.ContainerCloseEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerOpenEvent;
import com.minecraftlegend.inventoryapi.Events.InputChangeEvent;
import com.minecraftlegend.inventoryapi.Events.InputEvent;
import com.minecraftlegend.inventoryapi.GUIComponent;
import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUIElement;
import com.minecraftlegend.inventoryapi.GUIEvent;
import com.minecraftlegend.inventoryapi.utils.InventorySlotWatcher;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2017 by Jan Hof
 * All rights reserved.
 **/
public class GUInput implements GUIElement {
    private Vector2i position, size;
    private GUIContainer parent;
    private List<GUIEvent> events = new ArrayList<>();


    private void setup() {
        InventorySlotWatcher[] slotWatchers = new InventorySlotWatcher[]{
                new InventorySlotWatcher(parent.getPlugin(),parent.getInventory(), getPosition().toInventoryPosition(), this) {
                    @Override
                    public void onSlotUpdate(InputChangeEvent inputChangeEvent) {
                        events
                                .stream()
                                .filter(InputEvent.class::isInstance)
                                .map(InputEvent.class::cast)
                                .forEach(event -> event.onInputChange(inputChangeEvent));
                    }
                }
        };

        parent.addEvent(new GUIEvent() {
            @Override
            public void onOpen(ContainerOpenEvent event) {
                slotWatchers[0].startListing();
            }

            @Override
            public void onClose(ContainerCloseEvent event) {
                slotWatchers[0].stopListening();

                if (getItemOnSlot() != null && GUInput.this.getItemOnSlot().getType() != Material.AIR) {
                    HashMap<Integer, ItemStack> leftover = event.getPlayer().getInventory().addItem(GUInput.this.getItemOnSlot());

                    if (!leftover.isEmpty()) {
                        event.getPlayer().getLocation().getWorld().dropItem(event.getPlayer().getLocation(), GUInput.this.getItemOnSlot());
                    }
                }
            }
        });
    }

    public void clear() {
        ((GUIContainer) getParent()).getInventory().setItem(getPosition().toInventoryPosition(), null);
        ((GUIContainer) getParent()).getInventory().setItem(getPosition().toInventoryPosition(), new ItemStack(Material.AIR));
    }

    @Override
    public GUIComponent getParent() {
        return parent;
    }

    @Override
    public void setParent(GUIComponent parent) {
        this.parent = (GUIContainer) parent;
        setup();
    }

    @Override
    public Vector2i getSize() {
        return size;
    }

    @Override
    public void setSize(Vector2i dimension) {
        this.size = dimension;
    }

    @Override
    public Vector2i getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector2i dimension) {
        this.position = dimension;
    }

    @Override
    public void draw() {
//        parent.getInventory().setItem(position.getX() + position.getY() * 9, input);
    }

    @Override
    public void dispose() {
        clear();
    }

    @Override
    public ItemStack getNative() {
        return parent.getInventory().getItem(getPosition().toInventoryPosition());
//        return input;
    }

    @Override
    public void addEvent(GUIEvent event) {
        events.add(event);
    }

    @Override
    public void removeEvent(GUIEvent event) {
        events.remove(event);
    }

    @Override
    public void addGlobalEvent(GUIEvent event) {
        parent.addGlobalEvent(event);
    }

    @Override
    public void removeGlobalEvent(GUIEvent event) {
        parent.removeGlobalEvent(event);
    }

    @Override
    public List<GUIEvent> getGlobalEvents() {
        return parent.getGlobalEvents();
    }

    @Override
    public List<GUIEvent> getEvents() {
        return events;
    }

    @Override
    public void setEvents(List<GUIEvent> events) {
        this.events = events;
    }

    @Override
    public void setGlobalEvents(List<GUIEvent> events) {
        parent.setGlobalEvents(events);
    }

    @Override
    public void lock() {

    }

    @Override
    public void unlock() {

    }

    @Override
    public boolean isLocked() {
        return false;
    }


    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ItemStack getItemOnSlot() {
        return parent.getInventory().getItem(getPosition().getX() + getPosition().getY() * 9);
    }

    public ItemStack getInput() {
        return getItemOnSlot();
    }
}
