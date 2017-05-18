package com.minecraftlegend.inventoryapi.utils;


import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.management.ReflectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.minecraftlegend.inventoryapi.utils.PackageType.CRAFTBUKKIT;
import static com.minecraftlegend.inventoryapi.utils.PackageType.MINECRAFT_SERVER;

/**
 * @author Juyas, Drayke
 * @version 0.1
 * @since 28.02.2017
 */
public final class NMSUtils
{

    public static Object getNMSPlayer( Player player ) throws ReflectionException
    {
        try
        {
            Method method = player.getClass().getMethod( "getHandle" );
            return method.invoke( player );
        }
        catch ( NoSuchMethodException | IllegalAccessException | InvocationTargetException e )
        {
            throw new ReflectionException( e, "can't get EntityPlayer" );
        }
    }

    public static Object getNMSWorld( World world ) throws ReflectionException, ReflectionCastException
    {
        Object craftWorld = getCraftWorld( world );
        try
        {
            return Reflect.invokeMethod( craftWorld, "getHandle" );
        }
        catch ( Exception e )
        {
            throw new ReflectionException( e, "can't get NMS World" );
        }
    }

    public static Entity getBukkitEntity( Object nmsEntity ) throws ReflectionCastException
    {
        try
        {
            return (Entity) Reflect.invokeMethod( nmsEntity, "getBukkitEntity" );
        }
        catch ( Exception e )
        {
            throw new ReflectionCastException( nmsEntity.getClass(), e );
        }
    }

    public static Object getCraftWorld( World world ) throws ReflectionCastException
    {
        Class craft = null;
        try
        {
            craft = CRAFTBUKKIT.getClass( "CraftWorld" );
            return craft.cast( world );
        }
        catch ( Exception e )
        {
            throw new ReflectionCastException( craft, e );
        }
    }

    public static Object getBlockPosition( Location loc ) throws ReflectionInstantiationException
    {
        return getBlockLocation( loc.getX(), loc.getY(), loc.getZ() );
    }

    public static Object getBlockLocation( int x, int y, int z ) throws ReflectionInstantiationException
    {
        try
        {
            Class blockPositionClass = MINECRAFT_SERVER.getClass( "BlockPosition" );
            return Reflect.instantiateObject( blockPositionClass, x, y, z );
        }
        catch ( Exception e )
        {
            throw new ReflectionInstantiationException( e );
        }
    }

    public static Object getBlockLocation( double x, double y, double z ) throws ReflectionInstantiationException
    {
        try
        {
            Class blockPositionClass = MINECRAFT_SERVER.getClass( "BlockPosition" );
            return Reflect.instantiateObject( blockPositionClass, x, y, z );
        }
        catch ( Exception e )
        {
            throw new ReflectionInstantiationException( e );
        }
    }

}
