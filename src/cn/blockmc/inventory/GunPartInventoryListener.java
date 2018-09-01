package cn.blockmc.inventory;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
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
			// e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			GunPartSlot slot = GunPartSlot.getSlot(e.getSlot());
			ClickType clicktype = e.getClick();
			ItemStack current = e.getCurrentItem();
			ItemStack cursor = e.getCursor();
			if (e.getClickedInventory() == null) {
				return;
			}
			if (e.getClickedInventory().equals(inv)) {
				if (slot == null) {
					e.setCancelled(true);
					return;
				}
				//TODO rewrite 
				if (cursor.getType()!=Material.AIR) {
					String parent = NMSItemStack.asNMSItemStack(cursor).getNBT().getString("parent");
					String typenode = plugin.strings.get(parent);
					if (typenode != null && typenode.equals(slot.getTypeNode())) {
						String gunparent = NMSItemStack.asNMSItemStack(ginv.getGun()).getNBT().getString("parent");
						List<String> allowparts = plugin.stringlists.get(gunparent+".AllowParts");
						if (allowparts.contains(parent)) {
							return;
						}
					}
					e.setCancelled(true);
					return;
				}

			} else {
				if (clicktype == ClickType.SHIFT_LEFT || clicktype == ClickType.SHIFT_RIGHT
						|| clicktype == ClickType.RIGHT) {
					if (current != null && current.getType() != Material.AIR) {
						ItemStack newcurrent = ginv.equipPart(current);
						if (newcurrent != null) {
							p.getInventory().setItem(e.getSlot(), newcurrent);
						}
						p.updateInventory();
						e.setCancelled(true);
					}
				}
				/////////////////////////
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
