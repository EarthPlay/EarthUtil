/* (C) 2022 EarthPlay */
package de.earthplay.earthutil;

import org.bukkit.plugin.java.JavaPlugin;

public class EarthUtil extends JavaPlugin {
    public static EarthUtil instance;

    @Override
    public void onEnable() {
        getLogger().info("EarthUtil was successfully enabled!");

        instance = this;
    }
}
