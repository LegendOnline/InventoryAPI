package main.java.com.minecraftlegend.inventoryapi.Layouts;

import main.java.com.minecraftlegend.inventoryapi.GUIContainer;
import main.java.com.minecraftlegend.inventoryapi.GUIElement;
import main.java.com.minecraftlegend.inventoryapi.GUILayout;
import main.java.com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.apache.commons.lang.Validate;
import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class ExactLayout implements GUILayout {



    protected String[] rawLayout;
    protected HashMap<Character,GUIElement> slots = new HashMap<>();
    protected int totalSize = 0;
    protected Vector2i dim;

    public ExactLayout(String ... rows){
        this.rawLayout = rows;
        for (int i = 0; i < rawLayout.length; i++) {
            String s = rawLayout[i];
            Validate.isTrue(s.length() == 9, "An inventory row has to have 9 items! row: " + i + " has " + s.length() + " chars!");
        }
        totalSize = rawLayout.length * 9;
        dim = new Vector2i(9,rawLayout.length);
    }

    public void set(char key, GUIElement element){
        slots.put(key,element);
    }


    public void apply(GUIContainer container) {


        for (int i = 0; i < rawLayout.length; i++) {
            for (int j = 0; j < rawLayout[i].length(); j++) {
                Validate.isTrue(isSet(rawLayout[i].charAt(j)), "key: " + rawLayout[i].charAt(j) + " is not referenced!");
                GUIElement e = (GUIElement) slots.get(rawLayout[i].charAt(j)).clone();
                e.setPosition(new Vector2i(j,i));
                container.add(e);
                e.draw();
            }
        }
    }


    protected boolean isSet(char key){
       return slots.containsKey(key);
    }


    public Vector2i getSize() {
        return dim;
    }


    public int getInventorySize() {
        return totalSize;
    }


    public InventoryType getInventoryType() {
        return InventoryType.CHEST;
    }
}
