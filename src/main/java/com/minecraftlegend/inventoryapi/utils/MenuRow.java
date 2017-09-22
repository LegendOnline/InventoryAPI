package com.minecraftlegend.inventoryapi.utils;

import com.minecraftlegend.inventoryapi.Elements.GUIButton;
import com.minecraftlegend.inventoryapi.Elements.GUILabel;
import com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import com.minecraftlegend.inventoryapi.GUIElement;
import com.minecraftlegend.inventoryapi.GUIEvent;
import com.minecraftlegend.inventoryapi.ItemBuilder;
import com.minecraftlegend.inventoryapi.Layouts.ExactLayout;
import com.minecraftlegend.inventoryapi.Router.Navigation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>MenuRow</h1>
 * A row with 9 GUIElements suited for some layouts.
 * <ul>
 *     <li>
 *         ListLayout:
 *         Call the build(Player player) method and add this instance
 *         in Front of the elements used to build the ListLayout.
 *     </li>
 *     <li>
 *         ExactLayout:
 *         Call the  buildMenu( Player player, ExactLayout layout, String firstRow )
 *         method to align the menu items to the characters from the firstRow parameter.
 *     </li>
 * </ul>
 * If the back button is not needed, simply let the backBtnText empty!
 * @author Drayke
 * @version 1.0
 * @since 20.09.2017
 */
public final class MenuRow extends ArrayList<GUIElement> {

    private static final int ROW_LENGTH = 9;

    private String closeBtnText = "§cClose";

    private String backBtnText = "§c<---";

    /**
     * Instantiates a new Menu row. Close and back
     * button are set with default texts.
     */
    public MenuRow() {
        super( ROW_LENGTH );
    }

    /**
     * Instantiates a new Menu row.
     *
     * @param closeBtnText the close btn text
     * @param backBtnText  the back btn text
     */
    public MenuRow( String closeBtnText, String backBtnText ) {
        this();
        this.closeBtnText = closeBtnText;
        this.backBtnText = backBtnText;
    }

    /**
     * Instantiates a new Menu row. Without a back button.
     *
     * @param closeBtnText the close btn text
     */
    public MenuRow( String closeBtnText ) {
        this( closeBtnText,"");
    }

    /**
     * Builds the menu with the default close and back button.
     * If the menu is cleared, the other menu elements needs
     * to be set again!
     *
     * @param player the player
     */
    public void buildMenu( Player player ) {
        //Back button shall always be the first button
        //This button will be added depending on the history of the player
        if ( Navigation.getInstance().canPop( player ) && !this.backBtnText.isEmpty()) set( 0, getBackButton( this.backBtnText ) );
        else set( 0, getFillerItem() );

        //Add filler items
        int counter = 1;
        while ( counter < ROW_LENGTH - 1 ) {
            if ( get( counter ) == null ) set( counter, getFillerItem() );
            counter++;
        }

        //Close button shall always be the last button
        set( ROW_LENGTH - 1, getCloseButton( this.closeBtnText ) );

        //Always just 9 Items!
        if(size()>ROW_LENGTH)
        {
            List<GUIElement> tmp = subList( 0, ROW_LENGTH );
            clear();
            addAll( tmp );
        }
    }

    /**
     * Builds the menu with the default close and back button.
     * If the menu is cleared, the other menu elements needs
     * to be set again! This method sets all characters of
     * the firstRow parameter to the corresponding menu item.
     * That's why this string needs to have 9 different characters
     * for a working menu!
     *
     * @param player the player
     * @param layout the exact layout
     * @param firstRow the first row of the exact layout
     */
    public void buildMenu( Player player, ExactLayout layout, String firstRow ) {
        buildMenu( player );
        //The first row of the layout is important!
        if(firstRow.length()!=9) return;
        int counter = 0;
        for(char c : firstRow.toCharArray())
        {
            layout.set( c, get( counter ) );
            counter++;
        }
    }

    @Override
    public GUIElement set( int index, GUIElement element ) {
        if ( index < ROW_LENGTH ) return super.set( index, element );
        else return element;
    }

    /**
     * Sets close button text.
     *
     * @param closeBtnText the close btn text
     */
    public void setCloseBtnText( String closeBtnText ) {
        this.closeBtnText = closeBtnText;
    }

    /**
     * Sets back button text.
     *
     * @param backBtnText the back btn text
     */
    public void setBackBtnText( String backBtnText ) {
        this.backBtnText = backBtnText;
    }

    private GUIElement getCloseButton( String closeText ) {
        GUIButton button = new GUIButton( new ItemBuilder().head( "NHF_X", closeText ).build() );
        button.addEvent( new GUIEvent() {
            @Override
            public void onClick( ComponentClickEvent event ) {
                event.getPlayer().closeInventory();
            }
        } );
        return button;
    }

    private GUIElement getBackButton( String backText ) {
        GUIButton button = new GUIButton( new ItemBuilder().head( "NHF_ArrowLeft", backText ).build() );
        button.addEvent( new GUIEvent() {
            @Override
            public void onClick( ComponentClickEvent event ) {
                if ( event.getPlayer() instanceof Player ) Navigation.getInstance().pop( (Player) event.getPlayer() );
            }
        } );
        return button;
    }

    private GUIElement getFillerItem() {
        return new GUILabel( " ", Material.STAINED_GLASS_PANE, (byte) 7 );
    }
}
