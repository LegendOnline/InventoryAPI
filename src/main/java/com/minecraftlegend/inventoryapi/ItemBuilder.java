package com.minecraftlegend.inventoryapi;


import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2017 by Jan Hof
 * All rights reserved.
 **/
public class ItemBuilder {


    private ItemStack item;
    private ItemMeta meta;

    /**
     * Creates an ItemBuilder
     */
    public ItemBuilder(){
        this(null);
    }


    /**
     * Creates an ItemBuilder
     * @param name of the item
     */
    public ItemBuilder(String name){
        item = new ItemStack( Material.STONE );
        meta = item.getItemMeta();
        if(name != null){
            meta.setDisplayName( name );
            item.setItemMeta( meta );
        }

    }

    /**
     * Changes the displayName of the item
     * @param name the new displayName
     * @return this ItemBuilder
     */
    public ItemBuilder name(String name){
        Validate.notNull( name , "Name is null");
        meta.setDisplayName( name );
        item.setItemMeta( meta );
        return this;
    }

    /**
     * Changes the Material of the item
     * @param m the new Material
     * @return this ItemBuilder
     */
    public ItemBuilder type(Material m){
        Validate.notNull( m , "Material is null");
        item.setType( m );
        return this;
    }

    /**
     * Adds a {@link String} to the end of the item's lore
     * @param str the String that should be appended
     * @return this ItemBuilder
     */
    public ItemBuilder lore(String str){
        Validate.notNull( str , "Lore is null");
        meta.getLore().add( str );
        item.setItemMeta( meta );
        return this;
    }

    /**
     * Sets the item's lore
     * @param str an undefined length of Strings the item's lore should be build up by
     * @return this ItemBuilder
     */
    public ItemBuilder lore(String ... str){
        Validate.notNull( str , "Lore is null");
        meta.setLore( Arrays.asList( str ) );
        item.setItemMeta( meta );
        return this;
    }

    /**
     * Sets the item's lore
     * @param str an {@link List} of Strings the item's lore should be build up by
     * @return this ItemBuilder
     */
    public ItemBuilder lore( List<String> str){
        Validate.notNull( str , "Lore is null");
        meta.setLore( str );
        item.setItemMeta( meta );
        return this;
    }

    /**
     * @param n the new amount this item should have
     * @return this ItemBuilder
     */
    public ItemBuilder amount(int n){
        Validate.notNull( n , "Amount is null");
        item.setAmount( n );
        return this;
    }

    /**
     * This is used to set subIds to the item
     * example {@link Material#STAINED_GLASS_PANE} with durability of 1
     *          will be a glass pane with an orange color
     * @param d the new durability this item should have
     * @return this ItemBuilder
     */
    public ItemBuilder durability(short d){
        Validate.notNull( d , "Material is null");
        item.setDurability( d );
        return this;
    }

    /**
     *
     * @param d the data that should be set to the item
     * @return
     */
    public ItemBuilder data(byte d){
        Validate.notNull( d , "Data is null");
        item.getData().setData(d  );
        return this;
    }

    /**
     * Adds an unsafe enchantment
     * @param e the {@link Enchantment} to be added
     * @param val the Enchantment's level
     * @return this ItemBuilder
     */
    public ItemBuilder enchant( Enchantment e, int val){
        Validate.notNull( e , "Enchantment is null");
        Validate.notNull( val , "Enchantment Level is null");
        item.addUnsafeEnchantment( e, val );
        return this;
    }

    /**
     *
     * @param flag the {@link ItemFlag} to be added
     * @return this ItemBuilder
     */
    public ItemBuilder flag( ItemFlag flag){
        Validate.notNull( flag , "ItemFlag is null");
        meta.getItemFlags().add( flag );
        item.setItemMeta( meta );
        return this;
    }

    /**
     *
     * @param flags undefined length of ItemFlags that should be added to the item
     * @returnt this ItemBuilder
     */
    public ItemBuilder flag( ItemFlag... flags){
        Validate.notNull( flags , "ItemFlag is null");
        meta.getItemFlags().addAll( Arrays.asList( flags ) );
        item.setItemMeta( meta );
        return this;
    }

    /**
     * Sets the items properties to be a head with the skin of the given player
     * @param player skin to be used
     * @return this ItemBuilder
     */
    public ItemBuilder head( Player player){
        item.setType( Material.SKULL_ITEM );
        item.setDurability( (short) 3 );
        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        headMeta.setOwner( player.getName() );
        item.setItemMeta( headMeta );
        return this;
    }

    /**
     * Sets the items properties to be a head with the skin of the given player
     * @param player skin to be used
     * @return this ItemBuilder
     */
    public ItemBuilder head( OfflinePlayer player){
        item.setType( Material.SKULL_ITEM );
        item.setDurability( (short) 3 );
        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        headMeta.setOwner( player.getName() );
        item.setItemMeta( headMeta );
        return this;
    }

    /**
     * Sets the items properties to be a head with the skin of the given player
     * @param player name of the player
     * @param title title of the head
     * <h2>Note:</h2>
     * After creating an head item it cant be modified furthermore because of the immutable itemMeta
     * @return this ItemBuilder
     */
    public ItemBuilder head( String player, String title){
        item.setType( Material.SKULL_ITEM );
        item.setDurability( (short) 3 );

        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        headMeta.setOwner( player );
        headMeta.setDisplayName( title );
        item.setItemMeta( headMeta );
        return this;
    }


    /**
     * Builds the item by all the properties given by previous calls to {@link ItemBuilder}
     * @return the finished {@link ItemStack}
     */
    public ItemStack build(){
        return item;
    }

}
