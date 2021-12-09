package cz.hopperw12.stats;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("stats").setExecutor(new CommandStats());

    }
}
