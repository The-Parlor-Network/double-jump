package me.cloud.doublejump.events;

import me.cloud.doublejump.Plugin;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class OnMoveEvent implements Listener {

    private final Plugin plugin;

    public OnMoveEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getVelocity().getY() > 0 && player.getLocation().getBlock().getType() != Material.LADDER && Plugin.doubleJumpEnabled.contains(player.getUniqueId())) {
            double jumpVelocity = 0.42F;
            if (player.hasPotionEffect(PotionEffectType.JUMP)) {
                jumpVelocity += (float) (player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() + 1) * 0.1F;
            }
            if (event.getPlayer().getLocation().getBlock().getType() != Material.LADDER) {
                if (!player.isOnGround() && Double.compare(player.getVelocity().getY(), jumpVelocity) == 0) {
                    if (player.getGameMode() != GameMode.CREATIVE) {
                        player.setAllowFlight(true);
                    }
                }
            }
        }
    }
}
