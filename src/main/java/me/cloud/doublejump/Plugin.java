package me.cloud.doublejump;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
import me.cloud.doublejump.commands.DoubleJumpToggleAutocompleter;
import me.cloud.doublejump.commands.DoubleJumpToggleCommand;
import me.cloud.doublejump.events.OnJoinEvent;
import me.cloud.doublejump.events.OnMoveEvent;
import me.cloud.doublejump.events.OnPlayerFlyEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Plugin extends JavaPlugin {

    public static ArrayList<UUID> cooldown = new ArrayList<>();

    public static ArrayList<UUID> doubleJumpEnabled = new ArrayList<>();


    @Override
    public void onEnable() {
        WorldGuard worldGuard = WorldGuard.getInstance();
        RegionManager regions = worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(getServer().getWorlds().get(0)));
        GlobalProtectedRegion globalRegion = new GlobalProtectedRegion("global");
        regions.addRegion(globalRegion);
        getServer().getPluginManager().registerEvents(new OnMoveEvent(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerFlyEvent(this), this);
        getServer().getPluginManager().registerEvents(new OnJoinEvent(this), this);
        getCommand("doublejump").setExecutor(new DoubleJumpToggleCommand(this));
        getCommand("doublejump").setTabCompleter(new DoubleJumpToggleAutocompleter());
        getLogger().info("Plugin has been enabled!");

        for (Player player : getServer().getOnlinePlayers()) {
            if (player.getAllowFlight() && !doubleJumpEnabled.contains(player.getUniqueId())) {
                player.setAllowFlight(false);
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled, goodbye!");
    }
}
