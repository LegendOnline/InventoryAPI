package com.minecraftlegend.inventoryapi.Elements;

import com.minecraftlegend.inventoryapi.GUIComponent;
import com.minecraftlegend.inventoryapi.GUIElement;
import com.minecraftlegend.inventoryapi.GUIEvent;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 13.06.2017.
 */
public class GUIVoid implements GUIElement{

    private Vector2i position, size;
    private GUIComponent parent;

    @Override
    public GUIComponent getParent() {
        return parent;
    }

    @Override
    public void setParent( GUIComponent parent ) {
        this.parent = parent;
    }

    @Override
    public Vector2i getSize() {
        return size;
    }

    @Override
    public void setSize( Vector2i dimension ) {
        size = dimension;
    }

    @Override
    public Vector2i getPosition() {
        return position;
    }

    @Override
    public void setPosition( Vector2i dimension ) {
        position = dimension;
    }

    @Override
    public void draw() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public ItemStack getNative() {
        return new ItemStack( Material.AIR );
    }

    @Override
    public void addEvent( GUIEvent event ) {

    }

    @Override
    public void removeEvent( GUIEvent event ) {

    }

    @Override
    public void addGlobalEvent( GUIEvent event ) {

    }

    @Override
    public void removeGlobalEvent( GUIEvent event ) {

    }

    @Override
    public List<GUIEvent> getGlobalEvents() {
        return new ArrayList<>(  );
    }

    @Override
    public List<GUIEvent> getEvents() {
        return new ArrayList<>(  );
    }

    @Override
    public void setEvents( List<GUIEvent> events ) {

    }

    @Override
    public void setGlobalEvents( List<GUIEvent> events ) {

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
            return new GUIVoid();
        }
    }
}
