package cn.blockmc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import cn.blockmc.inventory.GunPartInventory;

public class ZaoGun extends JavaPlugin implements Listener {
	public Map<String, ItemStack> scopes = new HashMap<String, ItemStack>();
	public Map<String, Integer> ints = new HashMap<String, Integer>();
	public Map<String, String> strings = new HashMap<String, String>();
	public Map<String, Boolean> booleans = new HashMap<String, Boolean>();

	@Override
	public void onEnable() {
		this.getLogger().info("Gun started");
		this.getServer().getPluginManager().registerEvents(this, this);
		this.loadWeapons(null);
//		this.loadParts(null);
		GunPartInventory.init(this);

	}

	public void PR(String str) {
		this.getLogger().info(str);
	}

	@EventHandler
	public void c(PlayerInteractEntityEvent e) {
		ItemStack item = new ItemStack(Material.EGG);
		NMSItemStack nmsitem = NMSItemStack.asNMSItemStack(item);
		NBTComponent nbt = nmsitem.getNBT();
		nbt.setInt("s", 1);
		PR(nbt.getInt("s") + "");
		e.getPlayer().getInventory().addItem(nmsitem.asNewItemStack());

	}

	@EventHandler
	public void cc(PlayerMoveEvent e) {
	}

	@EventHandler
	public void playerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		PR("fuc");
		// NBTTagCompound n = (NBTTagCompound)
		// NBTUtils.getNBT(NBTUtils.getNMSItemStack(item));
		// ItemStack item = NBTUtils.getNMSItemStack(item);
		// PR(n.getClass().getName());
		new GunPartInventory(null, p);

	}

	public void openGunGUI(PlayerInteractEvent e) {

	}

	public void loadParts(Player p) {
		String path = this.getDataFolder() + "/parts";
		File tag = new File(path);
		// File[] filelist = tag.listFiles();
		String[] species = new String[] { "defaultScopes.yml","Barrel.yml" };
		for (int i = 0; i < species.length; i++) {
			String spec = species[i];
			File dFile = new File(tag, spec);
			PR(dFile.getAbsolutePath());
			if (!dFile.exists()) {
				dFile = grabDefaults(tag, spec);
				try {
					dFile.createNewFile();
				} catch (IOException e) {
				}
				PR("add Default "+dFile.getName());
			}
			fillHashMaps(loadConfig(dFile, p),dFile.getName());
			
		}

	}

	public void loadWeapons(Player p) {
		PR("load weapon");
		String path = this.getDataFolder() + "/weapons";
		PR(path);
		File tag = new File(path);
		PR(tag.getAbsolutePath());
		File[] filelist = tag.listFiles();
		if (filelist == null || filelist.length == 0) {
			String[] specials = { "defaultScopes.yml" };
			String[] arrayOfString1;
			int lenth = (arrayOfString1 = specials).length;
			for (int i = 0; i < lenth; i++) {
				String spec = arrayOfString1[i];
				PR(spec);
				File dFile = grabDefaults(tag, spec);
				PR(dFile.getAbsolutePath());
				if (dFile != null) {
					try {
						dFile.createNewFile();
					} catch (IOException localIOException) {
					}
				}
			}
			filelist = tag.listFiles();
			PR("DefaultAdd");
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
				fillHashMaps(loadConfig(file, p), null);
			}
		}
		// completeList();
	}

	// public void loadScopes(Player p) {
	// String path = this.getDataFolder() + "/weapons";
	// File tag = new File(path);
	// File[] filelist = tag.listFiles();
	//
	// if (filelist == null) {
	// System.out.print("[CrackShot] No weapons were loaded!");
	// return;
	// }
	// }

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

	public void fillHashMaps(YamlConfiguration config, String type) {
		for (String string : config.getKeys(true)) {
			Object obj = config.get(string);
			if ((obj instanceof Boolean)) {
				booleans.put(string, (Boolean) obj);
			} else if ((obj instanceof Integer)) {
				ints.put(string, (Integer) obj);
			} else if ((obj instanceof String)) {
				obj = ((String) obj).replaceAll("&", "§");
				strings.put(string, (String) obj);
			}
		}
		for(String parent:config.getKeys(false)){
			if(type!=null){
				strings.put(parent, type);
			}
		}

	}

	private File grabDefaults(File directories, String path) {
		File file = new File(directories,path);
		PR(file.getAbsolutePath());

//		File file = new File(getDataFolder() + tag +"/"+ path);
//		File directories = new File(getDataFolder() + tag);
		if (!directories.exists()) {
			directories.mkdirs();
		}
//		InputStream inputStream = this.getClass().getResourceAsStream("/" + path);
		InputStream inputStream = this.getClass().getResourceAsStream("/"+path);
//		PR(inputStream.toString());
		if (inputStream == null) {
			PR("s");
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
		Material material = Material.valueOf(strings.get(parent + "Item_Information.Item_Material"));
		ItemStack item = new ItemStack(material);
		return null;
	}
}
