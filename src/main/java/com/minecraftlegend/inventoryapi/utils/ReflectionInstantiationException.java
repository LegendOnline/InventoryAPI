package com.minecraftlegend.inventoryapi.utils;

/**
 * @author Juyas, Drayke
 * @version 0.1
 * @since 28.02.2017
 */
public final class ReflectionInstantiationException extends Exception
{

    public ReflectionInstantiationException( Exception cause )
    {
        super( "object instantiation failed", cause );
    }

}
