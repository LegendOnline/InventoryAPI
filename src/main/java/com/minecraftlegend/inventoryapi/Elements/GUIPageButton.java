package com.minecraftlegend.inventoryapi.Elements;

import com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerCloseEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerOpenEvent;
import com.minecraftlegend.inventoryapi.GUIEvent;
import com.minecraftlegend.inventoryapi.Router.Navigation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * <h1>InventoryAPI</h1>
 * The Message enum for translation.
 *
 * @author Juyas, Drayke
 * @version 1.0
 * @since 13.09.2017
 */
public class GUIPageButton extends GUIButton {

    private String urlPath;

    public GUIPageButton( String title, String urlPath ) {
        super( title );
        this.urlPath = urlPath;
        link();
    }

    public GUIPageButton( ItemStack item, String urlPath ) {
        super( item );
        this.urlPath = urlPath;
        link();
    }

    public GUIPageButton( String title, Material icon, String urlPath ) {
        super( title, icon );
        this.urlPath = urlPath;
        link();
    }

    public GUIPageButton( String title, Material icon, byte type, String urlPath ) {
        super( title, icon, type );
        this.urlPath = urlPath;
        link();
    }

    private void link()
    {
        addEvent(new GUIEvent() {
            @Override
            public void onClick(ComponentClickEvent event) {
                Navigation.getInstance().push( (Player) event.getPlayer(), getUrlPath() );
            }
        });

    }

    public String getUrlPath() {
        return urlPath;
    }
}
