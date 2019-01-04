package cn.blockmc.inventory;

import cn.blockmc.ZaoGun;

public enum GunPartSlot {
	SCOPE(2,ZaoGun.SCOPENODE),BARREL(5,ZaoGun.BARRELNODE);

	private final int id;
	private final String typenode;
	private GunPartSlot(int id,String typenode){
		this.id = id;
		this.typenode = typenode;
	}
	
	public int getInventoryID(){
		return id;
	}
	public String getTypeNode(){
		return typenode;
	}
	
	public static GunPartSlot getSlot(int id){
		for(GunPartSlot slot:GunPartSlot.values()){
			if(slot.id==id){
				return slot;
			}
		}
		return null;
	}
	public static GunPartSlot getByType(String typenode){
		for(GunPartSlot slot:GunPartSlot.values()){
			if(slot.typenode.equals(typenode)){
				return slot;
			}
		}
		return null;
	}

}
