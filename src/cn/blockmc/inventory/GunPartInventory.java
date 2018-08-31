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
	private ItemStack gun;
	private Player player;
	private Inventory inventory;
//	private HashMap<GunPartSlot, ItemStack> items = new HashMap<GunPartSlot, ItemStack>();

	public GunPartInventory(ItemStack item, Player player) {
		this.gun = item;
		this.player = player;
		inventory = Bukkit.createInventory(null, 54);
		NMSItemStack nmsitem = NMSItemStack.asNMSItemStack(item);
		
		for(GunPartSlot slot:GunPartSlot.values()){
			String typenode = slot.getTypeNode();
			String scope = nmsitem.getNBT().getString(typenode);
			if(scope!=""){
				inventory.setItem(slot.getInventoryID(), plugin.getItem(scope));
			}
		}
		this.open();

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
	public Player getPlayer(){
		return this.player;
	}

//	public ItemStack getSlot(GunPartSlot slot) {
//		return items.get(slot);
//	}
	public Inventory getInventory(){
		return inventory;
	}

	public static void init(ZaoGun plugin) {
		GunPartInventory.plugin = plugin;
	}

}
