package com.minecraftlegend.inventoryapi.Layouts;


import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUIElement;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class ModelLayout extends ExactLayout {

    private String layout;

    /**
     * Syntax:
     * x:y=i,x:y-x=i,x:y|y=i,x:y#x:y=i,*=i
     * <p>
     * x = x position of the target slot in the inventory
     * y = y position of the target slot in the inventory
     * i = GUIElement/char referenced with GUIElement
     * - = fill slots horizontally
     * | = fill slots vertically
     * # = fill a square where pos1 is top-left and pos2 is bottom-right
     * , = separate multiple filling operations
     * * = fill all blank slots
     * <p>
     * <h2>Examples:</h2>
     * <p>
     * 4:5=d   fill slot at x=4 & y=5 with GUIElement associated with char 'd'
     * 7:0|2=g fills slots vertically from x=7 & y=0 to x=7 & y=2 with GUIElement associated with char 'g'
     * <p>
     * <h2>Complete Example:</h2>
     * <p>
     * 5:3=g,1:1-9=d,1:1|3=e,7:2#9:3=r,*=i
     * <p>
     *  <h2>Note:</h2>
     *  -The Coordinate system starts by 1:1 which is associated with the *top-left* corner of the inventory!!
     *  -The height of the inventory is determined by the biggest y coordinate in your operations string
     *  -To bind {@link GUIElement}'s to chars look at {@link ModelLayout#set(char, GUIElement)}
     * <p>
     *
     * @param layout the definition String of this layout
     */
    public ModelLayout( String layout ) {
        this.layout = layout;
        totalSize = getMaxY() * 9;
        dim = new Vector2i( 9, getMaxY() );
    }

    @Override
    public void apply( GUIContainer container ) {
        String[] ops = layout.split( "," );

        for ( String op : ops ) {
            if ( op.matches( "\\d:\\d=." ) ) {
                Vector2i coords = parseCoordinates( op.split( "=" )[0] );
                char key = op.split( "=" )[1].charAt( 0 );
                Validate.isTrue( isSet( key ), "key: " + key + " is not referenced!" );
                GUIElement orig = slots.get( key );
                //original element has already be assigned | there should be some bulk change element in the future
                //to receive all affected slots / items
                if(orig.getParent() != null && orig.getPosition() != null){
                    GUIElement clone = copy( orig );
                    clone.setPosition( coords );
                    container.add( clone );
                }else{
                    orig.setPosition( coords );
                    container.add( orig );
                }

            }
            else if ( op.matches( "\\d:\\d-\\d=." ) ) {
                String[] components = op.split( "=" );
                Vector2i start = parseCoordinates( components[0].split( "-" )[0] );
                Vector2i end = new Vector2i( Integer.parseInt( components[0].split( "-" )[1] ), start.getY() );
                char key = components[1].charAt( 0 );
                Validate.isTrue( isSet( key ), "key: " + key + " is not referenced!" );
                GUIElement element = slots.get( key );
                for ( int x = Math.min( start.getX(), end.getX() ); x < Math.max( start.getX(), end.getX() ); x++ ) {
                    Vector2i pos = start.clone();
                    pos.setX( x );

                    //original element has already be assigned | there should be some bulk change element in the future
                    //to receive all affected slots / items
                    if(element.getParent() != null && element.getPosition() != null){
                        GUIElement clone = copy( element );
                        clone.setPosition( pos );
                        container.add( clone );
                        // clone.draw();
                    }else{
                        element.setPosition( pos );
                        container.add( element );
                    }
                   // clone.draw();
                }
            }
            else if ( op.matches( "\\d:\\d\\|\\d=." ) ) {
                String[] components = op.split( "=" );
                Vector2i start = parseCoordinates( components[0].split( "\\|" )[0] );
                Vector2i end = new Vector2i( start.getX(), Integer.parseInt( components[0].split( "\\|" )[1] ) );
                char key = components[1].charAt( 0 );
                Validate.isTrue( isSet( key ), "key: " + key + " is not referenced!" );
                GUIElement element = slots.get( key );
                for ( int y = Math.min( start.getY(), end.getY() ); y < Math.max( start.getY(), end.getY() ); y++ ) {
                    Vector2i pos = start.clone();
                    pos.setY( y );

                    //original element has already be assigned | there should be some bulk change element in the future
                    //to receive all affected slots / items
                    if(element.getParent() != null && element.getPosition() != null){
                        GUIElement clone = copy( element );
                        clone.setPosition( pos );
                        container.add( clone );
                        // clone.draw();
                    }else{
                        element.setPosition( pos );
                        container.add( element );
                    }
                  //  clone.draw();
                }
            }
            else if ( op.matches( "\\d:\\d#\\d:\\d=." ) ) {
                String[] components = op.split( "=" );
                Vector2i start = parseCoordinates( components[0].split( "#" )[0] );
                Vector2i end = parseCoordinates( components[0].split( "#" )[1] );
                char key = components[1].charAt( 0 );
                Validate.isTrue( isSet( key ), "key: " + key + " is not referenced!" );
                GUIElement element = slots.get( key );

                for ( int y = start.getY(); y <= end.getY(); y++ ) {
                    for ( int x = start.getX(); x <= end.getX(); x++ ) {
                        Vector2i pos = new Vector2i( x, y );
                        //original element has already be assigned | there should be some bulk change element in the future
                        //to receive all affected slots / items
                        if(element.getParent() != null && element.getPosition() != null){
                            GUIElement clone = copy( element );
                            clone.setPosition( pos );
                            container.add( clone );
                            // clone.draw();
                        }else{
                            element.setPosition( pos );
                            container.add( element );
                        }
                      //  clone.draw();
                    }
                }


            }
            else if ( op.matches( "\\*=." ) ) {
                char key = op.split( "=" )[1].charAt( 0 );
                Validate.isTrue( isSet( key ), "key: " + key + " is not referenced!" );
                GUIElement element = slots.get( key );

                Inventory inv = container.getInventory();

                for ( int y = 0; y < totalSize / 9; y++ ) {
                    for ( int x = 0; x < 9; x++ ) {
                        if(container.getElement( new Vector2i( x,y ) ) == null) {
                            if ( inv.getItem( x + y * 9 ) == null || inv.getItem( x + y * 9 ).getType() == Material.AIR ) {
                                //original element has already be assigned | there should be some bulk change element in the future
                                //to receive all affected slots / items
                                if(element.getParent() != null && element.getPosition() != null){
                                    GUIElement clone = copy( element );
                                    clone.setPosition( new Vector2i( x, y ) );
                                    container.add( clone );
                                    // clone.draw();
                                }else{
                                    element.setPosition( new Vector2i( x, y )  );
                                    container.add( element );
                                }
                             //   clone.draw();
                            }
                        }
                    }
                }


            }else throw new IllegalArgumentException( "Layout operation '" + op + "' is not defined!" );
        }
    }

    private Vector2i parseCoordinates( String str ) {
        Validate.isTrue( str.matches( "\\d:\\d" ), "coordinates '" + str + "' doesnt match pattern x:y" );
        String[] coords = str.split( ":" );
        int x = Integer.parseInt( coords[0] ) - 1;
        int y = Integer.parseInt( coords[1] ) - 1;

        Validate.isTrue( (x+1 != 0 && y+1 != 0), "coordinates [" + x + "|" + y + "] have to be greater than 0" );
        return new Vector2i( x, y );
    }

    private int getMaxY() {
        String[] ops = layout.split( "," );
        int max = 0;

        for ( String op : ops ) {
            if ( op.matches( "\\d:\\d=." ) ) {
                Vector2i v = parseCoordinates( op.split( "=" )[0] );
                if ( v.getY() + 1 > max ) max = v.getY() + 1;
            }
            else if ( op.matches( "\\d:\\d-\\d=." ) ) {
                Vector2i v = parseCoordinates( op.split( "-" )[0] );
                if ( v.getY() + 1 > max ) max = v.getY() + 1;
            }
            else if ( op.matches( "\\d:\\d\\|\\d=." ) ) {
                int y = Integer.parseInt( op.split( "\\|" )[1].split( "=" )[0] );
                if ( y > max ) max = y;
            }
            else if ( op.matches( "\\d:\\d#\\d:\\d=." ) ) {
                Vector2i v = parseCoordinates( op.split( "#" )[0] );
                Vector2i v1 = parseCoordinates( op.split( "#" )[1].split( "=" )[0] );
                Vector2i maxV = v.max( v1 );
                if ( maxV.getY() + 1 > max ) max = maxV.getY() + 1;
            }
            else if ( op.matches( "\\*=." ) ) {
                continue;
            }
            else throw new IllegalArgumentException( "Layout operation '" + op + "' is not defined!" );
        }
        return max;
    }

    //Java has a really weird behavior that cloned objects are not completely deep-copied... this fucks up events
    private GUIElement copy(GUIElement object) {
        GUIElement clone = (GUIElement) object.clone();
        ArrayList events = new ArrayList(  );
        events.addAll( object.getEvents() );
        clone.setEvents( events );
        return clone;
    }
}
