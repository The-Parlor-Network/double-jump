package me.cloud.doublejump.events;

import me.cloud.doublejump.Plugin;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoinEvent implements Listener {

    private final Plugin plugin;

    public OnJoinEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!Plugin.doubleJumpEnabled.contains(player.getUniqueId())) {
            Plugin.doubleJumpEnabled.add(player.getUniqueId());
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                player.sendMessage("§7[§bDJ§7] §aDouble jump mode has been enabled by default for you. Use §b/doublejump off §ato disable it.");
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }, 20L);

        }
    }
}
