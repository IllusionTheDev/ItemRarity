package me.illusion.itemrarity.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.illusion.itemrarity.ItemRarityPlugin;
import me.illusion.itemrarity.data.Rarity;
import me.illusion.utilities.item.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PacketHandler {

    private final ItemRarityPlugin main;

    public PacketHandler(ItemRarityPlugin main) {
        this.main = main;
        registerListener(main);
    }

    private void registerListener(ItemRarityPlugin main) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        // Format (in order)
        // int syncId, int revision, int slot, ItemStack stack

        manager.addPacketListener(new PacketAdapter(main, PacketType.Play.Server.SET_SLOT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();

                ItemStack item = packet.getItemModifier().read(0);

                packet.getItemModifier().write(0, replaceData(item));
            }
        });

        manager.addPacketListener(new PacketAdapter(main, PacketType.Play.Server.WINDOW_ITEMS) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();

                List<ItemStack> items = packet.getItemListModifier().readSafely(0);

                for (int index = 0; index < items.size(); index++)
                    items.set(index, replaceData(items.get(index)));

                packet.getItemListModifier().write(0, items);
            }
        });

    }

    /**
     * Using NBT stuff for compatibility reasons, can be updated to NMS but can break
     *
     * @param item - Bukkit itemstack
     * @return Itemstack with new lore
     */
    private ItemStack replaceData(ItemStack item) {
        if (item == null || item.getType().name().contains("AIR"))
            return item;

        ItemStack copy = item.clone();
        ItemMeta copyMeta = copy.getItemMeta();

        List<String> newLore = copyMeta.getLore();

        if (newLore == null)
            newLore = new ArrayList<>();

        newLore = removeLore(newLore);

        Rarity rarity = main.getRarityManager().getRarity(copy);
        newLore.add(rarity.getLore());
        //  System.out.println("Adding lore pre-colorization: " + newLore);
        copyMeta.setLore(ItemUtil.colorize(newLore));

        //System.out.println("Result lore: " + copyMeta.getLore());
        copy.setItemMeta(copyMeta);

        return copy;
    }

    private List<String> removeLore(List<String> original) {
        List<String> copy = new ArrayList<>(original);

        for (Rarity rarity : main.getRarityManager().getRarities()) {

            List<String> secondCopy = new ArrayList<>(copy);
            for (String line : copy)
                if (line.startsWith(ChatColor.translateAlternateColorCodes('&', rarity.getLore()))) {
                    System.out.println("removed " + line);
                    secondCopy.remove(line);
                }

            copy = secondCopy;
        }

        return copy;
    }
}
