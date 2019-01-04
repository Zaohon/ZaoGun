package cn.blockmc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import cn.blockmc.inventory.GunPartInventory;
import net.minecraft.server.v1_13_R1.NBTTagCompound;

public class ZaoGun extends JavaPlugin implements Listener {
	public static final String GUNNODE = "Gun";
	public static final String SCOPENODE = "Scope";
	public static final String BARRELNODE = "Barrel";
	public static final String BULLETNODE = "BULLET";

	public Map<String, List<String>> stringlists = new HashMap<String, List<String>>();
	public Map<String, Integer> ints = new HashMap<String, Integer>();
	public Map<String, String> strings = new HashMap<String, String>();
	public Map<String, Boolean> booleans = new HashMap<String, Boolean>();

	@Override
	public void onEnable() {
		this.getLogger().info("Gun started");
		this.getServer().getPluginManager().registerEvents(this, this);
		this.loadWeapons(null);
		this.loadParts(null);
		GunPartInventory.init(this);

	}

	public void PR(String str) {
		this.getLogger().info(str);
	}

	@EventHandler
	public void c(PlayerInteractEntityEvent e) {
		// ItemStack item = new ItemStack(Material.EGG);
		// NMSItemStack nmsitem = NMSItemStack.asNMSItemStack(item);
		// NBTComponent nbt = nmsitem.getNBT();
		// nbt.setInt("s", 1);
		// PR(nbt.getInt("s") + "");
		e.getPlayer().getInventory().addItem(getItem("M4A1"));

	}

	@EventHandler
	public void ch(AsyncPlayerChatEvent e) {
		String parent = e.getMessage();
		e.getPlayer().getInventory().addItem(getItem(parent, 10));
	}
	//
	// @EventHandler
	// public void cc(PlayerMoveEvent e) {
	// Player p = e.getPlayer();
	// // ItemStack item = new ItemStack(Material.DIAMOND_AXE);
	// // NMSItemStack nmsitem = NMSItemStack.asNMSItemStack(item);
	// // NBTComponent nbt = nmsitem.getNBT().setString("scope", "4xScope");
	// // nmsitem.setNBT(nbt);
	// // p.getInventory().addItem(nmsitem.asNewItemStack());
	//// NMSItemStack nms =
	// NMSItemStack.asNMSItemStack(e.getPlayer().getInventory().getItemInMainHand());
	//// NBTTagCompound n = (NBTTagCompound) nms.getNBT().getTag();
	//// n.getKeys().forEach(key -> {
	//// Bukkit.broadcastMessage(key + " " + n.get(key));
	//// });
	// }

	public void loadParts(Player p) {
		String path = this.getDataFolder() + "/parts";
		File tag = new File(path);
		File[] listfile = tag.listFiles();
		if (listfile == null || listfile.length == 0) {
			String[] species = new String[] { "scopes.yml", "barrels.yml", "bullets.yml" };
			for (int i = 0; i < species.length; i++) {
				String spec = species[i];
				File dFile = grabDefaults(tag, spec);
				try {
					dFile.createNewFile();
				} catch (IOException e) {
				}
				PR("add Default " + dFile.getName().substring(0, dFile.getName().length() - 4));
			}
			listfile = tag.listFiles();
		}
		for (int i = 0; i < listfile.length; i++) {
			File file = listfile[i];
			this.fillHashMaps(loadConfig(file, p), file.getName().substring(0, file.getName().length() - 5));

		}
	}

	public void loadWeapons(Player p) {
		String path = this.getDataFolder() + "/weapons";
		File tag = new File(path);
		File[] filelist = tag.listFiles();
		if (filelist == null || filelist.length == 0) {
			String[] specials = { "defaultWeapons.yml" };
			String[] arrayOfString1;
			int lenth = (arrayOfString1 = specials).length;
			for (int i = 0; i < lenth; i++) {
				String spec = arrayOfString1[i];
				File dFile = grabDefaults(tag, spec);
				if (dFile != null) {
					try {
						dFile.createNewFile();
					} catch (IOException localIOException) {
					}
				}
			}
			filelist = tag.listFiles();
			PR("Default Weapon Add");
		}
		if (filelist == null) {
			System.out.print("[CrackShot] No weapons were loaded!");
			return;
		}
		File[] arrayOfFile1;
		int lenth = (arrayOfFile1 = filelist).length;
		for (int i = 0; i < lenth; i++) {
			File file = arrayOfFile1[i];
			if (file.getName().endsWith(".yml")) {
				fillHashMaps(loadConfig(file, p), GUNNODE);
			}
		}
		// completeList();
	}

	public YamlConfiguration loadConfig(File file, Player player) {
		YamlConfiguration config = new YamlConfiguration();
		try {
			config.load(file);
		} catch (FileNotFoundException localFileNotFoundException) {
		} catch (IOException ex) {
			if (player != null) {
				// player.sendMessage(this.heading + "The file '" +
				// file.getName() + "' could not be loaded.");
			}
			ex.printStackTrace();
		} catch (InvalidConfigurationException ex) {
			if (player != null) {
				// player.getWorld().playSound(player.getLocation(),
				// SoundManager.get("VILLAGER_HAGGLE"), 1.0F, 1.0F);
				// player.sendMessage(this.heading + "The file '" +
				// file.getName() + "' is incorrectly configured. View the error
				// report in the console and fix it!");
			}
			ex.printStackTrace();
		}
		return config;
	}

	@SuppressWarnings("unchecked")
	public void fillHashMaps(YamlConfiguration config, String type) {
		for (String string : config.getKeys(true)) {
			Object obj = config.get(string);
			PR(string + " " + obj);
			if ((obj instanceof Boolean)) {
				booleans.put(string, (Boolean) obj);
			} else if ((obj instanceof Integer)) {
				ints.put(string, (Integer) obj);
			} else if ((obj instanceof String)) {
				obj = ((String) obj).replaceAll("&", "§");
				strings.put(string, (String) obj);
			} else if (obj instanceof List<?>) {
				stringlists.put(string, (List<String>) obj);
			}
			Bukkit.broadcastMessage(obj + "");
		}
		for (String parent : config.getKeys(false)) {
			if (type != null) {
				strings.put(parent, type);
			}
		}

	}

	private File grabDefaults(File directories, String path) {
		File file = new File(directories, path);
		PR(file.getAbsolutePath());
		if (!directories.exists()) {
			directories.mkdirs();
		}
		InputStream inputStream = this.getClass().getResourceAsStream("/resources/" + path);
		if (inputStream == null) {
			return null;
		}
		try {
			FileOutputStream output = new FileOutputStream(file);
			byte[] buffer = new byte['?'];

			int read = 0;
			while ((read = inputStream.read(buffer)) > 0) {
				output.write(buffer, 0, read);
			}
			inputStream.close();
			output.close();

			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ItemStack getItem(String parent) {
		return getItem(parent, 1);
	}

	public ItemStack getItem(String parent, int amount) {
		Bukkit.broadcastMessage("try to get " + parent);
		Material material = Material.valueOf(strings.get(parent + ".Item_Information.Item_Material"));
		ItemStack item = new ItemStack(material, amount);
		setDisplayName(item, strings.get(parent + ".Item_Information.Item_Name"));
		setLore(item, strings.get(parent + ".Item_Information.Item_Lore"));

		NMSItemStack nmsitem = NMSItemStack.asNMSItemStack(item);
		NBTComponent nbt = nmsitem.getNBT();
		nbt.setString("parent", parent);
		// default part set
		List<String> parts = stringlists.get(parent + ".DefaultParts");
		if (parts != null) {
			for (String part : parts) {
				nbt.setString(strings.get(part), part);
			}
		}
		nmsitem.setMaxStack(ints.getOrDefault(parent + ".Item_Information.Item_Max_Stack", 1));
		nmsitem.setNBT(nbt);
		return nmsitem.asNewItemStack();
	}

	public ItemStack setDisplayName(ItemStack item, String name) {
		ItemMeta m = item.getItemMeta();
		m.setDisplayName(name);
		item.setItemMeta(m);
		return item;
	}

	public ItemStack setLore(ItemStack item, String... lore) {
		ItemMeta m = item.getItemMeta();
		m.setLore(Arrays.asList(lore));
		item.setItemMeta(m);
		return item;
	}

	@EventHandler
	public void interact(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItem();
		if (item == null || item.getType() == Material.AIR) {
			return;
		}
		String parent = NMSItemStack.asNMSItemStack(item).getNBT().getString("parent");
		if (parent.equals("")) {
			return;
		}
		String type = strings.get(parent);
		if (type.equals(GUNNODE)) {
			shootOnce(player,parent);
		}

	}
	public boolean shootOnce(Player player,String gun) {
		String bullet = strings.getOrDefault(gun+".Weapon_Information.Bullet", "43mm_Bullet");
		int damage = ints.getOrDefault(gun+".Weapon_Information.Shoot_Damage", 5);
		int offset_damage = ints.getOrDefault(gun+".Weapon_Information.Offset_Damage",0);
		int shoot_speed = ints.getOrDefault(gun+".Weapon_Information.Shoot_Speed",60);
		int shoot_Range = ints.getOrDefault(gun+".Weapon_Information.Shoot_Range",100);
		int recoil = ints.getOrDefault(gun+".Weapon_Information.Recoil", 0);
		
		Projectile pro = player.launchProjectile(Snowball.class);
		pro.setVelocity(player.getVelocity());
		
		return false;
	}
}
