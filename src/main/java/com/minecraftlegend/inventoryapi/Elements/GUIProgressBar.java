package main.java.com.minecraftlegend.inventoryapi.Elements;

import main.java.com.minecraftlegend.inventoryapi.GUIComponent;
import main.java.com.minecraftlegend.inventoryapi.GUIContainer;
import main.java.com.minecraftlegend.inventoryapi.GUIElement;
import main.java.com.minecraftlegend.inventoryapi.GUIEvent;
import main.java.com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class GUIProgressBar implements GUIElement {
    public static final int EMPTY = 0, FULL = 100;

    private Vector2i position,size;
    private GUIContainer parent;
    private List<GUILabel> barElements = new ArrayList<>();
    private GUILabel activeElement;
    private List<GUIEvent> events = new ArrayList<>();
    private double progress;

    public GUIProgressBar(Vector2i position,Vector2i size){
        this.position = position;
        this.size = size;
    }

    private void prepareElements(){
        int y = 0;
        int x = 0;
        for (int i = 0; i < size.getX(); i++) {
            GUILabel label = new GUILabel(" ", Material.WOOL,(byte)8);
            label.setPosition(new Vector2i((position.getX() + x),position.getY() + y));
            parent.add(label);
            barElements.add(label);

            if(position.getX() + x > 7){
                y++;
                x = 0;
            }else x ++;
        }
        activeElement = barElements.get(0);
    }

    @Override
    public void setParent(GUIComponent parent) {
        this.parent = (GUIContainer) parent;
        prepareElements();
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
        barElements.forEach(GUIElement::draw);
    }

    @Override
    public void dispose() {

    }

    @Override
    public ItemStack getNative() {
        return activeElement.getNative();
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
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getProgress() {
        return progress;
    }

    /**
     *
     * @param progress can only be setted if this element has been added to a container!
     */
    public void setProgress(double progress) {
        Validate.isTrue(parent != null,"Progress can only be setted if this element has been added to a container!");
        if(progress >= FULL){

            progress = FULL;
            for (int i = 0; i < 9; i++) {
                barElements.get(i).setIcon(new ItemStack(Material.WOOL,1,(byte)5));
                barElements.get(i).setTitle(" ");
            }
            barElements.get(8).setTitle("§a100%");
            draw();
            return;
        }
        this.progress = progress;
        int filled = (int) ((double)barElements.size() / 100D *progress);
        for (int i = 0; i < filled; i++) {
            barElements.get(i).setIcon(new ItemStack(Material.WOOL,1,(byte)5));
            barElements.get(i).setTitle(" ");
        }
        barElements.get((filled == 0 ? 0 : filled -1)).setTitle("§a" + Math.round(progress * 100.0) / 100.0 + "%");
        draw();
    }
}
