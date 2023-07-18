package me.cloud.doublejump.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class DoubleJumpToggleAutocompleter implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) { // if the length of the arguments is 1
            return Arrays.asList("on", "off"); // return a list of the arguments
        }
        return null; // return null if the length of the arguments is not 1
    }
}
