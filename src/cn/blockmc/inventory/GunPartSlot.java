package cn.blockmc.inventory;

public enum GunPartSlot {
	SCOPE(2);
	private int id;
	private GunPartSlot(int id){
		this.id = id;
	}
	public int getInventoryID(){
		return id;
	}
	public static GunPartSlot getSlot(int id){
		for(GunPartSlot slot:GunPartSlot.values()){
			if(slot.id==id){
				return slot;
			}
		}
		return null;
	}

}
