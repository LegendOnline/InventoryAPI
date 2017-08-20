package com.minecraftlegend.inventoryapi.utils;

import com.google.common.base.Preconditions;
import com.minecraftlegend.inventoryapi.Events.InputChangeEvent;
import com.minecraftlegend.inventoryapi.GUIComponent;
import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUIElement;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
public abstract class InventorySlotWatcher{

    private final Inventory inventory;
    private final int slot;
    private final GUIComponent watching;
    private BukkitRunnable scheduler;
    private ItemStack lastItemStack;
    private boolean active;
    private JavaPlugin plugin;

    public InventorySlotWatcher(JavaPlugin plugin,Inventory inventory, int slot, GUIComponent watching ) {
        Preconditions.checkNotNull( plugin, "Plugin owning the watcher cannot be null" );
        Preconditions.checkNotNull( inventory, "Inventory to watch cannot be null" );
        Preconditions.checkNotNull( watching, "Watching element to watch cannot be null" );

        this.plugin = plugin;
        this.inventory = inventory;
        this.slot = slot;
        this.watching = watching;
        this.active = false;
    }

    public abstract void onSlotUpdate(InputChangeEvent inputChangeEvent);

    private void update( ItemStack itemStack ) {
        InputChangeEvent changeEvent;
        if(itemStack != null && (lastItemStack == null || lastItemStack.getType() == Material.AIR)) {
            changeEvent = new InputChangeEvent(InputChangeEvent.InputActionType.IN, watching, ((GUIContainer) ((GUIElement) watching).getParent()).getPlayer());
        } else if(itemStack == null && lastItemStack != null && lastItemStack.getType() != Material.AIR) {
            changeEvent = new InputChangeEvent(InputChangeEvent.InputActionType.OUT, watching, ((GUIContainer) ((GUIElement) watching).getParent()).getPlayer());
        } else {
            changeEvent = new InputChangeEvent(InputChangeEvent.InputActionType.CHANGE, watching, ((GUIContainer) ((GUIElement) watching).getParent()).getPlayer());
        }
        lastItemStack = itemStack == null ? null : itemStack.clone();

        onSlotUpdate( changeEvent );
    }

    public void startListing() {
        if ( !active ) {
            scheduler = new BukkitRunnable() {
                @Override
                public void run() {
                    if ( ( inventory.getContents()[slot] == null ) == ( lastItemStack == null ) ) {
                        if ( lastItemStack != null && !lastItemStack.equals( inventory.getContents()[slot] ) ) {
                            update( inventory.getContents()[slot] );
                        }
                    }
                    else {
                        update( inventory.getContents()[slot] );
                    }
                }
            };
            scheduler.runTaskTimer(plugin, 1L, 1L );
            active = true;
        }
    }

    public void stopListening() {
        if ( active ) {
            scheduler.cancel();
            active = false;
        }
    }
}
