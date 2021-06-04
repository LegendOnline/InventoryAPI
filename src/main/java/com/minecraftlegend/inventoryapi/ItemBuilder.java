package com.minecraftlegend.inventoryapi;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    public ItemBuilder() {
        this(new ItemStack(Material.STONE));
    }


    /**
     * Creates an ItemBuilder
     *
     * @param name of the item
     */
    public ItemBuilder(String name) {
        item = new ItemStack(Material.STONE);
        meta = item.getItemMeta();
        if (name != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }

    }

    /**
     * Creates an ItemBuilder
     *
     * @param item as preset
     */
    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    /**
     * Changes the displayName of the item
     *
     * @param name the new displayName
     * @return this ItemBuilder
     */
    public ItemBuilder name(String name) {
        Objects.requireNonNull(name);
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Changes the Material of the item
     *
     * @param m the new Material
     * @return this ItemBuilder
     */
    public ItemBuilder type(Material m) {
        Objects.requireNonNull(m);
        item.setType(m);
        return this;
    }

    /**
     * Adds a {@link String} to the end of the item's lore
     *
     * @param str the String that should be appended
     * @return this ItemBuilder
     */
    public ItemBuilder lore(String str) {
        Objects.requireNonNull(str);

        if (!meta.hasLore())
            meta.setLore(new ArrayList<>());

        List<String> metaLore = meta.getLore();
        for (String lore : str.split("\n")) {
            metaLore.add(lore);
        }
        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the item's lore
     *
     * @param str an undefined length of Strings the item's lore should be build up by
     * @return this ItemBuilder
     */
    public ItemBuilder lore(String... str) {
        Objects.requireNonNull(str);
        meta.setLore(Arrays.asList(str));
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the item's lore
     *
     * @param str an {@link List} of Strings the item's lore should be build up by
     * @return this ItemBuilder
     */
    public ItemBuilder lore(List<String> str) {
        Objects.requireNonNull(str);
        meta.setLore(str);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * @param n the new amount this item should have
     * @return this ItemBuilder
     */
    public ItemBuilder amount(int n) {
        Objects.requireNonNull(n);
        item.setAmount(n);
        return this;
    }

    /**
     * This is used to set subIds to the item
     * example {@link Material#STAINED_GLASS_PANE} with durability of 1
     * will be a glass pane with an orange color
     *
     * @param d the new durability this item should have
     * @return this ItemBuilder
     */
    public ItemBuilder durability(short d) {
        Objects.requireNonNull(d);
        item.setDurability(d);
        return this;
    }

    /**
     * @param d the data that should be set to the item
     * @return
     */
    public ItemBuilder data(byte d) {
        Objects.requireNonNull(d);
        item.getData().setData(d);
        return this;
    }

    /**
     * Adds an unsafe enchantment
     *
     * @param e   the {@link Enchantment} to be added
     * @param val the Enchantment's level
     * @return this ItemBuilder
     */
    public ItemBuilder enchant(Enchantment e, int val) {
        Objects.requireNonNull(e, "Enchantment is null");
        Objects.requireNonNull(val, "Enchantment Level is null");
        item.addUnsafeEnchantment(e, val);
        return this;
    }

    /**
     * @param flag the {@link ItemFlag} to be added
     * @return this ItemBuilder
     */
    public ItemBuilder flag(ItemFlag flag) {
        Objects.requireNonNull(flag);
        meta.getItemFlags().add(flag);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * @param flags undefined length of ItemFlags that should be added to the item
     * @returnt this ItemBuilder
     */
    public ItemBuilder flag(ItemFlag... flags) {
        Objects.requireNonNull(flags);
        meta.getItemFlags().addAll(Arrays.asList(flags));
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the items properties to be a head with the skin of the given player
     *
     * @param player skin to be used
     * @return this ItemBuilder
     */
    public ItemBuilder head(Player player) {
        item.setType(Material.SKULL_ITEM);
        item.setDurability((short) 3);
        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        headMeta.setOwner(player.getName());
        item.setItemMeta(headMeta);
        return this;
    }

    /**
     * Sets the items properties to be a head with the skin of the given player
     *
     * @param player skin to be used
     * @return this ItemBuilder
     */
    public ItemBuilder head(OfflinePlayer player) {
        item.setType(Material.SKULL_ITEM);
        item.setDurability((short) 3);
        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        headMeta.setOwner(player.getName());
        item.setItemMeta(headMeta);
        return this;
    }

    /**
     * Sets the items properties to be a head with the skin of the given player
     *
     * @param player name of the player
     * @param title  title of the head
     *               <h2>Note:</h2>
     *               After creating an head item it cant be modified furthermore because of the immutable itemMeta
     * @return this ItemBuilder
     */
    public ItemBuilder head(String player, String title) {
        item.setType(Material.SKULL_ITEM);
        item.setDurability((short) 3);

        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        headMeta.setOwner(player);
        headMeta.setDisplayName(title);
        item.setItemMeta(headMeta);
        return this;
    }


    /**
     * Builds the item by all the properties given by previous calls to {@link ItemBuilder}
     *
     * @return the finished {@link ItemStack}
     */
    public ItemStack build() {
        return item;
    }

}
