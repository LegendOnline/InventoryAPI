package main.java.com.minecraftlegend.inventoryapi;

import main.java.com.minecraftlegend.inventoryapi.EventListeners.McGuiListener;
import main.java.com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class McGui implements GUIContainer{

    private Inventory inventory;
    private String title = "McGUI";
    private GUILayout layout;
    private List<GUIEvent> events = new ArrayList<>();
    private List<GUIComponent> components = new ArrayList<>();
    private boolean lock;
    private McGuiListener listener;

    public McGui(JavaPlugin plugin, String title, GUILayout layout){
        this.title = title;
        this.layout = layout;
        listener = new McGuiListener(this);
        plugin.getServer().getPluginManager().registerEvents(listener,plugin);
        init();
    }

    public McGui(JavaPlugin plugin, GUILayout layout){
        this(plugin,"McGUI",layout);
    }


    @Override
    public void init(){
        if(layout.getInventoryType() == InventoryType.CHEST){
            inventory = Bukkit.createInventory(null,layout.getInventorySize(),title);
        }else{
            inventory = Bukkit.createInventory(null,layout.getInventoryType(),title);
        }

        components.forEach(GUIComponent::init);
        components.forEach(e -> {
            if(e instanceof GUIElement){
                ((GUIElement) e).draw();
            }
        });
        layout.apply(this);
        components.forEach(GUIComponent::postInit);

    }



    public void draw(Player player){
        if(inventory.getType() == InventoryType.CHEST || inventory.getType() == InventoryType.ENDER_CHEST) {
            player.openInventory(inventory);
        }else if(inventory.getType() == InventoryType.CRAFTING){
            InventoryView view = player.openWorkbench(player.getLocation(), true);
            inventory = view.getTopInventory();
        } else if(inventory.getType() == InventoryType.ENCHANTING){
            InventoryView view = player.openEnchanting(player.getLocation(), true);
            inventory = view.getTopInventory();
        }
    }

    public void dispose(Player player){
        player.closeInventory();
        HandlerList.unregisterAll(listener);
    }


    @Override
    public void add(GUIComponent component) {
        components.add(component);
        if(component instanceof GUIElement){
            ((GUIElement) component).setParent(this);
            if(inventory != null)
             ((GUIElement) component).draw();
        }
    }

    @Override
    public void remove(GUIComponent component) {
        components.remove(component);
        if(component instanceof GUIElement){
            ((GUIElement) component).setParent(null);
        }
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte getRandomColor(){
        return (byte) new Random().nextInt(16);
    }


    public static ItemStack getPlayerHead(Player player){
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwner(player.getName());
        head.setItemMeta(headMeta);
        return head;
    }

    public static ItemStack getPlayerHead(Player player, String title){
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwner(player.getName());
        headMeta.setDisplayName(title);
        head.setItemMeta(headMeta);
        return head;
    }


    @Override
    public void addEvent(GUIEvent event) {
        this.events.add(event);
    }

    @Override
    public void removeEvent(GUIEvent event) {
        this.events.remove(event);
    }

    @Override
    public void setLayout(GUILayout layout) {
        this.layout = layout;
    }

    @Override
    public GUILayout getLayout() {
        return layout;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void lock() {
        this.lock = true;
    }

    @Override
    public void unlock() {
        this.lock = false;
    }

    @Override
    public boolean isLocked() {
        return lock;
    }

    @Override
    public Vector2i getSize() {
        return layout.getSize();
    }

    @Override
    public void setSize(Vector2i dimension) {
        Validate.isTrue(inventory.getType() == InventoryType.CHEST,"Size can only be adjusted if the inventory is a chest!");
        ItemStack[] contents = inventory.getContents();
        InventoryHolder holder = inventory.getHolder();
        int stack = inventory.getMaxStackSize();
        String title = inventory.getTitle();

        inventory = Bukkit.createInventory(holder,dimension.getX()*dimension.getY(),title);
        inventory.setContents(contents);
        inventory.setMaxStackSize(stack);
    }

    @Override
    public List<GUIEvent> getEvents() {
        return events;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GUIComponent> getComponents() {
        return components;
    }
}
