package com.minecraftlegend.inventoryapi.Layouts;


import com.google.common.collect.Queues;
import com.minecraftlegend.inventoryapi.Elements.GUIButton;
import com.minecraftlegend.inventoryapi.Elements.GUILabel;
import com.minecraftlegend.inventoryapi.Elements.GUISubContainer;
import com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUIElement;
import com.minecraftlegend.inventoryapi.GUIEvent;
import com.minecraftlegend.inventoryapi.GUILayout;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class ListLayout implements GUILayout {

    private List<GUIElement> elements;
    private int rows, invNum;
    private Vector2i size;
    private JavaPlugin plugin;

    /**
     * The list layout will convert a list of {@link GUIElement}s into multiple {@link GUIContainer}s
     * these containers are child components of the first 'root' container.
     * Buttons to click trough the different containers are automatically generated, such as the amount
     * of subContainers that are needed to generate enough space for the list of elements
     *
     * @param elements the list that should be displayed in the inventory
     * @param rows the amount of rows each container should have
     * <h2>Note:</h2>
     * As lower the amount of rows is, as higher is the amount of containers you have to click trough
     */
    public ListLayout( JavaPlugin plugin, List<GUIElement> elements, int rows ) {
        this.plugin = plugin;
        this.elements = elements;
        this.rows = rows;
        this.size = new Vector2i( 9, rows + 1 );
        this.invNum = (int) ( Math.ceil( (double) elements.size() / (double) ( rows * 9 ) ) - 1 );
    }

    @Override
    public void apply( GUIContainer container ) {
        container.setSize( size );

        ArrayList<GUIContainer> subs = new ArrayList<>();

        for ( int i = invNum; i > 0; i-- ) {
            if ( i == invNum ) {
                if ( invNum > 1 ) {
                    subs.add( new GUISubContainer( plugin,null, container.getInventory().getTitle() + "| Seite: " + i, true, new PlainLayout( size, InventoryType.CHEST ) ) );
                }
                else {
                    subs.add( new GUISubContainer( plugin, container, null, container.getInventory().getTitle() + "| Seite: " + i, true, new PlainLayout( size, InventoryType.CHEST ) ) );
                }
            }
            else if ( i == 1 ) {
                subs.add( new GUISubContainer( plugin, container, (GUISubContainer) subs.get( subs.size() - 1 ), container.getInventory().getTitle() + "| Seite: 1", true, new PlainLayout( size, InventoryType.CHEST ) ) );
            }
            else {
                subs.add( new GUISubContainer( plugin, (GUISubContainer) subs.get( invNum - i - 1 ), container.getInventory().getTitle() + "| Seite: " + i, true, new PlainLayout( size, InventoryType.CHEST ) ) );
            }
        }


        subs.add( container );
        Collections.reverse( subs );

        Queue<GUIElement> elementQueue = Queues.newConcurrentLinkedQueue();
        elementQueue.addAll( elements );

        invQuery:
        for ( GUIContainer inv : subs ) {
            if ( inv.getInventory().firstEmpty() == -1 ) continue invQuery;


            for ( int y = 0; y < rows; y++ ) {
                for ( int x = 0; x < 9; x++ ) {
                    GUIElement e = elementQueue.poll();
                    if ( e == null ) break;
                    e.setPosition( new Vector2i( x, y ) );
                    inv.add( e );
                }
            }

            GUILabel label = new GUILabel( " ", Material.STAINED_GLASS_PANE, (byte) 7 );
            for ( int i = 0; i < 9; i++ ) {
                GUILabel clone = (GUILabel) label.clone();
                clone.setPosition( new Vector2i( i, rows ) );
                inv.add( clone );
            }

            if ( inv instanceof GUISubContainer ) {
                if ( ( (GUISubContainer) inv ).getParent() != null ) {
                    ( (GUISubContainer) inv ).getExitButton().dispose();
                    ( (GUISubContainer) inv ).getExitButton().setPosition( new Vector2i( 2, rows ) );
                    ItemStack exit = new ItemStack( Material.ARROW );
                    ItemMeta im = exit.getItemMeta();
                    im.setDisplayName( "§4«" );
                    exit.setItemMeta( im );
                    ( (GUISubContainer) inv ).setExitIcon( exit );
                }

                if ( ( (GUISubContainer) inv ).getChild() != null ) {
                    ( (GUISubContainer) inv ).getNextButton().dispose();
                    ( (GUISubContainer) inv ).getNextButton().setPosition( new Vector2i( 6, rows ) );
                    ItemStack next = new ItemStack( Material.ARROW );
                    ItemMeta im2 = next.getItemMeta();
                    im2.setDisplayName( "§a»" );
                    next.setItemMeta( im2 );
                    ( (GUISubContainer) inv ).setNextIcon( next );
                }
            }

        }
        if ( invNum > 0 ) {
            GUIButton first = new GUIButton( "§a»", Material.ARROW );
            first.setPosition( new Vector2i( 6, rows ) );
            first.addEvent( new GUIEvent() {
                @Override
                public void onClick( ComponentClickEvent event ) {
                    subs.get( 1 ).draw( (Player) event.getPlayer() );
                }
            } );
            container.add( first );
        }
    }

    @Override
    public Vector2i getSize() {
        return size;
    }

    @Override
    public int getInventorySize() {
        return ( rows + 1 ) * 9;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.CHEST;
    }
}
