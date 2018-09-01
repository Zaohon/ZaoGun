package cn.blockmc.inventory;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import cn.blockmc.NMSItemStack;
import cn.blockmc.ZaoGun;

public class GunPartInventory {
	private static ZaoGun plugin;
	private ItemStack gun;
	private Player player;
	private Inventory inventory;
	// private HashMap<GunPartSlot, ItemStack> items = new HashMap<GunPartSlot,
	// ItemStack>();

	public GunPartInventory(ItemStack item, Player player) {
		this.gun = item;
		this.player = player;
		inventory = Bukkit.createInventory(null, 54);
		NMSItemStack nmsitem = NMSItemStack.asNMSItemStack(item);

		for (GunPartSlot slot : GunPartSlot.values()) {
			String typenode = slot.getTypeNode();
			String typeparent = nmsitem.getNBT().getString(typenode);
			if (!typeparent .equals("")) {
				inventory.setItem(slot.getInventoryID(), plugin.getItem(typeparent));
			}
		}
		this.open();

	}

	public ItemStack equipPart(ItemStack part) {
		String partnode = NMSItemStack.asNMSItemStack(part).getNBT().getString("parent");
		List<String> allowparts = plugin.stringlists.get(NMSItemStack.asNMSItemStack(gun).getNBT().getString("parent")+".AllowParts");
		if (allowparts!=null&&allowparts.contains(partnode)) {
			String typenode = plugin.strings.get(partnode);
			GunPartSlot slot = GunPartSlot.getByType(typenode);
			ItemStack currentitem = inventory.getItem(slot.getInventoryID());
			inventory.setItem(slot.getInventoryID(), part);
			player.updateInventory();
			return currentitem == null ? new ItemStack(Material.AIR) : currentitem;
		}
		return null;
	}

	public void open() {
		player.openInventory(inventory);
		GunPartInventoryListener.addNewInvListener(this);
	}

	public void destroy() {
		player.closeInventory();
		inventory.clear();
	}

	public ZaoGun getPlugin() {
		return plugin;
	}

	public ItemStack getGun() {
		return gun;
	}

	public Player getPlayer() {
		return this.player;
	}

	// public ItemStack getSlot(GunPartSlot slot) {
	// return items.get(slot);
	// }
	public Inventory getInventory() {
		return inventory;
	}

	public static void init(ZaoGun plugin) {
		GunPartInventory.plugin = plugin;
	}

}
