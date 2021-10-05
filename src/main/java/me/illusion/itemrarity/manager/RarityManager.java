package me.illusion.itemrarity.manager;

import me.illusion.itemrarity.ItemRarityPlugin;
import me.illusion.itemrarity.data.Rarity;
import me.illusion.utilities.item.ItemBuilder;
import me.illusion.utilities.storage.YMLBase;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RarityManager {

    private final Set<Rarity> rarities = new HashSet<>();
    private Rarity defaultRarity;


    public RarityManager(ItemRarityPlugin main) {
        load(main);
    }

    public Rarity getRarity(ItemStack item) {
        for(Rarity rarity : rarities)
            if(rarity.belongs(item))
                return rarity;
            return defaultRarity;
    }

    public Rarity getRarity(String rarityName) {
        for(Rarity rarity : rarities)
            if(rarity.getName().equalsIgnoreCase(rarityName))
                return rarity;

        return defaultRarity;
    }

    public Set<Rarity> getRarities() {
        return rarities;
    }

    private void load(ItemRarityPlugin main) {
        FileConfiguration config = new YMLBase(main, "rarities.yml").getConfiguration();

        for(String key : config.getKeys(false)) {
            if(!config.isConfigurationSection(key))
                continue;

            boolean hasCustomItems = config.isConfigurationSection(key + ".custom-items");

            String lore = config.getString(key + ".lore");
            List<String> materials = config.getStringList(key + ".items");
            Set<ItemStack> customItems = new HashSet<>();
            if(hasCustomItems) {
                ConfigurationSection section = config.getConfigurationSection(key + ".custom-items");
                for(String itemKey : section.getKeys(false)) {
                    customItems.add(ItemBuilder.fromSection(section.getConfigurationSection(itemKey)));
                }
            }

            EnumSet<Material> set = EnumSet.noneOf(Material.class);

            for(String name : materials)
                set.add(Material.matchMaterial(name));

            Rarity rarity = new Rarity(key, lore, set, customItems);

            rarities.add(rarity);
        }

        String defaultRarity = config.getString("default");
        this.defaultRarity = getRarity(defaultRarity);
    }

}
