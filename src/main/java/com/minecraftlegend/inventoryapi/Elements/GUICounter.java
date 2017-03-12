package main.java.com.minecraftlegend.inventoryapi.Elements;

import main.java.com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import main.java.com.minecraftlegend.inventoryapi.GUIComponent;
import main.java.com.minecraftlegend.inventoryapi.GUIContainer;
import main.java.com.minecraftlegend.inventoryapi.GUIElement;
import main.java.com.minecraftlegend.inventoryapi.GUIEvent;
import main.java.com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class GUICounter  implements GUIElement {

    private Vector2i position,size;
    private GUIContainer parent;
    private List<GUIEvent> events = new ArrayList<>();
    private int count = 1, steps = 1;
    private GUILabel label;

    public GUICounter(){
        label = new GUILabel("§a" + count, Material.REDSTONE);
        label.setPosition(position);
        label.setAmount(count);
        addEvent(new GUIEvent() {
            @Override
            public void onClick(ComponentClickEvent event) {
                if(event.getClick() == ClickType.LEFT){
                    count += steps;
                    if(count < 64){
                        label.setAmount(count);
                        label.setTitle("§a" + GUICounter.this.count);
                        label.draw();
                    }
                }else if(event.getClick() == ClickType.RIGHT){
                    count -= steps;
                    if(count > 0){
                        label.setAmount(count);
                        label.setTitle("§a" + GUICounter.this.count);
                        label.draw();
                    }
                }
            }
        });
    }

    public GUICounter(int count, int steps){
        this.count = count;
        this.steps = steps;
        label = new GUILabel("§a" + count, Material.REDSTONE);
        label.setPosition(position);
        label.setAmount(count);
        addEvent(new GUIEvent() {
            @Override
            public void onClick(ComponentClickEvent event) {
                System.out.println("triggered click");
                if(event.getClick() == ClickType.LEFT){
                    GUICounter.this.count += steps;
                    if(GUICounter.this.count < 64){
                        label.setAmount(GUICounter.this.count);
                        label.setTitle("§a" + GUICounter.this.count);
                        label.draw();
                    }
                }else if(event.getClick() == ClickType.RIGHT){
                    GUICounter.this.count -= steps;
                    if(GUICounter.this.count > 0){
                        label.setAmount(GUICounter.this.count);
                        label.setTitle("§a" + GUICounter.this.count);
                        label.draw();
                    }
                }
            }
        });
    }

    @Override
    public void setParent(GUIComponent parent) {
        this.parent = (GUIContainer) parent;
        ((GUIContainer) parent).add(label);
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
        label.setPosition(dimension);
    }

    @Override
    public Vector2i getPosition() {
        return position;
    }

    @Override
    public void draw() {
        label.draw();
    }

    @Override
    public void dispose() {
        label.dispose();
    }

    @Override
    public ItemStack getNative() {
        return label.getNative();
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

    @Override
    public Object clone() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        if(count < 64 && count > 0){
            label.setAmount(count);
            label.setTitle("§a" + GUICounter.this.count);
            label.draw();
        }
    }
}
