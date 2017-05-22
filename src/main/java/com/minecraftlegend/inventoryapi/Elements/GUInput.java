package com.minecraftlegend.inventoryapi.Elements;


import com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerCloseEvent;
import com.minecraftlegend.inventoryapi.Events.InputChangeEvent;
import com.minecraftlegend.inventoryapi.Events.InputEvent;
import com.minecraftlegend.inventoryapi.GUIComponent;
import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUIElement;
import com.minecraftlegend.inventoryapi.GUIEvent;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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
    private ItemStack input = new ItemStack( Material.AIR, 0,(short) 0, (byte) 0);
    private List<GUIEvent> events = new ArrayList<>();


    private void setup(){
        addGlobalEvent( new GUIEvent() {
            @Override
            public void onClick( ComponentClickEvent event ) {
                if ( parent.getInventory() == event.getGui().getInventory()) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {

                            if(input != null && input.getType() != Material.AIR || parent.getInventory().getItem( position.toInventoryPosition() ) != null && parent.getInventory().getItem( position.toInventoryPosition() ).getType() != Material.AIR) {
                                if ( input != null && input.getType() == Material.AIR && parent.getInventory().getItem( position.toInventoryPosition() ) != null && parent.getInventory().getItem( position.toInventoryPosition() ).getType() != Material.AIR ) {

                                    GUInput.this.input = parent.getInventory().getItem( position.toInventoryPosition() );
                                    for ( GUIEvent guiEvent : events ) {
                                        if ( guiEvent instanceof InputEvent ) {
                                            ( (InputEvent) guiEvent ).onInputChange( new InputChangeEvent( InputChangeEvent.InputActionType.IN, GUInput.this, event.getPlayer() ) );
                                        }
                                    }
                                }
                                else if ( input != null && input.getType() != Material.AIR && parent.getInventory().getItem( position.toInventoryPosition() ) != null && parent.getInventory().getItem( position.toInventoryPosition() ).getType() != Material.AIR ) {

                                    GUInput.this.input = parent.getInventory().getItem( position.toInventoryPosition() );
                                    for ( GUIEvent guiEvent : events ) {
                                        if ( guiEvent instanceof InputEvent ) {
                                            ( (InputEvent) guiEvent ).onInputChange( new InputChangeEvent( InputChangeEvent.InputActionType.CHANGE, GUInput.this, event.getPlayer() ) );
                                        }
                                    }
                                }
                                else {
                                    GUInput.this.input = new ItemStack( Material.AIR, 0,(short) 0, (byte) 0);
                                    for ( GUIEvent guiEvent : events ) {
                                        if ( guiEvent instanceof InputEvent ) {
                                            ( (InputEvent) guiEvent ).onInputChange( new InputChangeEvent( InputChangeEvent.InputActionType.OUT, GUInput.this, event.getPlayer() ) );
                                        }
                                    }
                                }
                            }
                        }
                    }.runTaskLaterAsynchronously( parent.getPlugin(), 1L );
                }
            }
        });


        parent.addEvent( new GUIEvent() {
            @Override
            public void onClose( ContainerCloseEvent event ) {
                    if ( input != null && input.getType() != Material.AIR ) {
                        HashMap<Integer, ItemStack> leftover = event.getPlayer().getInventory().addItem( input );
                        if ( !leftover.isEmpty() ) {
                            event.getPlayer().getLocation().getWorld().dropItem( event.getPlayer().getLocation(), input );
                        }
                    }
            }
        } );
    }

    public void clear(){
        input = new ItemStack( Material.AIR, 0,(short) 0, (byte) 0);
        draw();
    }

    @Override
    public GUIComponent getParent() {
        return parent;
    }

    @Override
    public void setParent( GUIComponent parent ) {
        this.parent = (GUIContainer) parent;
        setup();
    }

    @Override
    public Vector2i getSize() {
        return size;
    }

    @Override
    public void setSize( Vector2i dimension ) {
        this.size = dimension;
    }

    @Override
    public Vector2i getPosition() {
        return position;
    }

    @Override
    public void setPosition( Vector2i dimension ) {
        this.position = dimension;
    }

    @Override
    public void draw() {
        parent.getInventory().setItem( position.getX() + position.getY() * 9, input );
    }

    @Override
    public void dispose() {
        if ( parent.getInventory().getItem( position.toInventoryPosition() ).equals( input ) )
            parent.getInventory().setItem( position.toInventoryPosition(),  new ItemStack( Material.AIR ) );    }

    @Override
    public ItemStack getNative() {
        return input;
    }

    @Override
    public void addEvent( GUIEvent event ) {
        events.add( event );
    }

    @Override
    public void removeEvent( GUIEvent event ) {
        events.remove( event );
    }

    @Override
    public void addGlobalEvent( GUIEvent event ) {
        parent.addGlobalEvent( event );
    }

    @Override
    public void removeGlobalEvent( GUIEvent event ) {
        parent.removeGlobalEvent( event );
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
        }
        catch ( CloneNotSupportedException e ) {
            e.printStackTrace();
        }
        return null;
    }
}
