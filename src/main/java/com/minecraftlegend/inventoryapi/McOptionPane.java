package com.minecraftlegend.inventoryapi;


import com.minecraftlegend.inventoryapi.Elements.GUIButton;
import com.minecraftlegend.inventoryapi.Elements.GUILabel;
import com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerCloseEvent;
import com.minecraftlegend.inventoryapi.Events.GUIEventCallback;
import com.minecraftlegend.inventoryapi.Layouts.ModelLayout;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class McOptionPane {

    /**
     * Opens a confirm / deny dialog
     * @param player the player to whom the dialog should be visible
     * @param message the confirm Question that should be asked (Inventory Title)
     * @param onConfirm callback event that is triggered, if the player clicks the confirm button
     * @param onDeny callback event that is triggered, if the player clicks the deny button
     */
    public static void showConfirmDialog( JavaPlugin plugin, Player player, String message, GUIEventCallback<ComponentClickEvent> onConfirm, GUIEventCallback<ComponentClickEvent> onDeny ) {
        showConfirmDialog(plugin, player, message, "§aOk", "§cAbbrechen", onConfirm, onDeny );
    }


    /**
     * Opens a confirm / deny dialog
     * @param player the player to whom the dialog should be visible
     * @param message the confirm Question that should be asked (Inventory Title)
     * @param acceptLabel the name, the confirm button should be called
     * @param denyLabel the name, the deny button should be called
     * @param onConfirm callback event that is triggered, if the player clicks the confirm button
     * @param onDeny callback event that is triggered, if the player clicks the deny button
     */
    public static void showConfirmDialog( JavaPlugin plugin, Player player, String message, String acceptLabel, String denyLabel, GUIEventCallback<ComponentClickEvent> onConfirm, GUIEventCallback<ComponentClickEvent> onDeny ) {
        Inventory prev = player.getOpenInventory().getTopInventory();
        ModelLayout layout = new ModelLayout( "1:1|3=p,*=p" );
        layout.set( 'p', new GUILabel( " ", Material.STAINED_GLASS_PANE, (byte) 7 ) );
        McGui gui = new McGui(plugin, message, layout );
        GUIButton accept = new GUIButton( acceptLabel, Material.WOOL, (byte) 5 );
        accept.setPosition( new Vector2i( 2, 1 ) );
        GUIButton deny = new GUIButton( denyLabel, Material.WOOL, (byte) 14 );
        deny.setPosition( new Vector2i( 6, 1 ) );
        accept.addEvent( new GUIEvent() {
            @Override
            public void onClick( ComponentClickEvent event ) {
                if ( onConfirm != null ) onConfirm.call( event );
            }
        } );
        deny.addEvent( new GUIEvent() {
            @Override
            public void onClick( ComponentClickEvent event ) {
                if ( onDeny != null ) onDeny.call( event );
            }
        } );
        gui.add( accept );
        gui.add( deny );

        if ( prev.getType() != InventoryType.CRAFTING ) {
            gui.addEvent( new GUIEvent() {
                @Override
                public void onClose( ContainerCloseEvent event ) {
                    Bukkit.getScheduler().runTaskLater( plugin, new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 1 );
                }
            } );
        }
        gui.draw( player );
    }


}
