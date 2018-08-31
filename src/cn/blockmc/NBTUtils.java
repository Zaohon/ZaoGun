package cn.blockmc;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.inventory.ItemStack;

public class NBTUtils {
	private final static Class<?> ITEM_STACK = ItemStack.class;
	private final static Class<?> CRAFT_ITEM_STACK = NMSUtils.getCraftClass("inventory.CraftItemStack");

//	public static ItemStack addNBT(ItemStack item, String k, Object v) {
// try {
	// Object nmsitem = getNMSItemStack(item);
	// Object tag = getNBT(nmsitem);
	// NMSUtils.invokeMethod("set" + v.getClass().getSimpleName(), tag, new
	// Object[] { k, v });
	// NMSUtils.invokeMethod("setTag", nmsitem, new Object[] { tag });
	// Object nitem;
	// nitem = NMSUtils.getMethod("asCraftMirror", CRAFT_ITEM_STACK, new Class[]
	// { nmsitem.getClass() })
	// .invoke(null, nmsitem);
	// return (ItemStack) nitem;
	// } catch (IllegalAccessException | IllegalArgumentException |
	// InvocationTargetException e) {
	// e.printStackTrace();
	// }
	//
	// return null;
	// }

	public static ItemStack setInt(ItemStack item, String k, int v) {
		try {
			Object nmsitem = getNMSItemStack(item);
			Object tag = getNBT(nmsitem);
			NMSUtils.getMethod("setInt", tag.getClass(), new Class[] { String.class, int.class }).invoke(tag, new Object[]{k,v});
			NMSUtils.invokeMethod("setTag", nmsitem, new Object[] { tag });
			Object nitem;
			nitem = NMSUtils.getMethod("asCraftMirror", CRAFT_ITEM_STACK, new Class[] { nmsitem.getClass() })
					.invoke(null, nmsitem);
			return (ItemStack) nitem;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static int getInt(ItemStack item, String k, int defaultvaule) {
		Object nmsitem = getNMSItemStack(item);
		Object tag = getNBT(nmsitem);
		Object o = NMSUtils.invokeMethod("getInt", tag, new Object[] { k });
		return o == null ? defaultvaule : (int) o;
	}

	public static String getString(ItemStack item, String k, String defaultvaule) {
		Object nmsitem = getNMSItemStack(item);
		Object tag = getNBT(nmsitem);
		Object o = NMSUtils.invokeMethod("getString", tag, new Object[] { k });
		return o == null ? defaultvaule : (String) o;
	}

	public static Double getDouble(ItemStack item, String k, double defaultvaule) {
		Object nmsitem = getNMSItemStack(item);
		Object tag = getNBT(nmsitem);
		Object o = NMSUtils.invokeMethod("getDouble", tag, new Object[] { k });
		return o == null ? defaultvaule : (Double) o;
	}

	public static ItemStack setBoolean(ItemStack item, String k, Boolean v) {
		try {
			Object nmsitem = getNMSItemStack(item);
			Object tag = getNBT(nmsitem);
			NMSUtils.getMethod("setBoolean", tag.getClass(), new Class[] { String.class, boolean.class }).invoke(tag, new Object[]{k,v});
			NMSUtils.invokeMethod("setTag", nmsitem, new Object[] { tag });
			Object nitem;
			nitem = NMSUtils.getMethod("asCraftMirror", CRAFT_ITEM_STACK, new Class[] { nmsitem.getClass() })
					.invoke(null, nmsitem);
			return (ItemStack) nitem;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Boolean getBoolean(ItemStack item, String k, boolean defaultvaule) {
		Object nmsitem = getNMSItemStack(item);
		Object tag = getNBT(nmsitem);
		Object o = NMSUtils.invokeMethod("getBoolean", tag, new Object[] { k });
		return o == null ? defaultvaule : (Boolean) o;
	}

	public static byte getByte(ItemStack item, String k, byte defaultvaule) {
		Object nmsitem = getNMSItemStack(item);
		Object tag = getNBT(nmsitem);
		Object o = NMSUtils.invokeMethod("getByte", tag, new Object[] { k });
		return o == null ? defaultvaule : (byte) o;
	}

	public static Long getLong(ItemStack item, String k, Long defaultvaule) {
		Object nmsitem = getNMSItemStack(item);
		Object tag = getNBT(nmsitem);
		Object o = NMSUtils.invokeMethod("getLong", tag, new Object[] { k });
		return o == null ? defaultvaule : (Long) o;
	}

	public static Object getNMSItemStack(ItemStack item) {
		try {
			Object nmsitem = NMSUtils.getMethod("asNMSCopy", CRAFT_ITEM_STACK, new Class<?>[] { ITEM_STACK })
					.invoke(null, item);
			return nmsitem;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getNBT(Object nmsitem) {
		try {
			return NMSUtils.invokeMethod("getOrCreateTag", nmsitem);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;

	}

}
