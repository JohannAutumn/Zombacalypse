import java.util.*;

public class Inventory {
	private int numOfItems = 8; //max number of items in the game
	
	//List of items in the game: (Item(itemused, itemtaken, itemname/keyword, item description))
	private Item blankitem = new Item ( false, false, "", "" );
	private Item proteinbar = new Item( false, false, "PROTEINBAR",
		"a healthy protein bar; use to increase your damage");
	private Item vitamins = new Item( false, false, "VITAMINS",
		"a bottle of vitamins; use to increase hp by 5");
	private Item baseballbat = new Item( false, false, "BASEBALLBAT",
		"your trusty baseball bat; increases damage by 2" );
	private Item helmet = new Item( false, false, "HELMET",
		"a policeman's helmet; increases your defense" );
	private Item beefjerky = new Item( false, false, "BEEFJERKY",
		"a sealed bag of beef jerky; heals 10 hp");
	private Item boatkeys = new Item( false, false, "BOATKEYS",
		"a set of keys, containing what looks like a boat key.");
	private Item fuelcan = new Item( false, false, "FUELCAN",
		"a full fuel can, take it to the boat in the harbor!");
	
	//array holding the items
	private Item[] inv = { blankitem, proteinbar, vitamins, baseballbat, 
		helmet, beefjerky, boatkeys, fuelcan };
	
	//method to list items in your inventory
	public void invList() {
		int itemCount = 0; //sets count of items to 0 for use in first if statement
		//checks number of items: if player has 0 items, displays first if
		for (int i = 0; i < inv.length;i++ ) {
			if ( inv[i].getItemTaken() && !inv[i].getItemUsed() ) {
				itemCount++;
			}
				
		}//end for
		if (itemCount == 0) {
			System.out.printf("\nYou don't have any items!\n\n");
		} else { //loops through the Item array inv and displays items the player has
			System.out.printf("\nYou have: \n");
			for (int i = 0; i < inv.length;i++ ) {
				if ( inv[i].getItemTaken() && !inv[i].getItemUsed() )
				System.out.printf("%s - %s\n", inv[i].getItemName(), inv[i].getItemDesc());
			}
			System.out.printf("\n\n");
		}
	}//end invList
	
	//method to check if a player has an item (taken true, used false)
	public boolean playerHas(String name) {
		boolean isItemIn = false;
		for (int i = 0;i < inv.length;i++) {
			if (inv[i].getItemName().equals(name) && 
			inv[i].getItemTaken() && !inv[i].getItemUsed() ) {
				isItemIn = true;
				break;
			} else {
				isItemIn = false;
			}
		}
		return isItemIn;
	}
	
	//method to check if a player has used an item (taken true, used true)
	public boolean playerUsed(String name) {
		boolean isItemUsed = false;
		for (int i = 0; i < inv.length;i++) {
			if (inv[i].getItemName().equals(name) &&
			inv[i].getItemTaken() && inv[i].getItemUsed() ) {
				isItemUsed = true;
				break;
			} else {
				isItemUsed = false;
			}//end else
		}//end if
		return isItemUsed;
	}//end playerUsed method
	
	//overloaded getter to get an Item from inv[] array with either index or name
	public Item getItem(int indexOf) {
		return inv[indexOf];
	}
	public Item getItem(String name) {
		Item returnItem = inv[0];
		for (Item i : inv) {
			if (i.getItemName().equals(name)) {
				returnItem = i;
			}//end if
		}//end for
		return returnItem;
	}//end method getItem(using name)
	
}// end of class
	
	