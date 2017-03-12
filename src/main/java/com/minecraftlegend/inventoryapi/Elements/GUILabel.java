package main.java.com.minecraftlegend.inventoryapi.Elements;

import main.java.com.minecraftlegend.inventoryapi.GUIComponent;
import main.java.com.minecraftlegend.inventoryapi.GUIContainer;
import main.java.com.minecraftlegend.inventoryapi.GUIElement;
import main.java.com.minecraftlegend.inventoryapi.GUIEvent;
import main.java.com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class GUILabel implements GUIElement {

    private ItemStack item;
    private Vector2i position,size;
    private GUIContainer parent;
    private String title;
    private List<String> description;
    private List<GUIEvent> events = new ArrayList<>();
    private Material icon = Material.STAINED_GLASS_PANE;
    private int amount;

    public GUILabel(String title){
        this.title = title;
        item = new ItemStack(icon,1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(title);
        item.setItemMeta(itemMeta);
    }

    public GUILabel(ItemStack item){
        this.item = item;
        this.title = item.getItemMeta().getDisplayName();
    }

    public GUILabel(String title, Material icon){
        this.title = title;
        this.icon = icon;
        item = new ItemStack(icon,1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(title);
        item.setItemMeta(itemMeta);
    }

    public GUILabel(String title, Material icon, byte type){
        this.title = title;
        this.icon = icon;
        item = new ItemStack(icon,1,type);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(title);
        item.setItemMeta(itemMeta);
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
    public void draw() {
        //System.out.println("item position: ("+parent.getInventory().getTitle()+")" +position.toString());
        parent.getInventory().setItem(position.getX() + position.getY()*9,item);
    }


    @Override
    public void dispose() {
        if(parent.getInventory().getItem(position.getX() + position.getY()*9).equals(item))
         parent.getInventory().setItem(position.getX() + position.getY()*9,new ItemStack(Material.AIR));
    }

    @Override
    public void lock() {
    }

    @Override
    public void unlock() {

    }

    @Override
    public GUIComponent getParent() {
        return parent;
    }

    @Override
    public void setParent(GUIComponent parent) {
        this.parent = (GUIContainer) parent;
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
    public ItemStack getNative() {
        return item;
    }


    @Override
    public boolean isLocked() {
        return true;
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

    public void setIcon(ItemStack item) {
        this.item = item;
        this.icon = item.getType();
        this.amount = item.getAmount();
        this.title = item.getItemMeta().getDisplayName();
        this.description = item.getItemMeta().getLore();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        item.setAmount(amount);
    }
}
