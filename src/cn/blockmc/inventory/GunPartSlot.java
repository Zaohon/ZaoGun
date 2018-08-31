package cn.blockmc.inventory;

public enum GunPartSlot {
	SCOPE(2,"scope"),BARREL(5,"barrel");

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

}
