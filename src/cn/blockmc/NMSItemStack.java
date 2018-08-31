package cn.blockmc;

import org.bukkit.inventory.ItemStack;

public class NMSItemStack {
	private final static Class<?> CRAFT_ITEM_STACK = NMSUtils.getCraftClass("inventory.CraftItemStack");
	private Object nmsitem;
	private NBTComponent nbt;
	private NMSItemStack(Object nmsitem){
		this.nmsitem = nmsitem;
		this.nbt =new NBTComponent( NMSUtils.invokeMethod("getOrCreateTag", nmsitem));
	}
	public NBTComponent getNBT(){
		return this.nbt;
	}
	public void setNBT(NBTComponent nbt){
		this.nbt = nbt;
	}
	public ItemStack asNewItemStack(){
		NMSUtils.invokeMethod("setTag", nmsitem,new Object[]{nbt.getTag()});
		return (ItemStack) NMSUtils.invokeStaticMethod("asCraftMirror",  CRAFT_ITEM_STACK, new Object[]{nmsitem});
	}
	public static NMSItemStack asNMSItemStack(ItemStack item){
		return new NMSItemStack(NMSUtils.invokeStaticMethod("asNMSCopy", CRAFT_ITEM_STACK, new Object[]{item}));
	}
}
