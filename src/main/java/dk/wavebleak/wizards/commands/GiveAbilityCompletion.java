package dk.wavebleak.wizards.commands;

import dk.wavebleak.wizards.ability.AbilityManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class GiveAbilityCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1) {
            List<String> ids = new ArrayList<>();
            AbilityManager.abilities.forEach(ability -> {
                ids.add(String.valueOf(ability.id()));
            });

            return ids;
        }
        return new ArrayList<>();
    }
}
