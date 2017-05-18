package com.minecraftlegend.inventoryapi.utils;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2017 by Jan Hof
 * All rights reserved.
 **/
public enum AnvilSlot
{

    INPUT_LEFT( 0 ),
    INPUT_RIGHT( 1 ),
    OUTPUT( 2 );

    private int slot;

    AnvilSlot( int slot )
    {
        this.slot = slot;
    }

    public static AnvilSlot bySlot( int slot )
    {
        for ( AnvilSlot anvilSlot : values() )
        {
            if ( anvilSlot.getSlot() == slot ) return anvilSlot;
        }

        return null;
    }

    public int getSlot()
    {
        return slot;
    }

}