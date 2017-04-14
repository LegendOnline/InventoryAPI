package com.minecraftlegend.inventoryapi.Elements;


import com.minecraftlegend.inventoryapi.EventListeners.GuiAnvilListener;
import com.minecraftlegend.inventoryapi.GUIComponent;
import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUIElement;
import com.minecraftlegend.inventoryapi.GUIEvent;
import com.minecraftlegend.inventoryapi.GUILayout;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
@Deprecated
public class GUIAnvil implements GUIContainer {

    private Inventory inventory;
    private Player player;
    private String title = "McGUI";
    private GUILayout layout;
    private List<GUIEvent> events = new ArrayList<>();
    private List<GUIComponent> components = new ArrayList<>();
    private boolean lock;
    private GuiAnvilListener listener;
    private GUILabel ingredient1,ingredient2,result;
    private JavaPlugin plugin;

    public GUIAnvil(JavaPlugin plugin, Player player, String title, GUILayout layout){
        this.player = player;
        this.title = title;
        this.layout = layout;
        this.plugin = plugin;
        Validate.isTrue(layout.getInventoryType() != InventoryType.ANVIL,"Layout has to be compatible to anvil inventory!");
        listener = new GuiAnvilListener(this);
        plugin.getServer().getPluginManager().registerEvents(listener,plugin);
        init();
    }

    public GUIAnvil(JavaPlugin plugin,Player player,GUILayout layout){
        this(plugin,player,"McGUI",layout);
    }



    @Override
    public void init(){

        components.forEach(GUIComponent::init);
        layout.apply(this);
        components.forEach(GUIComponent::postInit);
    }


    @Override
    public void add(GUIComponent component) {
        components.add(component);
        if(component instanceof GUIElement ){
            ((GUIElement) component).setParent(this);
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
    public List<GUIComponent> getComponents() {
        return components;
    }

    @Override
    public Vector2i getSize() {
        return layout.getSize();
    }

    @Override
    public void setSize(Vector2i dimension) {
        throw new IllegalStateException("The size of an anvil inventory cant be changed!");
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
        this.inventory = (AnvilInventory) inventory;
    }

    @Override
    public void draw(Player player) {
        player.openInventory(inventory);
    }

    @Override
    public void dispose(Player player) {
        player.closeInventory();
        HandlerList.unregisterAll(listener);;
    }

    @Override
    public void addEvent(GUIEvent event) {
        events.add(event);
    }

    @Override
    public void removeEvent(GUIEvent event) {
        events.remove(event);
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
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GUILabel getIngredient1() {
        return ingredient1;
    }

    public void setIngredient1(GUILabel ingredient1) {
        this.ingredient1 = ingredient1;
        add(ingredient1);
        inventory.setItem(0,ingredient1.getNative());
    }

    public GUILabel getIngredient2() {
        return ingredient2;
    }

    public void setIngredient2(GUILabel ingredient2) {
        this.ingredient2 = ingredient2;
        add(ingredient2);
        inventory.setItem(1,ingredient2.getNative());
    }

    public GUILabel getResult() {
        return result;
    }

    public void setResult(GUILabel result) {
        this.result = result;
        add(result);
        inventory.setItem(2,result.getNative());
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
