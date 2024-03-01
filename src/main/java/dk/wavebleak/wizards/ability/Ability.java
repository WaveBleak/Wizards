package dk.wavebleak.wizards.ability;

import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
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

    protected boolean isHoldingGem(Player player) {
        return id() == AbilityManager.fromItem(player.getItemInHand()).id();
    }

    protected boolean isInSpawnArea(Player player) {
        RegionContainer container =  WorldGuardPlugin.inst().getRegionContainer();;
        ApplicableRegionSet set = container.get(player.getWorld()).getApplicableRegions(player.getLocation());
        for(ProtectedRegion pr : set) if (pr.getId().toLowerCase().contains("spawn")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[!] Dette item kan ikke benyttes i spawn."));
            return true;
        }
        return false;
    }

    protected void removeItem(Player player) {
        ItemStack item = getAsItem();
        item.setAmount(1);
        player.getInventory().removeItem(item);
    }
}
