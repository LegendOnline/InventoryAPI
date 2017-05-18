package com.minecraftlegend.inventoryapi.utils;

/**
 * @author Juyas, Drayke
 * @version 0.1
 * @since 28.02.2017
 */
public final class ReflectionCastException extends Exception
{

    public ReflectionCastException( Class clazz, Exception cause )
    {
        super( "failed to cast to " + clazz.getCanonicalName(), cause );
    }

}
