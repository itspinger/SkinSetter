package net.pinger.skinsetter;

import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.Skin;
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
                skin = DisguiseAPI.getSkinManager().getFromMojang("Tylarzz");
                return;
            }

            skin = response.get();
        });
    }

    public Skin getSkin() {
        return skin;
    }
}
