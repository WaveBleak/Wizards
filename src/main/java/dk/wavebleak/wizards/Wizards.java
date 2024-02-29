package dk.wavebleak.wizards;

import dk.wavebleak.wizards.ability.AbilityManager;
import dk.wavebleak.wizards.commands.GiveAbilityCommand;
import dk.wavebleak.wizards.commands.GiveAbilityCompletion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Wizards extends JavaPlugin {


    public static Wizards instance;
    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new AbilityManager(), this);

        getCommand("giveability").setExecutor(new GiveAbilityCommand());
        getCommand("giveability").setTabCompleter(new GiveAbilityCompletion());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
