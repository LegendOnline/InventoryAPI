package com.minecraftlegend.inventoryapi.Elements;

import com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import com.minecraftlegend.inventoryapi.GUIEvent;
import com.minecraftlegend.inventoryapi.Router.Navigation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * <h1>InventoryAPI</h1>
 * A GUIPageButton for navigation between different pages
 * with the help of URLs. Pages needs to be registered within
 * the Router instance.
 *
 * @author Drayke
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
