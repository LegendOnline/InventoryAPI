package com.minecraftlegend.inventoryapi.Router.exception;

import com.minecraftlegend.inventoryapi.McGui;

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
public class InvalidGUIException extends RuntimeException
{
    public InvalidGUIException() {
        super("Could not construct GUI. Is a default constructor present?");
    }

    public InvalidGUIException( Class<? extends McGui> clazz ) {
        super("Could not construct GUI ("+clazz.getName()+"). Is a default constructor present?");
    }
}

/***********************************************************************************************
 *
 *                  All rights reserved, MadDev (c) copyright 2017
 *
 ***********************************************************************************************/