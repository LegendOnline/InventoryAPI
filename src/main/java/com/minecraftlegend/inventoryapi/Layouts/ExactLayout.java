package com.minecraftlegend.inventoryapi.Layouts;


import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUIElement;
import com.minecraftlegend.inventoryapi.GUILayout;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.apache.commons.lang3.Validate;
import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class ExactLayout implements GUILayout {


    protected String[] rawLayout;
    protected HashMap<Character, GUIElement> slots = new HashMap<>();
    protected int totalSize = 0;
    protected Vector2i dim;

    /**
     * This layout is mostly used for small inventory views or ones, that cant be defined by other layouts
     * Every String in the list of rows represents one row in the inventory. Therefore one String can not have more than 9 chars
     * The size of the inventory is determined by the amount of Strings you specify.
     * Every char in one String represents a slot in the inventory
     * <h2>Example:</h2>
     * "xxxyyyxxx" -> x will be air and y diamond
     * the inventory would end up with one row and the 3 middle slots are filled with diamonds
     * to bind {@link GUIElement}'s to chars look at {@link ExactLayout#set(char, GUIElement)}
     *
     * @param rows the definition Strings of this layout
     */
    public ExactLayout( String... rows ) {
        this.rawLayout = rows;
        for ( int i = 0; i < rawLayout.length; i++ ) {
            String s = rawLayout[i];
            Validate.isTrue( s.length() == 9, "An inventory row has to have 9 items! row: " + i + " has " + s.length() + " chars!" );
        }
        totalSize = rawLayout.length * 9;
        dim = new Vector2i( 9, rawLayout.length );
    }

    /**
     * Attaches {@link GUIElement}s to chars
     * If one char is used multiple times in the definition String the {@link GUIElement} will be cloned
     * @param key the char to identify / bind the element
     * @param element to bind
     */
    public void set( char key, GUIElement element ) {
        slots.put( key, element );
    }


    public void apply( GUIContainer container ) {


        for ( int i = 0; i < rawLayout.length; i++ ) {
            for ( int j = 0; j < rawLayout[i].length(); j++ ) {
                Validate.isTrue( isSet( rawLayout[i].charAt( j ) ), "key: " + rawLayout[i].charAt( j ) + " is not referenced!" );
                GUIElement orig = slots.get( rawLayout[i].charAt( j ) );
                //original element has already be assigned | there should be some bulk change element in the future
                //to receive all affected slots / items
                if(orig.getParent() != null && orig.getPosition() != null){
                    GUIElement clone = (GUIElement) orig.clone();
                    clone.setPosition( new Vector2i( j, i ) );
                    container.add( clone );
                    // clone.draw();
                }else{
                    orig.setPosition(  new Vector2i( j, i )  );
                    container.add( orig );
                }
            }
        }
    }


    protected boolean isSet( char key ) {
        return slots.containsKey( key );
    }


    public Vector2i getSize() {
        return dim;
    }


    public int getInventorySize() {
        return totalSize;
    }


    public InventoryType getInventoryType() {
        return InventoryType.CHEST;
    }
}
