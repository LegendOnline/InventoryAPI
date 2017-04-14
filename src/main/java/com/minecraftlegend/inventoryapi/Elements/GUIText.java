package com.minecraftlegend.inventoryapi.Elements;


import com.minecraftlegend.inventoryapi.Events.AnvilEvent;
import com.minecraftlegend.inventoryapi.Events.AnvilResultEvent;
import com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import com.minecraftlegend.inventoryapi.GUIComponent;
import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUIElement;
import com.minecraftlegend.inventoryapi.GUIEvent;
import com.minecraftlegend.inventoryapi.Layouts.PlainLayout;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
@Deprecated
public class GUIText implements GUIElement {

    private String text;
    private ItemStack item;
    private Vector2i position,size;
    private GUIContainer parent;
    private String title;
    private List<String> description;
    private List<GUIEvent> events = new ArrayList<>();
    private Material icon = Material.STAINED_GLASS_PANE;
    private int amount;

    public GUIText(String title){
        this.title = title;
        item = new ItemStack(icon,1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(title);
        item.setItemMeta(itemMeta);
    }

    public GUIText(String title, Material icon){
        this.title = title;
        item = new ItemStack(icon,1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(title);
        item.setItemMeta(itemMeta);
    }

    public GUIText(String title, Material icon, byte type){
        this.title = title;
        item = new ItemStack(icon,1,type);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(title);
        item.setItemMeta(itemMeta);
    }


    public void postInit(JavaPlugin plugin){
        addEvent(new GUIEvent() {
            @Override
            public void onClick(ComponentClickEvent event) {
               GUIAnvil anvil = new GUIAnvil(plugin, (Player) event.getPlayer(),"Test", new PlainLayout());
                anvil.setIngredient1(new GUILabel("Enter Text here",Material.PAPER));
                anvil.addEvent(new AnvilEvent() {
                    @Override
                    public void onResultClick(AnvilResultEvent event) {
                        event.getPlayer().sendMessage("you entered: §a" + event.getAnvil().getResult().getTitle());
                        anvil.dispose(event.getPlayer());
                    }
                });
                anvil.lock();
                anvil.draw((Player) event.getPlayer());
            }
        });
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


    @Override
    public void setParent(GUIComponent parent) {
        this.parent = (GUIContainer) parent;
    }

    @Override
    public GUIComponent getParent() {
        return parent;
    }

    @Override
    public Vector2i getSize() {
        return size;
    }

    @Override
    public void setSize(Vector2i dimension) {
        this.size = dimension;
    }

    @Override
    public void setPosition(Vector2i dimension) {
        this.position = dimension;
    }

    @Override
    public Vector2i getPosition() {
        return position;
    }

    @Override
    public void draw() {
        parent.getInventory().setItem(position.getX() + position.getY()*9,item);
    }

    @Override
    public void dispose() {
        parent.getInventory().setItem(position.getX() + position.getY()*9,new ItemStack(Material.AIR));
    }

    @Override
    public ItemStack getNative() {
        return item;
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

    }

    @Override
    public void unlock() {

    }

    @Override
    public boolean isLocked() {
        return true;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(title);
        item.setItemMeta(itemMeta);
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(description);
        item.setItemMeta(itemMeta);
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
        item.setType(icon);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        item.setAmount(amount);
    }
}
