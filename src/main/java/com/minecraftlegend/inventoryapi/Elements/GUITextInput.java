package com.minecraftlegend.inventoryapi.Elements;


import com.minecraftlegend.inventoryapi.Events.AnvilEvent;
import com.minecraftlegend.inventoryapi.Events.AnvilResultEvent;
import com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerCloseEvent;
import com.minecraftlegend.inventoryapi.GUIComponent;
import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUIElement;
import com.minecraftlegend.inventoryapi.GUIEvent;
import com.minecraftlegend.inventoryapi.utils.AnvilSlot;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/

public class GUITextInput implements GUIElement {

    private String text;
    private ItemStack item;
    private Vector2i position, size;
    private GUIContainer parent;
    private GUIAnvil anvil;
    private String title;
    private List<String> description;
    private List<GUIEvent> events = new ArrayList<>();
    private Material icon = Material.STAINED_GLASS_PANE;
    private int amount;

    public GUITextInput( String title ) {
        this.title = title;
        item = new ItemStack( icon, 1 );
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName( title );
        item.setItemMeta( itemMeta );
    }

    public GUITextInput( String title, Material icon ) {
        this.title = title;
        item = new ItemStack( icon, 1 );
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName( title );
        item.setItemMeta( itemMeta );
    }

    public GUITextInput( String title, Material icon, byte type ) {
        this.title = title;
        item = new ItemStack( icon, 1, type );
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName( title );
        item.setItemMeta( itemMeta );
    }


    public void postInit() {
        anvil = new GUIAnvil(parent.getPlugin());

        addEvent( new GUIEvent() {
            @Override
            public void onClick( ComponentClickEvent event ) {
                anvil.setSlot( new GUILabel( "Enter Text here", Material.PAPER ), AnvilSlot.INPUT_LEFT );
                anvil.addEvent( new AnvilEvent() {
                    @Override
                    public void onResultClick( AnvilResultEvent event ) {
                        event.getAnvil().clearSlot( AnvilSlot.INPUT_LEFT );
                        event.getAnvil().clearSlot( AnvilSlot.INPUT_RIGHT );
                        event.getAnvil().clearSlot( AnvilSlot.OUTPUT);
                        anvil.dispose( event.getPlayer() );
                    }

                    @Override
                    public void onClose( ContainerCloseEvent event ) {
                        if(event.getContainer() instanceof GUIAnvil){
                            GUIAnvil anvil = (GUIAnvil) event.getContainer();
                            anvil.clearSlot( AnvilSlot.INPUT_LEFT );
                            anvil.clearSlot( AnvilSlot.INPUT_RIGHT );
                            anvil.clearSlot( AnvilSlot.OUTPUT);
                        }

                    }
                } );
                anvil.lock();
                anvil.draw( (Player) event.getPlayer() );
            }
        } );
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

    @Override
    public GUIComponent getParent() {
        return parent;
    }

    @Override
    public void setParent( GUIComponent parent ) {
        this.parent = (GUIContainer) parent;
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
        parent.getInventory().setItem( position.getX() + position.getY() * 9, item );
    }

    @Override
    public void dispose() {
        parent.getInventory().setItem( position.getX() + position.getY() * 9, new ItemStack( Material.AIR ) );
    }

    @Override
    public ItemStack getNative() {
        return item;
    }

    @Override
    public void addEvent( GUIEvent event ) {
        if(event instanceof AnvilEvent){
            anvil.addEvent( event );
        }else{
            events.add( event );
        }
    }

    @Override
    public void removeEvent( GUIEvent event ) {
        events.remove( event );
        anvil.removeEvent( event );
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
    public void setEvents( List<GUIEvent> events ) {
        this.events = events;
    }

    @Override
    public void setGlobalEvents( List<GUIEvent> events ) {
        parent.setGlobalEvents( events );
    }

    @Override
    public void lock() {

    }

    @Override
    public void unlock() {

    }

    @Override
    public boolean isLocked() {
        return true;
    }

    public String getText() {
        return text;
    }

    public void setText( String text ) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName( title );
        item.setItemMeta( itemMeta );
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription( List<String> description ) {
        this.description = description;
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore( description );
        item.setItemMeta( itemMeta );
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon( Material icon ) {
        this.icon = icon;
        item.setType( icon );
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount( int amount ) {
        this.amount = amount;
        item.setAmount( amount );
    }
}
