package cn.blockmc;

import java.lang.reflect.InvocationTargetException;

public class NBTComponent {
	private Object nbtcompount;

	public NBTComponent(Object nbtcompount) {
		this.nbtcompount = nbtcompount;
	}
	public Object getTag() {
		return nbtcompount;
	}
	public NBTComponent remove(String k){
		NMSUtils.invokeMethod("remove", nbtcompount,new Object[]{k});
		return this;
	}

	public NBTComponent setString(String k, String v) {
		NMSUtils.invokeMethod("setString", nbtcompount, new Object[] { k, v });
		return this;
	}

	public String getString(String k) {
		return (String) NMSUtils.invokeMethod("getString", nbtcompount, new Object[] { k });
	}

	public NBTComponent setInt(String k, int v) {
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
