package cn.blockmc.inventory;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import cn.blockmc.NMSItemStack;
import cn.blockmc.ZaoGun;

public class GunPartInventory {
	private static ZaoGun plugin;
	private Player player;
	private Inventory inventory;
	private HashMap<GunPartSlot, ItemStack> items = new HashMap<GunPartSlot, ItemStack>();

	public GunPartInventory(ItemStack item, Player player) {
		this.player = player;
		NMSItemStack nmsitem = NMSItemStack.asNMSItemStack(item);
		String scope = nmsitem.getNBT().getString("scope");
		if (scope != null) {
			items.put(GunPartSlot.SCOPE, plugin.getItem(scope));
		}

	}

	public void open() {

	}

	public static void init(ZaoGun plugin) {
		GunPartInventory.plugin = plugin;
	}

}
