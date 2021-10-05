package me.illusion.itemrarity;

import lombok.Getter;
import me.illusion.itemrarity.listener.PacketHandler;
import me.illusion.itemrarity.manager.RarityManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ItemRarityPlugin extends JavaPlugin {

    private RarityManager rarityManager;

    @Override
    public void onEnable() {
        rarityManager = new RarityManager(this);
        new PacketHandler(this);
    }
}
