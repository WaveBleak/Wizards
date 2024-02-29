package dk.wavebleak.wizards.ability;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public interface IAbility {
    /**
     * Retrieves the material type of the item.
     *
     * @return The material type of the item.
     */
    Material itemType();

    /**
     * Retrieves the name of the ability.
     *
     * @return The name of the ability as a string.
     */
    String name();

    /**
     * Get the lore for the item.
     *
     * @return An array of strings representing the lore for the item.
     */
    String[] lore();

    /**
     * A unique id representing the item
     * MUST NOT BE THE SAME AS ANY OTHER ID
     * @return the unique id
     */
    int id();

    /**
     * Performs an action when the player right-clicks.
     *
     * @param player The player who right-clicked.
     */
    void onRightClick(Player player);

    /**
     * Executes the onTick method for the given player. Called after the start method
     *
     * @param player The player for which to execute the onTick method.
     */
    void onTick(Player player);

    /**
     * Represents a method that is called when a player attacks a living entity.
     *
     * @param player The attacking player
     * @param victim The entity being attacked
     */
    void onAttack(Player player, LivingEntity victim);

    /**
     * Called when a player breaks a block with the ability.
     *
     * @param event The BlockBreakEvent representing the block break action
     */
    void onBreak(BlockBreakEvent event);

    /**
     * Called once right before the first tick is called
     */
    void onStartup();
}
