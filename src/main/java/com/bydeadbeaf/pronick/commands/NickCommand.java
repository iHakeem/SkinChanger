package com.bydeadbeaf.pronick.commands;

import com.bydeadbeaf.pronick.helper.SkinTextureParser;
import com.bydeadbeaf.pronick.utlis.HttpManger;
import com.bydeadbeaf.pronick.helper.UUIDHelper;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NickCommand implements CommandExecutor {

    private void sendPacket(Packet<?> packet) {
        Bukkit.getOnlinePlayers().forEach(all -> ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packet));
    }

    private void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }




    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            System.out.println("This command only for players!");
            return false;
        }
        Player player = (Player)sender;
        if (!(args.length > 0)){
            player.sendMessage(ChatColor.RED + "Invalid arguments!");
            return false;
        }
        HttpManger httpManger = new HttpManger();
        UUIDHelper uuidHelper = new UUIDHelper();
        SkinTextureParser skinTextureParser = new SkinTextureParser();
        String uuidData = httpManger.get("https://api.mojang.com/users/profiles/minecraft/%s" , args[0]);
        String uuid = uuidHelper.getUUID(uuidData);
        String skinData = httpManger.get("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false" , uuid);
        String skin = skinTextureParser.getSkin(skinData);
        String signature = skinTextureParser.getSig(skinData);
        CraftPlayer craftPlayer = (CraftPlayer) player;


        craftPlayer.getProfile().getProperties().removeAll("textures");
        craftPlayer.getProfile().getProperties().put("textures" , new Property("textures" , skin , signature));

        new BukkitRunnable(){
            @Override
            public void run() {
                sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER , craftPlayer.getHandle()));
                PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(craftPlayer.getHandle());
                Bukkit.getOnlinePlayers().forEach(currentPlayer ->{
                    if (!(craftPlayer.getName().equals(craftPlayer.getName()))){
                        ((CraftPlayer)currentPlayer).getHandle().playerConnection.sendPacket(spawn);
                    }
                });
                int heldItemSlot = player.getInventory().getHeldItemSlot();
                int food = player.getFoodLevel();
                double health = player.getHealth();
                float xp = player.getExp();
                int level = player.getLevel();
                boolean flying = player.isFlying();

                sendPacket(player , new PacketPlayOutRespawn(craftPlayer.getHandle().getWorld().worldProvider.getDimension(),craftPlayer.getHandle().getWorld().getDifficulty()
                ,craftPlayer.getHandle().getWorld().worldData.getType() , WorldSettings.EnumGamemode.valueOf(craftPlayer.getGameMode().toString())));

                craftPlayer.getHandle().playerConnection.teleport(new Location(player.getWorld(),craftPlayer.getHandle().locX , ((CraftPlayer) player).getHandle().locY
                ,craftPlayer.getHandle().locZ , ((CraftPlayer) player).getHandle().yaw , ((CraftPlayer) player).getHandle().pitch));

                player.updateInventory();
                player.getInventory().setHeldItemSlot(heldItemSlot);
                player.setHealth(health);
                player.setExp(xp);
                player.setLevel(level);
                player.setFoodLevel(food);
                player.setFlying(flying);
            }
        }.run();


        return true;
    }



}
