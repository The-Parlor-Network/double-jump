package me.cloud.doublejump.commands;

import me.cloud.doublejump.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class DoubleJumpToggleCommand implements CommandExecutor {

    private final Plugin plugin;

    public DoubleJumpToggleCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length == 0) {
                player.sendMessage("§7[§bDJ§7] §7Usage: §7/" + command.getLabel() + " §7<§aon§7/§coff§7>");
                return true;
            }
            if (strings[0].equalsIgnoreCase("on")) {
                if (Plugin.doubleJumpEnabled.contains(player.getUniqueId())) {
                    player.sendMessage("§7[§bDJ§7] §cYou already have double jump enabled!");
                    return true;
                }
                Plugin.doubleJumpEnabled.add(player.getUniqueId());
                player.sendMessage("§7[§bDJ§7] §7Double Jump has been §aenabled!");

            } else if (strings[0].equalsIgnoreCase("off")) {
                if (!Plugin.doubleJumpEnabled.contains(player.getUniqueId())) {
                    player.sendMessage("§7[§bDJ§7] §cYou already have double jump disabled!");
                    return true;
                }
                Plugin.doubleJumpEnabled.remove(player.getUniqueId());
                player.sendMessage("§7[§bDJ§7] §7Double Jump has been §cdisabled!");
                player.setAllowFlight(false);
            }
            return true;
        }
        return false;
    }

}
