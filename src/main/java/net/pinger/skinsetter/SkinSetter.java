package net.pinger.skinsetter;

import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.Skin;
import net.pinger.skinsetter.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SkinSetter extends JavaPlugin {

    private Skin skin;

    @Override
    public void onEnable() {
        // This might happen with new versions
        // Or with versions older than 1.8
        // And the plugin will not have it's core functionality
        // So it is advised to disable it
        if (DisguiseAPI.getProvider() == null) {
            getLogger().info("Failed to find the provider for this version");
            getLogger().info("Disabling...");

            // Disable the plugin
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        // Fetch the url from the config
        String skinUrl = getConfig().getString("skin");

        // Using the SkinManager
        // Try to get the skin from the url
        // Or use the fallback skin (Tylarzz)
        DisguiseAPI.getSkinManager().getFromImage(skinUrl, response -> {
            // Check if the response was successful
            if (!response.success()) {
                this.skin = DisguiseAPI.getSkinManager().getFromMojang("Tylarzz");
                return;
            }

            this.skin = response.get();
        });

        // Register the player listener
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public Skin getSkin() {
        return skin;
    }
}
