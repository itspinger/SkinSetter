package net.pinger.skinsetter.listener;

import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.Skin;
import net.pinger.disguise.packet.PacketProvider;
import net.pinger.skinsetter.SkinSetter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private final SkinSetter skinSetter;

    public PlayerListener(SkinSetter skinSetter) {
        this.skinSetter = skinSetter;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Delay the task
        // By 1 second
        // To allow for the world to load
        Bukkit.getScheduler().runTaskLater(this.skinSetter, () -> {
            setSkin(player, skinSetter.getSkin());
        }, 20L);
    }

    private void setSkin(Player player, Skin skin) {
        // Get the packet provider
        PacketProvider provider = DisguiseAPI.getProvider();

        // Using the provider first update the properties
        // And then send the packets to refresh the player
        provider.updateProperties(player, skin);
        provider.sendServerPackets(player);
    }

}
