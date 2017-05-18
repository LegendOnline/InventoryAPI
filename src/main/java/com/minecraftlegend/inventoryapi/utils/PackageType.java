package com.minecraftlegend.inventoryapi.utils;

import org.bukkit.Bukkit;

/**
 * <h1>legend-awakening</h1>
 *
 * @author Juyas, Drayke
 * @version 0.1
 * @since 29.04.2017
 */
public enum PackageType
{

    MINECRAFT_SERVER( "net.minecraft.server." + getServerVersion() ),
    CRAFTBUKKIT( "org.bukkit.craftbukkit." + getServerVersion() ),
    CRAFTBUKKIT_BLOCK( CRAFTBUKKIT, "block" ),
    CRAFTBUKKIT_CHUNKIO( CRAFTBUKKIT, "chunkio" ),
    CRAFTBUKKIT_COMMAND( CRAFTBUKKIT, "command" ),
    CRAFTBUKKIT_CONVERSATIONS( CRAFTBUKKIT, "conversations" ),
    CRAFTBUKKIT_ENCHANTMENS( CRAFTBUKKIT, "enchantments" ),
    CRAFTBUKKIT_ENTITY( CRAFTBUKKIT, "entity" ),
    CRAFTBUKKIT_EVENT( CRAFTBUKKIT, "event" ),
    CRAFTBUKKIT_GENERATOR( CRAFTBUKKIT, "generator" ),
    CRAFTBUKKIT_HELP( CRAFTBUKKIT, "help" ),
    CRAFTBUKKIT_INVENTORY( CRAFTBUKKIT, "inventory" ),
    CRAFTBUKKIT_MAP( CRAFTBUKKIT, "map" ),
    CRAFTBUKKIT_METADATA( CRAFTBUKKIT, "metadata" ),
    CRAFTBUKKIT_POTION( CRAFTBUKKIT, "potion" ),
    CRAFTBUKKIT_PROJECTILES( CRAFTBUKKIT, "projectiles" ),
    CRAFTBUKKIT_SCHEDULER( CRAFTBUKKIT, "scheduler" ),
    CRAFTBUKKIT_SCOREBOARD( CRAFTBUKKIT, "scoreboard" ),
    CRAFTBUKKIT_UPDATER( CRAFTBUKKIT, "updater" ),
    CRAFTBUKKIT_UTIL( CRAFTBUKKIT, "util" );

    private final String path;


    PackageType( String path )
    {
        this.path = path;
    }

    PackageType( PackageType parent, String path )
    {
        this( parent + "." + path );
    }

    public final static String getServerVersion()
    {
        return Bukkit.getServer().getClass().getPackage().getName().substring( 23 );
    }

    public String getPath()
    {
        return path;
    }

    public Class<?> getClass( String className ) throws ClassNotFoundException
    {
        return Class.forName( this + "." + className );
    }

    @Override
    public String toString()
    {
        return path;
    }

}