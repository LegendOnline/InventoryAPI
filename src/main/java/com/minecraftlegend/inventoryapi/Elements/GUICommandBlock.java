package com.minecraftlegend.inventoryapi.Elements;

import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_10_R1.TileEntityCommand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
@Deprecated
public class GUICommandBlock{
    private Player player;
    private String text;

    public GUICommandBlock(Player player){
        this.player = player;
    }


    public void open(String placeholder){
        CraftPlayer cp = (CraftPlayer) player;
        TileEntityCommand cmd = new TileEntityCommand();
        Location l = player.getLocation();

        cmd.getCommandBlock().setName("testName");
        cmd.getCommandBlock().setCommand(placeholder);
        player.sendBlockChange(l, Material.COMMAND.getId(), (byte) 0);
        cp.getHandle().playerConnection.sendPacket(cmd.getUpdatePacket());
        cp.getHandle().playerConnection.sendPacket(new PacketPlayOutOpenSignEditor(new BlockPosition(l.getBlockX(),l.getBlockY(),l.getBlockZ())));
        player.sendBlockChange(l, l.getBlock().getType().getId(), l.getBlock().getData());
    }



}
