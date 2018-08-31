package cn.blockmc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.bukkit.Bukkit;

import net.minecraft.server.v1_13_R1.NBTTagCompound;

public class NBTComponent {
	private Object nbtcompount;
	// private Map<Object, Object> map;

	public NBTComponent(Object nbtcompount) {
		this.nbtcompount = nbtcompount;
		// this.map = (Map<Object, Object>) NMSUtils.getField("map",
		// this.nbtcompount);
	}

	// public Map<?, ?> getMap() {
	// return map;
	// }
	public Object getTag() {
		return nbtcompount;
	}

	public NBTComponent setString(String k, String v) {
		NMSUtils.invokeMethod("setString", nbtcompount, new Object[] { k, v });
		return this;
	}

	public String getString(String k) {
		return (String) NMSUtils.invokeMethod("getString", nbtcompount, new Object[] { k });
	}

	public NBTComponent setInt(String k, int v) {

//		NBTTagCompound n = (NBTTagCompound) nbtcompount;

		// Method[] ms = nbtcompount.getClass().getDeclaredMethods();
		// for(int i=0;i<ms.length;i++){
		// Method m = ms[i];
		// Bukkit.broadcastMessage(m.getName());
		// Class[] cs = m.getParameterTypes();
		// for(int j=0;j<cs.length;j++){
		// Bukkit.broadcastMessage(cs[j].getName());
		// }
		// Bukkit.broadcastMessage(" ");
		// }
		try {
			NMSUtils.getMethod("setInt", nbtcompount.getClass(), new Class<?>[] { String.class, int.class })
					.invoke(nbtcompount, new Object[] { k, v });
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return this;
	}

	public int getInt(String k) {
		return (int) NMSUtils.invokeMethod("getInt", nbtcompount, new Object[] { k });
	}

	public NBTComponent setBoolean(String k, Boolean v) {
		try {
			NMSUtils.getMethod("setBoolean", nbtcompount.getClass(), new Class<?>[] { String.class, boolean.class })
					.invoke(nbtcompount, new Object[] { k, v });
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return this;
	}

	public boolean getBoolean(String k) {
		return (boolean) NMSUtils.invokeMethod("getBoolean", nbtcompount, new Object[] { k });
	}

}
