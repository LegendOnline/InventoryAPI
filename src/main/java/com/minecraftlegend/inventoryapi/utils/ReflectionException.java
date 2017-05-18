package com.minecraftlegend.inventoryapi.utils;

/**
 * @author Juyas, Drayke
 * @version 0.1
 * @since 18.02.2017
 */
public final class ReflectionException extends Exception
{

    public ReflectionException( String message )
    {
        super( message );
    }

    public ReflectionException( Exception cause, String message )
    {
        super( message, cause );
    }

}
