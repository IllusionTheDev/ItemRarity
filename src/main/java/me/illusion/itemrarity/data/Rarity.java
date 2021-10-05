package me.illusion.itemrarity.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public class Rarity {

    private final String name;
    private final String lore;

    private final EnumSet<Material> set;
    private final Set<ItemStack> customItems;

    public boolean belongs(ItemStack item) {
        for(ItemStack customItem : customItems)
            if(item.isSimilar(customItem))
                return true;

        return belongs(item.getType());
    }

    public boolean belongs(Material material) {
        return set.contains(material);
    }

}
