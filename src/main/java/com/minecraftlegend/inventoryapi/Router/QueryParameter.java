package com.minecraftlegend.inventoryapi.Router;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <h1>InventoryAPI</h1>
 * <h2>Class heading</h2>
 * <p>
 * Class description.
 * </p>
 *
 * @author Drayke
 * @version 1.0
 * @since 31.08.2017.
 */
public final class QueryParameter extends ArrayList<String> {

    public QueryParameter() {
    }

    public QueryParameter( Collection< ? extends String > c ) {
        super( c );
    }

    public int getInt( int index)
    {
        return Integer.parseInt( get( index ) );
    }

}

/***********************************************************************************************
 *
 *                  All rights reserved, MadDev (c) copyright 2017
 *
 ***********************************************************************************************/