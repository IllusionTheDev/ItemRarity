package me.illusion.utilities.item;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ItemUtil {

    private ItemUtil() {

    }

    public static void give(Player player, ItemStack item) {
        PlayerInventory inv = player.getInventory();

        for (Map.Entry<Integer, ItemStack> entry : inv.addItem(item).entrySet()) {
            ItemStack copy = entry.getValue().clone();
            item.setAmount(entry.getKey());
            player.getWorld().dropItemNaturally(player.getLocation(), copy);
        }
    }

    /**
     * Adds lines into item lore
     *
     * @param item    - Item to add to
     * @param newLore - Lines to add
     */
    public static void addLore(ItemStack item, List<String> newLore) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        if (lore == null)
            lore = new ArrayList<>();

        lore.addAll(newLore);

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    /**
     * Colorizes a StringList
     *
     * @param in - The input string list
     * @return colorized list
     */
    public static List<String> colorize(List<String> in) {
        List<String> val = new ArrayList<>();

        for (String s : in)
            val.add(ChatColor.translateAlternateColorCodes('&', s));

        return val;
    }
}
