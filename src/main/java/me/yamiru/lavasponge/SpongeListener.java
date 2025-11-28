package me.yamiru.lavasponge;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SpongeListener implements Listener {

    private final LavaSponge plugin;
    private boolean enabled;
    private Set<String> enabledWorlds;
    private Set<String> disabledWorlds;
    private int radius;
    private boolean wetSponge;
    private int maxBlocks;

    public SpongeListener(LavaSponge plugin) {
        this.plugin = plugin;
        reloadSettings();
    }

    public void reloadSettings() {
        enabled = plugin.getConfig().getBoolean("enabled", true);
        
        List<String> ew = plugin.getConfig().getStringList("enabled-worlds");
        List<String> dw = plugin.getConfig().getStringList("disabled-worlds");
        
        enabledWorlds = ew.isEmpty() ? null : new HashSet<>(ew);
        disabledWorlds = dw.isEmpty() ? null : new HashSet<>(dw);
        
        radius = plugin.getConfig().getInt("absorption.radius", 2);
        wetSponge = plugin.getConfig().getBoolean("absorption.wet-sponge", false);
        maxBlocks = plugin.getConfig().getInt("absorption.max-blocks", 65);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!enabled || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.SPONGE) return;

        Player player = event.getPlayer();
        if (!player.hasPermission("lavasponge.use")) return;

        if (!canUseInWorld(player.getWorld().getName())) return;

        Block clicked = event.getClickedBlock();
        if (clicked == null || !hasLavaNearby(clicked.getLocation())) return;

        event.setCancelled(true);

        int removed = removeLava(clicked.getLocation());
        if (removed > 0) {
            int amount = item.getAmount();
            
            if (amount > 1) {
                // Ak je viac kusov, zníž o 1 a pridaj wet sponge
                item.setAmount(amount - 1);
                if (wetSponge) {
                    ItemStack wetSpongeItem = new ItemStack(Material.WET_SPONGE, 1);
                    player.getInventory().addItem(wetSpongeItem);
                }
            } else {
                // Ak je len 1 kus, zmeň ho na wet sponge alebo odstráň
                if (wetSponge) {
                    item.setType(Material.WET_SPONGE);
                } else {
                    item.setAmount(0);
                }
            }
            
            player.sendMessage("§6Absorbed §e" + removed + " §6lava blocks!");
        }
    }

    private boolean canUseInWorld(String worldName) {
        if (disabledWorlds != null && disabledWorlds.contains(worldName)) return false;
        return enabledWorlds == null || enabledWorlds.contains(worldName);
    }

    private boolean hasLavaNearby(Location loc) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (loc.clone().add(x, y, z).getBlock().getType() == Material.LAVA) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int removeLava(Location center) {
        Set<Block> lavaBlocks = new HashSet<>();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block block = center.clone().add(x, y, z).getBlock();
                    if (block.getType() == Material.LAVA && lavaBlocks.size() < maxBlocks) {
                        lavaBlocks.add(block);
                    }
                }
            }
        }

        lavaBlocks.forEach(block -> block.setType(Material.AIR));
        return lavaBlocks.size();
    }
}
