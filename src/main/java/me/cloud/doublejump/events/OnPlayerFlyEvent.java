package me.cloud.doublejump.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.cloud.doublejump.Plugin;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class OnPlayerFlyEvent implements Listener {

    private final Plugin plugin;

    public OnPlayerFlyEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    public static Collection<ProtectedRegion> getRegionsAt(Location location) {
        List<ProtectedRegion> regions = new ArrayList<>();

        World world = BukkitAdapter.adapt(location.getWorld());
        if (world == null) {
            return regions;
        }

        RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);
        if (rm == null) {
            return regions;
        }

        for (ProtectedRegion region : rm.getApplicableRegions(BukkitAdapter.asBlockVector(location))) {
            regions.add(region);
        }

        return regions;
    }

    @EventHandler
    public void onFly(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE && Plugin.doubleJumpEnabled.contains(player.getUniqueId())) {

            event.setCancelled(true);
            player.setAllowFlight(false);

            Collection<ProtectedRegion> regions = getRegionsAt(player.getLocation());

            if (!Objects.equals(regions.stream().findFirst().get().getId(), "spawn")) {
                player.sendMessage("§7[§bDJ§7] §cYou cannot double jump here!");
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0.5F);
                return;
            }


            if (Plugin.cooldown.contains(player.getUniqueId())) {
                return;
            }

            if (player.getLocation().getPitch() < 50F) {
                player.setVelocity(player.getLocation().getDirection().multiply(1).setY(1));
            } else {
                player.setVelocity(player.getLocation().getDirection().setY(1));
            }

            player.playSound(player, Sound.ENTITY_ZOMBIE_INFECT, 1, 1.5F);
            player.setAllowFlight(true);
            player.setAllowFlight(false);

//            Plugin.cooldown.add(player.getUniqueId());
//
//
//
//            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
//                Plugin.cooldown.remove(player.getUniqueId());
//            }, 5L);
        }

    }
}
