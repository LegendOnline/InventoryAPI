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
 * <li>
 * ListLayout:
 * Call the prepareMenu(Player player) method and add this List
 * in front of the elements used to build the ListLayout.
 * list.addAll(MenuRow);
 * </li>
 * <li>
 * ExactLayout:
 * Call the  buildMenu( Player player, ExactLayout layout, String firstRow )
 * method to align the menu items to the characters from the firstRow parameter.
 * </li>
 * </ul>
 * If the back button is not needed, simply let the backBtnText empty!
 *
 * @author Drayke
 * @version 1.0
 * @since 20.09.2017
 */
public final class MenuRow {

    private static final int ROW_LENGTH = 9;    //< The length of one minecraft inventory row

    private static final int SLOT_BACK = 0;     //< The slot for the back button
    private static final int SLOT_CLOSE = 8;    //< The slot for the close button

    private String closeBtnText = null;
    private String backBtnText = null;

    private boolean prepared = false;

    private ArrayList<GUIElement> row;

    /**
     * Instantiates a new Menu row. Close and back
     * button are set with default texts.
     */
    public MenuRow() {
        this(null,null);
    }

    /**
     * Instantiates a new Menu row.
     *
     * @param closeBtnText the close btn text
     * @param backBtnText  the back btn text
     */
    public MenuRow( String closeBtnText, String backBtnText ) {
        this.row = new ArrayList<>( ROW_LENGTH ); //Preload the List
        this.closeBtnText = closeBtnText;
        this.backBtnText = backBtnText;
        setDefaultButtons( null );
    }

    /**
     * Sets the title for the default close button of this instance
     *
     * @param closeBtnText the title
     */
    public void setCloseBtnText( String closeBtnText ) {
        this.closeBtnText = closeBtnText;
    }

    /**
     * Sets the title for the default back button of this instance
     *
     * @param backBtnText the title
     */
    public void setBackBtnText( String backBtnText ) {
        this.backBtnText = backBtnText;
    }

    /**
     * Sets the default buttons for the GUI.
     * Back -and CloseButton. The backbutton is just
     * set depending on the player
     *
     * @param player the player
     */
    public void setDefaultButtons( Player player ) {

        //Add 9 filler items
        for (int i = this.row.size(); i < ROW_LENGTH; i++) {
            this.row.add( getFillerItem() );
        }

        //Set default items
        if ( player != null && Navigation.getInstance().canPop( player ) )
            set( SLOT_BACK, getBackButton( backBtnText==null?"§c<---":backBtnText  )  );

        set( SLOT_CLOSE, getCloseButton( closeBtnText==null?"§cX":closeBtnText ) );
    }

    /**
     * Builds the menu with the default close and back button.
     * If the menu is cleared, the other menu elements needs
     * to be set again!
     * After this method call, all items are in place.
     *
     * @param player the player
     */
    public void prepareMenu( Player player ) {
        //Back button shall always be the first button
        //This button will be added depending on the history of the player
        setDefaultButtons( player );

        //Always just 9 Items!
        if ( this.row.size() > ROW_LENGTH ) {
            List<GUIElement> tmp = this.row.subList( 0, ROW_LENGTH );
            this.row.clear();
            this.row.addAll( tmp );
        }

        prepared = true;
    }

    /**
     * Builds the menu with the default close and back button.
     * If the menu is cleared, the other menu elements needs
     * to be set again! This method sets all characters of
     * the firstRow parameter to the corresponding menu item.
     * That's why this string needs to have 9 different characters
     * for a working menu!
     *
     * @param player   the player
     * @param layout   the exact layout
     * @param firstRow the first row of the exact layout
     */
    public void buildMenu( Player player, ExactLayout layout, String firstRow ) {

        if(!prepared) prepareMenu( player );

        //The first row of the layout is important!
        if ( firstRow.length() != ROW_LENGTH ) return;
        int counter = 0;
        for ( char c : firstRow.toCharArray() ) {
            layout.set( c, this.row.get( counter ) );
            counter++;
        }
    }

    /**
     * Get's the item of the row.
     *
     * @return the gui element row
     */
    public ArrayList<GUIElement> getRow()
    {
        return this.row;
    }

    /**
     * Removes a gui element.
     *
     * @param index the index
     *
     * @return the removed gui element
     */
    public GUIElement remove( int index ) {
        GUIElement element = this.row.get( index );
        set( index,getFillerItem() );
        return element;
    }

    /**
     * Sets a gui element at a given index.
     * The index can't be bigger then the row length.
     *
     * @param index   the index
     * @param element the element
     *
     * @return the gui element
     */
    public GUIElement set( int index, GUIElement element ) {
        if ( index < ROW_LENGTH )
            return this.row.set( index, element );

         return element;
    }

    /**
     * Clears the row and sets the default buttons.
     */
    public void clear() {
        clear( null );
    }

    /**
     * Clears the row and sets the default buttons.
     * @param player the player for the default navigation buttons
     */
    public void clear(Player player) {
        this.row.clear();
        this.prepared = false;
        setDefaultButtons( player );
    }

    /*************************************************************
     *
     *                 Helper methods for items
     *
     *************************************************************/

    private GUIElement getCloseButton( String closeText ) {
        GUIButton button = new GUIButton( new ItemBuilder().head( "MHF_X", closeText ).build() );
        button.addEvent( new GUIEvent() {
            @Override
            public void onClick( ComponentClickEvent event ) {
                event.getPlayer().closeInventory();
            }
        } );
        return button;
    }

    private GUIElement getBackButton( String backText ) {
        GUIButton button = new GUIButton( new ItemBuilder().head( "MHF_ArrowLeft", backText ).build() );
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

    public boolean isPrepared() {
        return prepared;
    }

}
