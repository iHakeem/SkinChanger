package com.bydeadbeaf.pronick;


import com.bydeadbeaf.pronick.commands.NickCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {



    @Override
    public void onEnable() {
        getCommand("nick").setExecutor(new NickCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
