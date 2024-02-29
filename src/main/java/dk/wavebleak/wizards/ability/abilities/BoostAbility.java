package dk.wavebleak.wizards.ability.abilities;

import dk.wavebleak.wizards.Wizards;
import dk.wavebleak.wizards.ability.Ability;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class BoostAbility extends Ability {
    @Override
    public Material itemType() {
        return Material.FEATHER;
    }

    @Override
    public String name() {
        return "&f&lBoost Ability";
    }

    @Override
    public String[] lore() {
        return new String[] {"&fRight click for at booste dig fremad!"};
    }

    @Override
    public int id() {
        return 0;
    }

    @Override
    public void onRightClick(Player player) {
        removeItem(player);
        player.setVelocity(player.getLocation().getDirection().multiply(2));
    }

    @Override
    public void onTick(Player player) {

    }

    @Override
    public void onAttack(Player player, LivingEntity victim) {

    }

    @Override
    public void onBreak(BlockBreakEvent event) {

    }

    @Override
    public void onStartup() {
        Wizards.instance.getLogger().info("Boost ability registered!");
    }
}
