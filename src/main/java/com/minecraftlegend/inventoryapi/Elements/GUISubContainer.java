package com.minecraftlegend.inventoryapi.Elements;

import com.minecraftlegend.inventoryapi.Events.ComponentClickEvent;
import com.minecraftlegend.inventoryapi.Events.ContainerCloseEvent;
import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUIEvent;
import com.minecraftlegend.inventoryapi.GUILayout;
import com.minecraftlegend.inventoryapi.McGui;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class GUISubContainer extends McGui {

    private GUIContainer parent;
    private GUIButton exitButton, nextButton;
    private boolean openParentOnClose,useExitButton,checkParent;
    private GUISubContainer child;
    private JavaPlugin plugin;

    public GUISubContainer( JavaPlugin plugin, GUISubContainer child, String title, GUILayout layout, boolean openParentOnClose) {
        this(plugin, null,child,title,layout,openParentOnClose);
    }

    public GUISubContainer(JavaPlugin plugin,GUISubContainer child, String title,boolean useExitButton, GUILayout layout) {
        this(plugin, null,child,title,useExitButton,layout);
    }

    public GUISubContainer(JavaPlugin plugin, GUIContainer parent, GUISubContainer child, String title, GUILayout layout, boolean openParentOnClose) {
        super(plugin, title, layout);
        this.parent = parent;
        this.child = child;
        this.openParentOnClose = openParentOnClose;
        this.plugin = plugin;
        initButtons();

        GUIEvent close = new GUIEvent() {
            @Override
            public void onClose(ContainerCloseEvent event) {
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            parent.draw((Player) event.getPlayer());
                        }catch (NullPointerException e){

                        }
                    }
                },1);
            }
        };


        if(child != null){
            child.setParent(this);
            add(nextButton);
            nextButton.addEvent(new GUIEvent() {
                @Override
                public void onClick(ComponentClickEvent event) {
                    removeEvent(close);
                    child.draw((Player) event.getPlayer());
                    addEvent(close);
                }
            });
        }

            if(this.openParentOnClose){
                addEvent(close);
            }



    }

    /**
     *
     * @param parent
     * @param title
     * @param layout
     */
    public GUISubContainer(JavaPlugin plugin,GUIContainer parent,GUISubContainer child, String title,boolean useExitButton, GUILayout layout) {
        super(plugin,title, layout);
        this.parent = parent;
        this.child = child;
        this.useExitButton = useExitButton;
        this.checkParent = parent == null;
        this.plugin = plugin;
        initButtons();

        if(child != null){
            child.setParent(this);
            add(nextButton);
            nextButton.addEvent(new GUIEvent() {
                @Override
                public void onClick(ComponentClickEvent event) {
                    child.draw((Player) event.getPlayer());
                }
            });
        }

        if(!checkParent) handleExit();

    }

    private void handleExit(){
        if(useExitButton) {
            initButtons();
            add(exitButton);
            exitButton.addEvent(new GUIEvent() {
                @Override
                public void onClick(ComponentClickEvent event) {
                    parent.draw((Player) event.getPlayer());
                }
            });

        }
    }

    public GUIContainer getParent() {
        return parent;
    }

    public void setParent(GUIContainer parent) {
        this.parent = parent;
        if(this.openParentOnClose){
            addEvent(new GUIEvent() {
                @Override
                public void onClose(ContainerCloseEvent event) {
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            parent.draw((Player) event.getPlayer());
                        }
                    },2);
                }
            });
        }

        if(checkParent) handleExit();
    }

    private void initButtons(){
        if(exitButton == null){
            this.exitButton = new GUIButton("§4X", Material.REDSTONE_BLOCK);
            exitButton.setPosition(new Vector2i(8,0));
        }
        if(nextButton == null){
            this.nextButton = new GUIButton("§aNext", Material.WOOL,(byte)5);
            nextButton.setPosition(new Vector2i(8, getSize().getY() -1 ));
        }
    }

    public void setExitIcon(Material m){
        exitButton.setIcon(m);
        exitButton.draw();
    }

    public void setNextIcon(Material m){
        nextButton.setIcon(m);
        nextButton.draw();
    }

    public void setExitIcon(ItemStack i){
        exitButton.setIcon(i);
        exitButton.draw();
    }

    public void setNextIcon(ItemStack i){
        nextButton.setIcon(i);
        nextButton.draw();
    }

    public GUIButton getExitButton() {
        return exitButton;
    }

    public void setExitButton(GUIButton exitButton) {
        this.exitButton = exitButton;
    }

    public GUIButton getNextButton() {
        return nextButton;
    }

    public void setNextButton(GUIButton nextButton) {
        this.nextButton = nextButton;
    }


    public GUISubContainer getChild() {
        return child;
    }

}
