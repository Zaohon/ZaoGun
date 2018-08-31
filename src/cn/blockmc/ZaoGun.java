package cn.blockmc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_13_R1.NBTTagCompound;

public class ZaoGun extends JavaPlugin implements Listener {
	public Map<String,ItemStack> scopes =new HashMap<String, ItemStack>();
	@Override
	public void onEnable() {
		this.getLogger().info("Gun started");
		this.getServer().getPluginManager().registerEvents(this, this);

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
		PR(nbt.getInt("s")+"");
		e.getPlayer().getInventory().addItem(nmsitem.asNewItemStack());

	}
	@EventHandler
	public void cc(PlayerMoveEvent e){
	}
	@EventHandler
	public void playerInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		PR("fuc");
//		NBTTagCompound n = (NBTTagCompound) NBTUtils.getNBT(NBTUtils.getNMSItemStack(item));
//		ItemStack item = NBTUtils.getNMSItemStack(item);
//		PR(n.getClass().getName());
		
	}
	public void openGunGUI(PlayerInteractEvent e){
		Player p = e.getPlayer();
		ItemStack gun = e.getItem();
		Inventory gui = Bukkit.createInventory(null, 54);
		int scope = NBTUtils.getInt(gun, "scope", 0);
		if(scope!=0){
			
		}
	}
	public void loadScopes(Player p){
		String path = this.getDataFolder()+"/weapons";
		File tag = new File(path);
		File[] filelist = tag.listFiles();
		if(filelist==null||filelist.length==0){
			String[] specials = { "defaultScopes" };
			String[] arrayOfString1;
		      int i = (arrayOfString1 = specials).length;
		      for (int str1 = 0; str1 < i; str1++)
		      {
		       String spec = arrayOfString1[str1];
		        File dFile = grabDefaults(spec);
		        if (dFile != null) {
		          try
		          {
		            dFile.createNewFile();
		          }
		          catch (IOException localIOException) {}
		        }
		      }
		      filelist = tag.listFiles();
		      PR("DefaultAdd");
		}
		if (filelist == null)
	    {
	      System.out.print("[CrackShot] No weapons were loaded!"); return;
	    }
//	    File[] arrayOfFile1;
//	    int str1 = (arrayOfFile1 = filelist).length;
//	    for (int spec = 0; spec < str1; spec++)
//	    {
//	      File file = arrayOfFile1[spec];
//	      if (file.getName().endsWith(".yml"))
//	      {
//	        this.plugin.weaponConfig = loadConfig(file, player);
//	        this.plugin.fillHashMaps(this.plugin.weaponConfig);
//	      }
//	    }
//	    completeList();
	}

	private File grabDefaults(String defaultfile) {

	    File file = new File(getDataFolder() + "/weapons/" + defaultfile);
	    File directories = new File(getDataFolder() + "/weapons");
	    if (!directories.exists()) {
	      directories.mkdirs();
	    }
	    InputStream inputStream =this.getClass().getResourceAsStream("/" + defaultfile);
	    if (inputStream == null) {
	      return null;
	    }
	    try
	    {
	      FileOutputStream output = new FileOutputStream(file);
	      byte[] buffer = new byte['?'];
	      
	      int read = 0;
	      while ((read = inputStream.read(buffer)) > 0) {
	        output.write(buffer, 0, read);
	      }
	      inputStream.close();
	      output.close();
	      
	      return file;
	    }
	    catch (Exception localException) {}
	    return null;
	}
}
