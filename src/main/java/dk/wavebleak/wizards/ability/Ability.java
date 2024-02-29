package dk.wavebleak.wizards.ability;

import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class Ability implements IAbility{

    public ItemStack getAsItem() {
        ItemStack item = new ItemStack(itemType());
        ItemMeta meta = item.getItemMeta();

        if(meta == null) return new ItemStack(Material.STONE);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name()));

        List<String> lore = new ArrayList<>();
        for(String line : lore()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        meta.setLore(lore);

        item.setItemMeta(meta);

        NBT.modify(item, nbt -> {
            nbt.setInteger("id", id());
        });

        return item;
    }

    protected void removeItem(Player player) {
        ItemStack item = getAsItem();
        item.setAmount(1);
        player.getInventory().removeItem(item);
    }
}
