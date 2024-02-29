package dk.wavebleak.wizards.commands;

import dk.wavebleak.wizards.ability.Ability;
import dk.wavebleak.wizards.ability.AbilityManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveAbilityCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if(!player.isOp()) return false;

        if(strings.length == 0) return false;
        try {
            Ability ability = AbilityManager.fromID(Integer.parseInt(strings[0]));
            player.getInventory().addItem(ability.getAsItem());

            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
