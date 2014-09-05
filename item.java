//item class to be used by class Inventory
public class Item {
	private boolean itemUsed; //has the player used the item
	private boolean itemTaken; //has the player taken the item
	private String itemName; //what is the items name/keyword
	private String itemDesc; //description of the item
	
	//constructor
	public Item ( boolean used, boolean taken, String name, String desc) {
		itemUsed = used;
		itemTaken = taken;
		itemName = name;
		itemDesc = desc;
	}
		
	//setters
	public void setItemUsed (boolean used) {
		itemUsed = used;
	}
	public void setItemTaken (boolean taken) {
		itemTaken = taken;
	}
	public void setItemName (String name) {
		itemName = name;
	}
	public void setItemDesc (String desc) {
		itemDesc = desc;
	}
	
	//getters
	public boolean getItemUsed() {
		return itemUsed;
	}
	public boolean getItemTaken() {
		return itemTaken;
	}
	public String getItemName() {
		return itemName;
	}
	public String getItemDesc() {
		return itemDesc;
	}
}//end of class