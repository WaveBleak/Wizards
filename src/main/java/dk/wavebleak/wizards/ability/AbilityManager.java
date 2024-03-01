package dk.wavebleak.wizards.ability;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableItemNBT;
import dk.wavebleak.wizards.Wizards;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.Reflections;

import java.util.*;
import java.util.function.Function;

/**
 * The AbilityManager class is responsible for managing abilities in the game.
 * It provides methods to retrieve abilities based on their ID and execute certain actions for each ability.
 * It also listens to events and calls the corresponding methods in the abilities.
 */
public class AbilityManager implements Listener {

    public static List<Ability> abilities = new ArrayList<>();

    public static Ability fromID(int id) {
        return abilities.stream().filter(x -> x.id() == id).findAny().orElse(null);
    }

    public static Ability fromItem(ItemStack item) {
        if(item == null || item.getType() == Material.AIR || item.getAmount() == 0) return null;
        Integer _int = NBT.get(item, (Function<ReadableItemNBT, Integer>) nbt -> nbt.getInteger("id"));
        if(_int == null) return null;
        int id = _int;
        return fromID(id);
    }

    public AbilityManager() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    AbilityRunnable runnable = (ability) -> {
                        ability.onTick(player);
                    };

                    executePerAbility(player.getInventory(), runnable);
                }
            }
        }.runTaskTimer(Wizards.instance, 1, 1);

        Reflections reflections = new Reflections("dk.wavebleak.wizards");

        Set<Class<? extends Ability>> subTypes = reflections.getSubTypesOf(Ability.class);

        for (Class<? extends Ability> gemClass : subTypes) {
            try {
                Ability ability = gemClass.newInstance();
                abilities.add(ability);
            } catch (InstantiationException | IllegalAccessException e) {
                Wizards.instance.getLogger().warning(e.getMessage());
            }
        }

        for(Ability ability : abilities) {
            Wizards.instance.getLogger().info("TEST " + ability.name());
            ability.onStartup();
        }

    }


    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        ItemStack itemInHand = event.getItemInHand();

        AbilityRunnable runnable = (gem) -> event.setCancelled(true);

        executeIfAbility(itemInHand, runnable);
    }


    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        AbilityRunnable runnable = (gem) -> gem.onBreak(event);

        executePerAbility(event.getPlayer().getInventory(), runnable);
    }




    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) return;
        if(!(event.getEntity() instanceof LivingEntity)) return;
        Player attacker = (Player) event.getDamager();
        LivingEntity victim = (LivingEntity) event.getEntity();

        AbilityRunnable runnable = (gem) -> gem.onAttack(attacker, victim);

        executePerAbility(attacker.getInventory(), runnable);
    }


    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        switch (event.getAction()) {
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                ItemStack item = event.getPlayer().getItemInHand();

                AbilityRunnable runnable = (gem) -> gem.onRightClick(event.getPlayer());

                executeIfAbility(item, runnable);
        }
    }


    public void executePerAbility(Inventory inventory, AbilityRunnable runnable) {
        for(ItemStack item : inventory) {
            if(item == null) continue;
            executeIfAbility(item, runnable);
        }
    }

    public void executeIfAbility(ItemStack item, AbilityRunnable runnable) {
        if(item == null || item.getType() == Material.AIR || item.getAmount() == 0) return;
        abilities.stream().filter(ability -> {
            int id1 = ability.id();
            if(fromItem(item) == null) return false;
            int id2 = fromItem(item).id();

            return id1 == id2;
        }).findFirst().ifPresent(runnable::run);
    }
    
    
}
