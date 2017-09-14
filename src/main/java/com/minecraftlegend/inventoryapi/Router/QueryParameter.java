package com.minecraftlegend.inventoryapi.Router;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <h1>QueryParameter</h1>
 * <p>
 * An array list with values from the query.
 * Query: ?val=1&2&3
 * QueryParameter = {"1","2","3"};
 * </p>
 *
 * @author Drayke
 * @version 1.0
 * @since 31.08.2017.
 */
public final class QueryParameter extends ArrayList< String > {

    /**
     * Instantiates a new Query parameter.
     */
    public QueryParameter() {
    }

    /**
     * Instantiates a new Query parameter.
     *
     * @param c the collection
     */
    public QueryParameter( Collection< ? extends String > c ) {
        super( c );
    }

    /**
     * Is value from type integer.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean isInt( int index ) {
        String val = get( index );
        try {
            Integer.parseInt( val );
        } catch ( NumberFormatException e ) {
            return false;
        }
        return true;
    }

    /**
     * Gets integer value.
     *
     * @param index the index
     * @return the int
     */
    public int getInt( int index ) {
        return Integer.parseInt( get( index ) );
    }

    /**
     * Is value from type double.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean isDouble( int index ) {
        String val = get( index );
        try {
            Double.parseDouble( val );
        } catch ( NumberFormatException e ) {
            return false;
        }
        return true;
    }

    /**
     * Gets double value.
     *
     * @param index the index
     * @return the double
     */
    public double getDouble( int index ) {
        return Double.parseDouble( get( index ) );
    }

    /**
     * Is value from type boolean.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean isBool( int index ) {
        String val = get( index );
        try {
            Boolean.parseBoolean( val );
        } catch ( NumberFormatException e ) {
            return false;
        }
        return true;
    }

    /**
     * Gets boolean value.
     *
     * @param index the index
     * @return the bool
     */
    public boolean getBool( int index ) {
        return Boolean.parseBoolean( get( index ) );
    }
}
