package cn.blockmc;

import java.lang.reflect.InvocationTargetException;
import org.bukkit.inventory.ItemStack;

public class NMSItemStack {
	private final static Class<?> ITEM_STACK = ItemStack.class;
	private final static Class<?> CRAFT_ITEM_STACK = NMSUtils.getCraftClass("inventory.CraftItemStack");
	private Object nmsitem;
	private NBTComponent nbt;

	private NMSItemStack(Object nmsitem) {
		this.nmsitem = nmsitem;
		this.nbt = new NBTComponent(NMSUtils.invokeMethod("getOrCreateTag", nmsitem));
	}

	public NBTComponent getNBT() {
		return this.nbt;
	}

	public NMSItemStack setNBT(NBTComponent nbt) {
		this.nbt = nbt;
		return this;
	}

	public ItemStack asNewItemStack() {
		NMSUtils.invokeMethod("setTag", nmsitem, new Object[] { nbt.getTag() });
		return (ItemStack) NMSUtils.invokeStaticMethod("asCraftMirror", CRAFT_ITEM_STACK, new Object[] { nmsitem });
	}
	public int getMaxStack(){
		return (int) NMSUtils.getField("maxStackSize", NMSUtils.invokeMethod("getItem", nmsitem));
	}

	public void setMaxStack(int size) {
		NMSUtils.setField("maxStackSize", NMSUtils.invokeMethod("getItem", nmsitem), size);
	}

	public static NMSItemStack asNMSItemStack(ItemStack item) {
		try {
			return new NMSItemStack(NMSUtils.getMethod("asNMSCopy", CRAFT_ITEM_STACK, new Class<?>[] { ITEM_STACK })
					.invoke(null, item));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
