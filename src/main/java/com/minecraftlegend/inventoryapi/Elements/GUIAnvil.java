package com.minecraftlegend.inventoryapi.Elements;


import com.minecraftlegend.inventoryapi.EventListeners.GuiAnvilListener;
import com.minecraftlegend.inventoryapi.GUIComponent;
import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUIElement;
import com.minecraftlegend.inventoryapi.GUIEvent;
import com.minecraftlegend.inventoryapi.GUILayout;
import com.minecraftlegend.inventoryapi.utils.AnvilSlot;
import com.minecraftlegend.inventoryapi.utils.NMSUtils;
import com.minecraftlegend.inventoryapi.utils.Reflect;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.minecraftlegend.inventoryapi.utils.PackageType.CRAFTBUKKIT_ENTITY;
import static com.minecraftlegend.inventoryapi.utils.PackageType.MINECRAFT_SERVER;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/

public class GUIAnvil implements GUIContainer {

    private Inventory inventory;
    private GUILayout layout;
    private List<GUIEvent> events = new ArrayList<>();
    private List<GUIComponent> components = new ArrayList<>();
    private boolean lock;
    private GuiAnvilListener listener;
    private HashMap<AnvilSlot, GUIElement> items = new HashMap<AnvilSlot, GUIElement>();
    private JavaPlugin plugin;

    public GUIAnvil(JavaPlugin plugin) {
        this.plugin = plugin;
        listener = new GuiAnvilListener( this );
        plugin.getServer().getPluginManager().registerEvents( listener, plugin);
        init();
    }

    @Override
    public void init() {
        components.forEach( GUIComponent::init );
        if(layout != null){
            layout.apply( this );
        }
        components.forEach( GUIComponent::postInit );
    }


    @Override
    public void add( GUIComponent component ) {
        components.add( component );
        if ( component instanceof GUIElement) {
            ( (GUIElement) component ).setParent( this );
        }
    }

    @Override
    public void remove( GUIComponent component ) {
        components.remove( component );
        if ( component instanceof GUIElement ) {
            ( (GUIElement) component ).setParent( null );
        }
    }


    @Override
    public GUIElement getElement( Vector2i position ) {
        for ( GUIComponent component : components ) {
            if(component instanceof GUIElement){
                if(( (GUIElement) component ).getPosition().equals( position )){
                    return (GUIElement) component;
                }
            }
        }
        return null;
    }

    private void reflectOpen(Player player) throws Exception
    {

        //remember, the anvil is only usable since MC 1.8

        Class craftPlayerClass = CRAFTBUKKIT_ENTITY.getClass( "CraftPlayer" );
        Class containerAnvilClass = MINECRAFT_SERVER.getClass( "ContainerAnvil" );
        Class playerInventoryClass = MINECRAFT_SERVER.getClass( "PlayerInventory" );
        Class worldClass = MINECRAFT_SERVER.getClass( "World" );
        Class blockPositionClass = MINECRAFT_SERVER.getClass( "BlockPosition" );
        Class entityHumanClass = MINECRAFT_SERVER.getClass( "EntityHuman" );
        Class packetClass = MINECRAFT_SERVER.getClass( "PacketPlayOutOpenWindow" );
        Class chatMessageClass = MINECRAFT_SERVER.getClass( "ChatMessage" );

        //EntityPlayer p = ((CraftPlayer) player).getHandle();
        Object entityPlayer = Reflect.invokeMethod( player, craftPlayerClass, "getHandle" );

        //ContainerAnvil container = new ContainerAnvil(p);
        Object anvil = Reflect.getConstructor( containerAnvilClass, playerInventoryClass, worldClass, blockPositionClass, entityHumanClass )
                .newInstance( entityHumanClass.getField( "inventory" ).get( entityPlayer ), entityHumanClass.getField( "world" ).get( entityPlayer ),
                        NMSUtils.getBlockPosition(player.getLocation()), entityPlayer );

        //inv = container.getBukkitView().getTopInventory();
        inventory = (Inventory) Reflect.invokeMethod( Reflect.invokeMethod( anvil, "getBukkitView" ), "getTopInventory" );

        for ( AnvilSlot slot : items.keySet() )
        {
            inventory.setItem( slot.getSlot(), items.get( slot ).getNative() );
        }

        //int c = p.nextContainerCounter();
        int c = (int) Reflect.invokeMethod( entityPlayer, "nextContainerCounter" );

        //new ChatMessage("Repairing"), 0)
        Object chatMessage = Reflect.instantiateObject( chatMessageClass, "Repairing", new Object[] { } );

        //new PacketPlayOutOpenWindow(c, "minecraft:anvil", chatmessage)
        Object packet = Reflect.instantiateObject( packetClass, c, "minecraft:anvil", chatMessage, 0 );

        //p.playerConnection
        Object playerConnection = Reflect.getValue( entityPlayer, true, "playerConnection" );

        //playerConnection.sendPacket
        Reflect.invokeMethod( playerConnection, "sendPacket", packet );

        //method "a" override
        Reflect.setValue( anvil, false, "checkReachable", false );

        //p.activeContainer = container;
        Field activeContainer = entityHumanClass.getField( "activeContainer" );
        activeContainer.set( entityPlayer, anvil );

        //get active container of player
        Object container = activeContainer.get( entityPlayer );

        //p.activeContainer.windowId = c;
        activeContainer.getType().getField( "windowId" ).set( container, c );

        //p.activeContainer.addSlotListener(p);
        Reflect.invokeMethod( container, "addSlotListener", entityPlayer );

    }

    @Override
    public GUIElement getElementByName( String name ) {
        return null;
    }

    @Override
    public List<GUIComponent> getComponents() {
        return components;
    }

    @Override
    public Vector2i getSize() {
        return layout.getSize();
    }

    @Override
    public void setSize( Vector2i dimension ) {
        throw new IllegalStateException( "The size of an anvil inventory cant be changed!" );
    }

    @Override
    public GUILayout getLayout() {
        return layout;
    }

    @Override
    public void setLayout( GUILayout layout ) {
        this.layout = layout;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void setInventory( Inventory inventory ) {
        this.inventory = inventory;
    }

    @Override
    public void draw( Player player ) {
        try {
            reflectOpen(player);
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose( Player player ) {
        player.closeInventory();
        HandlerList.unregisterAll( listener );
    }

    @Override
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void addEvent( GUIEvent event ) {
        events.add( event );
    }

    @Override
    public void removeEvent( GUIEvent event ) {
        events.remove( event );
    }

    @Override
    public void addGlobalEvent( GUIEvent event ) {
    }

    @Override
    public void removeGlobalEvent( GUIEvent event ) {

    }

    @Override
    public List<GUIEvent> getGlobalEvents() {
        return null;
    }

    @Override
    public List<GUIEvent> getEvents() {
        return events;
    }

    @Override
    public void lock() {
        lock = true;
    }

    @Override
    public void unlock() {
        lock = false;
    }

    @Override
    public boolean isLocked() {
        return lock;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch ( CloneNotSupportedException e ) {
            e.printStackTrace();
        }
        return null;
    }

    public void setSlot(GUIElement element, AnvilSlot slot){
        items.put( slot, element );
        add( element );
    }

    public GUIElement getSlot(AnvilSlot slot){
       return items.get( slot );
    }

    public void clearSlot(AnvilSlot slot){
        remove( items.get( slot ) );
        items.remove( slot );
        inventory.setItem( slot.getSlot(), new ItemStack( Material.AIR ) );
    }


}
