package cn.blockmc.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import cn.blockmc.NBTComponent;
import cn.blockmc.NMSItemStack;
import cn.blockmc.ZaoGun;

public class GunPartInventoryListener implements Listener {
	private ZaoGun plugin;
	private GunPartInventory ginv;
	private Inventory inv;

	private GunPartInventoryListener(GunPartInventory ginv) {
		this.ginv = ginv;
		this.plugin = ginv.getPlugin();
		this.inv = ginv.getInventory();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onInvClicked(InventoryClickEvent e) {
		if (e.getInventory().equals(inv)) {
			e.setCancelled(true);
			GunPartSlot slot = GunPartSlot.getSlot(e.getSlot());
			if (slot != null) {
				
			}
		}
	}

	@EventHandler
	public void onPlayerClose(InventoryCloseEvent e) {
		if (e.getInventory().equals(inv)) {
			NMSItemStack nmsitem = NMSItemStack.asNMSItemStack(ginv.getGun());
			NBTComponent nbt = nmsitem.getNBT();
			for (GunPartSlot slot : GunPartSlot.values()) {
				String typenode = slot.getTypeNode();
				ItemStack item = inv.getItem(slot.getInventoryID());
				if (item == null) {
					nbt.remove(typenode);
				} else {
					String node = NMSItemStack.asNMSItemStack(item).getNBT().getString("parent");
					nbt.setString(typenode, node);
				}
			}
			nmsitem.setNBT(nbt);
			ginv.getPlayer().getInventory().setItemInMainHand(nmsitem.asNewItemStack());
			this.destroy();
		}
	}

	private void destroy() {
		HandlerList.unregisterAll(this);
		ginv.destroy();
	}

	public static void addNewInvListener(GunPartInventory ginv) {
		GunPartInventoryListener lis = new GunPartInventoryListener(ginv);
	}

}
