import java.util.*;

public class Zombacalypse {
	Random randNum = new Random(); //initiates random number generator
	Scanner input = new Scanner(System.in); //initiates scanner
	Player newPlayer = new Player("NAME", 20, 20, 4, 7, 4); //creates a player for this game
	Inventory inv = new Inventory(); //initiates an object of the Inventory class for this game
	
	private int NUMBER_OF_ROOMS = 10; //number of rooms
	private boolean[] roomVisited = new boolean[NUMBER_OF_ROOMS]; //array to check if rooms completed
	private String OptInput; //Used to hold Scanner's input during command options
	private boolean OptCheck; //Used to hold while statements during command options
	private boolean zombie3Dead = false; //is zombie3 (in room 4) dead
	private boolean openCar = false; //checks if the car door in room5 is open
	private boolean zombieGirl = false; //checks if the zombie girl in room6 has been killed
	
	//method for taking command input
	private String ComInput(Scanner s, String[] roomComList) {
		//list of accepted system commands or their abbreviations
		String[] sysComList = { "QUIT", "EXIT", "I", "INV", "INVENTORY",
										"HEALTH", "HP", "HEAL", "STAT", "STATS",
										"SCORE" };
		Arrays.sort(sysComList); //sorts sysComList for binarySearch use
		Arrays.sort(roomComList); //sorts roomComList for binarySearch use
		System.out.printf( "\n>>> " ); // prints the command line for the game
		OptInput = s.nextLine(); // reads a line of input from the user
		OptInput = OptInput.toUpperCase(); // makes the input uppercase for ease of entering commands
		//checks if the entered command is part of the System command list then runs the sysCommand method if it is
		if ( Arrays.binarySearch( sysComList, OptInput ) >= 0 ) {
			this.sysCommand(OptInput); //method for handling system commands
			boolean OptCheck = false; //returns to the command prompt for more user input
			return OptInput; //returns the input for use in command methods
		//checks if the entered command is part of the room command list then exits back to the room method
		} else if ( Arrays.binarySearch( roomComList, OptInput ) >= 0 ) {
			return OptInput;
		//checks if the entered command starts with "use " and the method playerHas from class Inventory
		//before 
		} else if ( OptInput.length() > 4 && 
		OptInput.substring(0,4).equals("USE ") && 
		inv.playerHas(OptInput.substring(4))) {
			this.useItem(OptInput.substring(4)); //calls the method useItem to provide the effects of the item
			inv.getItem(OptInput.substring(4)).setItemUsed(true); //sets the item in inventory to "used"
			boolean OptCheck = false; //continues the do/while loop for input
			return OptInput;
		//checks if the "used" item has already been used, if it has, prints a message
		} else if ( OptInput.length() > 4 &&
		OptInput.substring(0,4).equals("USE ") &&
		inv.playerUsed(OptInput.substring(4))) {
			System.out.printf("\nYou already used your %s!\n\n", OptInput.substring(4));
			OptCheck = false;
			return OptInput;
		//prints message if the player doesn't have the item or entered the wrong name
		} else if ( OptInput.length() > 4 &&
		OptInput.substring(0,4).equals("USE ") &&
		!inv.playerUsed(OptInput.substring(4)) &&
		!inv.playerHas(OptInput.substring(4))) {
			System.out.printf("\nYou don't have that item.\n\n");
			OptCheck = false;
			return OptInput;
		} else { // if all suitable input commands don't match, prints out an error message
			boolean OptCheck = false;
			System.out.printf("\nInvalid command.\n\n");
			return OptInput;
		}	
	}// end ComInput
	
	//method to clean up input commands (System Commands)
	public boolean sysCommand(String i) {
		switch (i) {
			//cases to display players health
			case "HEALTH" :
			case "HP" :
			case "HEAL" :
				//displays current hp and max hp
				System.out.printf("\nYou have %d out of %d HP.\n\n", newPlayer.getHP(), newPlayer.getMaxHP());
				OptCheck = false;
				break;
			//cases to display inventory calling the method invList of inventory object
			case "INVENTORY" :
			case "I" :
			case "INV" :
				inv.invList();
				OptCheck = false;
				break;
			//cases to display a players stats
			case "STATS" :
			case "STAT" :
			case "SCORE" :
				this.score(); //calls the method score that displays a chars stats.
				OptCheck = false;
				break;
			//caes to quit the game
			case "QUIT" :
			case "EXIT" :
				System.out.println("Thank you for playing!");
				System.exit(0);
			} // end switch
		return OptCheck = false;
	} //end sysCommand method
	
	//method to use items
	public void useItem(String i) {
		switch (i) {
			case "PROTEINBAR": //increases players damage
				newPlayer.setDmg(newPlayer.getDmg()+1);
				System.out.printf("\nYou eat the protein bar, you now do more damage!\n\n");
				break;
			case "VITAMINS": //increases players hp
				newPlayer.setMaxHP(newPlayer.getMaxHP()+5);
				newPlayer.setHP(newPlayer.getHP()+5);
				System.out.printf("\nYou take the vitamins, you now have more hp!\n\n");
				break;
			case "BASEBALLBAT": //increases players dmg
				newPlayer.setDmg(newPlayer.getDmg()+2);
				System.out.printf("\nYou use your baseball bat, you now do more damage!\n\n");
				break;
			case "HELMET": //increases players def
				newPlayer.setDef(newPlayer.getDef()+1);
				System.out.printf("\nYou put on the policeman's helmet, your defense increases!\n\n");
				break;
			case "BEEFJERKY": //heals 10 hp (not above max hp)
				newPlayer.setHP(newPlayer.getHP()+10);
				if (newPlayer.getHP() > newPlayer.getMaxHP()) {
					newPlayer.setHP(newPlayer.getMaxHP());
				}
				System.out.printf("\nYou eat the beef jerky and heal 10 HP!\n\n");
				break;
			case "BOATKEYS": // needed to escape on the boat
				System.out.printf("You can't use this yet.");
				break;
			case "FUELCAN": // needed to escape on the boat
				System.out.printf("You can't use this yet.");
				break;
		}
	}//end method useItem
			
	//method for pausing
	public static void pause(Scanner s) {
		System.out.println("<<< Hit <Enter> to continue >>>");
		s.nextLine();
	}
	
	//method for die rolling
	public static int dieRoll(int numOfDice, int typeOfDice, int bonus) {
		Random randNum = new Random();
		int rollTotal = 0;
		//gives a random number between 1 and the typeOfDice for the number of dice
		//can also apply a bonus or a penalty
		for (int i = 1;i <= numOfDice;i++) {
			rollTotal+=1+randNum.nextInt(typeOfDice);
		}
		rollTotal+=bonus;
		return rollTotal;
	}
	
	//method that randomly selects a string when the player misses (used in the combat method)
	public static String playerMiss(NPC npc) {
		Random randNum = new Random();
		String returnText;
		int missType = randNum.nextInt(3);// gives a random int to pick from one of three miss statements
		switch (missType) {
			case 0:
				returnText = String.format("You swing at the %s and miss.\n", npc.getName());
				break;
			case 1:
				returnText = String.format("The %s blocks your attack.\n", npc.getName());
				break;
			default:
				returnText = String.format("The %s dodges your attack.\n", npc.getName());
				break;
		}
		return returnText;
	}
	
	//method that randomly selects a string when the NPC misses (used in the combat method)
	public static String NPCMiss(NPC npc) {
		Random randNum = new Random();
		String returnText;
		int missType = randNum.nextInt(3);// gives a random int to pick from one of three miss statements
		switch (missType) {
			case 0:
				returnText = String.format("A %s swings at you and misses.\n", npc.getName());
				break;
			case 1:
				returnText = String.format("You block the %s's attack.\n", npc.getName());
				break;
			default:
				returnText = String.format("You dodge the %s's attack.\n", npc.getName());
				break;
		}
		return returnText;
	}		
		
	//method for a round of combat 
	public void combatRound(Player player, NPC npc) {
		boolean phit;
		boolean npchit;
		int pdmg;
		int npcdmg;
		
		//determine hits (if random1-10+attackers atk is greater or equal to defense its a hit)
	
		if (this.dieRoll(1,10,player.getAtk()) >= npc.getDef()) {
			phit = true;
		} else { phit = false; }
		if (this.dieRoll(1,10,npc.getAtk()) >= player.getDef()) {
			npchit = true;
		} else { npchit = false; }
		
		//determine damage the player does
		if (phit == true) {
			pdmg = this.dieRoll(1,4,player.getDmg());//1-4 plus players damage
			System.out.printf("You hit the %s for %d damage.\n", npc.getName(), pdmg);
			npc.setHP(npc.getHP() - pdmg);//subtracts players damage from npc hp
		} else {
			System.out.println(this.playerMiss(npc));} //runs playerMiss method to give random miss print statement
		
		//determines damage the npc does
		if (npc.getHP() <= 0) {
			return;
		} else if (npchit == true) {
			npcdmg = this.dieRoll(1,4,npc.getDmg());//1-4 plus npc damage
			System.out.printf("The %s hits you for %d damage.\n", npc.getName(), npcdmg);
			player.setHP(player.getHP() - npcdmg);//subtracts npcs damage from player hp
		} else {
			System.out.println(this.NPCMiss(npc));} //runs npcMiss method to give random miss print statement
		
	} // end combatRound method
	
	//method for a full fight
	public void combat(Player player, NPC npc) {
		
		//runs a while loop that ends when either player or npc dies
		while (player.getHP() > 0 && npc.getHP() > 0) {
			this.pause(input);
			this.combatRound(player, npc); //runs a round of combat using the combatRound method
			if (player.getHP() > 0 && npc.getHP() > 0) {
				continue;
			} else if (player.getHP() <= 0) {
				System.out.printf("Oh no! Sorry, %s, but the %s has killed you.. Please try again!\n\n", player.getName(), npc.getName());
				System.exit(0);
			} else {
				System.out.printf("You have killed the %s!\n\n", npc.getName());
				break;
			}
		}
	}// end combat method
	
	//method to display the players stats
	public void score() {
		System.out.printf("\nName: %7s        HP: %7d/%d  " 
						+ "Atk: %5d    Dmg: %5d   Def: %5d\n\n",
						newPlayer.getName(), newPlayer.getHP(), newPlayer.getMaxHP(),
						newPlayer.getAtk(), newPlayer.getDmg(), newPlayer.getDef() );
	}//end score method
	
	//room0 - intro
	public void intro() {
	//First room of the game, gets user name and prints intro
		if (!roomVisited[0]) { //checks if player has visited the room before
			roomVisited[0] = true; //marks that player has visited the room
			System.out.println("\n\n\n\n\nGreetings and welcome to John's Amazing Adventure game!\n");
			System.out.println("Before we get started, what is your name?");
			String newPlayerName = input.nextLine();
			newPlayer.setName(newPlayerName);
			System.out.printf("Welcome %s,\n\n\n", newPlayer.getName());
			System.out.println("One evening, while you are working on your Introduction to Programming homework");
			System.out.println("in your pajamas, you see a large flash from outside, followed by a deafening ");
			System.out.println("boom. As you walk to the door, you begin to hear screams. Grabbing a kitchen ");
			System.out.println("knife, you step outside.");
			System.out.println("\nAs you open the door, you see your neighbour shambling toward you. You've seen ");
			System.out.println("enough zombie movies to know: The Zombie Apocalypse is upon us! As your ");
			System.out.println("neighbor attacks you, you wish you hadn't left your baseball bat in the back\nyard.\nYour zombie neighbour attacks you!\n\n");
			NPC zombie1 = new NPC("zombie neighbour", 6, 1, 4, 1);//instantiates an enemy npc zombie1
			this.combat(newPlayer, zombie1);//starts combat with zombie1
			System.out.println("As your neighbour falls to the ground, dead once again, you catch your breath ");
			System.out.println("and gather your bearings. Noise comes from the STREET in front of your house. ");
			System.out.println("You know you have some supplies back in your HOUSE and there was always the \nBACK YARD.\n");
		} else { //prints if the room has been visited
			System.out.println("You find yourself back in your front yard. The body of your neighbour remains \n"
							+ "where you left it.\n");
		}//end if intro is completed
		boolean OptCheck = false;
		String[] RoomList = { "STREET", "HOUSE", "BACK YARD", "BACK" }; //list of possible room commands
		//input phase of the room: do/while loop until player leaves room or dies
		//uses switch statement to process viable input commands
		do {
			System.out.printf("Your options are: \"STREET, HOUSE, or BACK YARD\"\n");
			System.out.printf("You can also enter \"HEALTH\" to see your health,\n\"INVENTORY\" to see what's in your inventory,\n"
							+ "\"STATS\" to see your stats\nor \"QUIT\" to quit the game.\n");
			this.ComInput(input, RoomList); //
			
			switch(OptInput) {
				case "STREET" :
					System.out.println("\nYou step out into the street.");
					OptCheck = true; //closes dowhile statement
					this.room3(); //takes player to room3 - street1
					break;
				case "HOUSE" :
					System.out.printf("\nYou walk back into the house.\n\n");
					OptCheck = true;
					this.room1();//takes player to room1 - house
					break;
				case "BACK YARD" :
				case "BACK" :
					System.out.println("\nYou walk into the back yard.");
					OptCheck = true;
					this.room2(); //takes player to room2 - backyard
					break;
				default : 
					OptCheck = false;
					break;
			} //end switch
		} while (!OptCheck); //end dowhile
		
	} //end room0 - intro
	
	//room1 - players house
	public void room1() {
		if (!roomVisited[1]) { //checks if player has visited the room
			roomVisited[1] = true; //marks that the player has visited
			System.out.println("\nAs you step back into the house, catching your breath, you wish you had gone");
			System.out.println("shopping before this poop hit the fan. Going through your stuff, you notice");
			System.out.println("a PROTEIN BAR and a bottle of VITAMINS. Perhaps those would be helpful? After ");
			System.out.println("you grab them, you'd better head back OUTSIDE; you won't last here long without\nfood.\n");
		} else {
			System.out.println("\nYou are back in your house and it still looks just as you left it.\n");
			if (!inv.getItem("PROTEINBAR").getItemTaken()) {//checks if player has taken proteinbar
				System.out.println("Your PROTEINBAR is still here.");
			}
			if (!inv.getItem("VITAMINS").getItemTaken()) { //checks if player has taken vitamins
				System.out.println("Your VITAMINS are still here.");
			}
		}
		boolean OptCheck = false;
		String[] RoomList = { "GET PROTEINBAR", "GET VITAMINS", "OUT", "OUTSIDE" }; //room command list
		do {
			System.out.printf("Your options are: \"GET PROTEINBAR, GET VITAMINS and OUTSIDE\"\n");
			System.out.printf("You can also enter \"HEALTH\" to see your health,\n\"INVENTORY\" to see what's in your inventory,\n"
							+ "\"STATS\" to see your stats\nor \"QUIT\" to quit the game.\n");
			this.ComInput(input, RoomList);
			switch (OptInput) {
				case "GET PROTEINBAR" :
					if (!inv.getItem("PROTEINBAR").getItemTaken()) { //if player hasn't taken bar, gets it
						System.out.println("\nYou grab the protein bar! Try USE PROTEINBAR to eat it.\n");
						inv.getItem("PROTEINBAR").setItemTaken(true); //marks that player has taken bar
						OptCheck = false;
					} else {
						System.out.println("You already grabbed it!\n");
						OptCheck = false;
					}
					break;
				case "GET VITAMINS" :
					if ( !inv.getItem("VITAMINS").getItemTaken() ) {
						System.out.println("\nYou grab the vitamins and take them. Try USE VITAMINS to take them.\n");
						inv.getItem("VITAMINS").setItemTaken(true);
						OptCheck = false;
					} else { 
						System.out.println("You already grabbed them!\n");
						OptCheck = false;
					}
					break;
				case "OUT" :
				case "OUTSIDE" :
					System.out.println("You head back outside.\n");
					OptCheck = true;
					this.intro(); //back to room0
				default :
					OptCheck = false;
					break;
			}//end switch
		} while (!OptCheck);//end dowhile
	}// end house - room1
	
	//room2 - back_yard
	public void room2() {
		if (!roomVisited[2]) {
			roomVisited[2] = true;
			System.out.print("\nYou walk into your back yard to find another neighbour hunched over some\n"
							+ "unidentifiable, deceased animal. A few meters behind him is your baseball bat,\n"
							+ "propped up against a tree. As you approach, your zombie neighbor looks up, sees\n"
							+ "you and begins stumbling towards you slowly.\nYour (bigger) zombie neighbour attacks you!\n\n");
			NPC zombie2 = new NPC("bigger zombie neighbour", 10, 2, 5, 2); //instantiates npc zombie 2
			this.combat(newPlayer, zombie2); //starts combat with zombie2
			System.out.print("You finish off yet another undead foe and take stock of your surroundings.\n"
							+ "Although your backyard is fenced in, there is nothing here except your BAT,\n"
							+ "so you should probably head back to the FRONT YARD.\n\n");
		} else {
			System.out.println("You are back in your back yard, the body of your neighbour where you left him.");
			if (!inv.getItem("BASEBALLBAT").getItemTaken()) { System.out.println("Your bat is still leaning against the tree.\n\n");
			} else { System.out.printf( "\n\n" ); }
		}//end if room2 is completed
		boolean OptCheck = false;
		String[] RoomList = { "FRONT", "FRONT YARD", "GET BASEBALLBAT" };
		do {
			if (!inv.getItem("BASEBALLBAT").getItemTaken()) {
				System.out.println("Your options are: \"FRONT YARD or GET BASEBALLBAT\"");
			} else { 
				System.out.println("Your option is: \"FRONT YARD\"");
			}
			System.out.println("You can also enter \"HEALTH\" to see your health,\n\"INVENTORY\" to see what's in your inventory,\n"
							+ "\"STATS\" to see your stats\nor \"QUIT\" to quit the game.\n");
			this.ComInput( input, RoomList );
			switch (OptInput) {
				case "FRONT YARD" :
				case "FRONT" :
					System.out.println("\nYou walk back into the front yard.\n");
					OptCheck = true;
					this.intro();
					break;
				case "GET BASEBALLBAT" :
					if (!inv.getItem("BASEBALLBAT").getItemTaken()) {
						System.out.println("\nYou grab your trusty baseball bat. Try USE BASEBALLBAT to do more damage!\n");
						inv.getItem("BASEBALLBAT").setItemTaken(true);
						break;
					} else {
						System.out.println("\nYou already got it!");
						break;
					}
				default : 
					OptCheck = false;
					break;
			} //end switch
		} while (!OptCheck); //end dowhile
	} //end room2 - front yard
	
	//room3 - street1
	public void room3() {
		if (!roomVisited[3]) {
			roomVisited[3] = true;
			System.out.print("\nYou walk out into the street, realizing you are still wearing your pajamas.\n"
							+ "Before you have the chance to head back into your house to grab some real\n"
							+ "clothes, you hear a scream coming from down the street. Turning, you see that\n"
							+ "a half-dozen or so burning cars are spitting out smoke. You cannot see through\n"
							+ "the smoke, but you hear the scream again. Up the street, the road looks \n"
							+ "relatively clear.\n\n");
		} else {
			System.out.println("You find yourself back on the street in front of your house. Smoke still burns\n"
							+ "from down the street, and up the street still looks clear.");
			if (roomVisited[4]) {
				System.out.println("You hear the moaning of the zombie horde down the street.\n");
			} else {
				System.out.println("\n");
			}	
		}//end if room3 is completed
		boolean OptCheck = false;
		String[] RoomList = { "FRONT", 	"FRONT YARD", "UP", "DOWN" };
		do {
			System.out.print( "*NOTE* This will be the last room that provides you with ALL of the possible\n"
							+ "commands. Obvious commands in future rooms will still be shown, but there will\n"
							+ "be other, hidden commands. Also, while not displayed, HEALTH, INVENTORY\n"
							+ "STATS and QUIT will always be available. (remember to GET items)\n\n");
			System.out.println("Your options are: \"FRONT YARD, UP, and DOWN\"\n");
			this.ComInput( input, RoomList );
			switch (OptInput) {
				case "FRONT YARD" :
				case "FRONT" :
					System.out.println("\nYou walk back into the front yard.\n");
					OptCheck = true;
					this.intro();
					break;
				case "DOWN" :
					OptCheck = true;
					System.out.println("\nYou walk down the street.\n");
					this.room4();
					break;
				case "UP" :
					System.out.println("\nYou walk up the street.\n");
					OptCheck = true;
					this.room5();
					break;
				default : 
					OptCheck = false;
					break;
			} //end switch
		} while (!OptCheck); //end dowhile
	} //end room3 - street1
	
	//room4 - street2
	public void room4() {
		if (!roomVisited[4]) {
			roomVisited[4] = true;
			System.out.print("You walk through the smoke for a few seconds before it clears up a little bit.\n"
							+ "Rubbing the smoke from your eyes, you see the freshly slain body of a policeman\n"
							+ "wearing a helmet and being eaten by a zombie. The more the zombie eats, the\n"
							+ "stronger it seems to get. It doesn't seem to have noticed you, but farther\n"
							+ "down the street you hear the moaning of a horde of zombies and it is growing\n"
							+ "louder. If you hurry, you could finish off the zombie and grab the policeman's\n"
							+ "helmet before they arrive. Or you could go back up the street.\n\n");
		} else {
			System.out.print("You walk back into the smoke, but this time, before it clears, you feel the\n"
							+ "hands of dozens of zombies clawing you and sinking their teeth into your\n"
							+ "flesh. Before long, your vision goes black. You have been killed.\n\n");
			System.out.println("Thank you for playing!");
			System.exit(0);	
		}//end if room4 is completed
		boolean OptCheck = false;
		String[] RoomList = { "UP", "KILL ZOMBIE", "GET HELMET" };
		do {
			System.out.println("Your obvious options are: \"KILL ZOMBIE or UP\"\n");
			this.ComInput( input, RoomList );
			switch (OptInput) {
				case "UP" :
					System.out.println("\nYou walk back up the street.\n");
					OptCheck = true;
					this.room3();
					break;
				case "KILL ZOMBIE" :
					if (!zombie3Dead) {
						System.out.print("\nAs you approach, the zombie looks at you and attacks!\n\n");
						NPC zombie3 = new NPC("feasting zombie", 10, 3, 7, 3);
						this.combat(newPlayer, zombie3);
						zombie3Dead = true;
						System.out.print("You finish off the zombie that had been eating the policeman. Perhaps now you\n"
										+ "could get that helmet!\n\n");
						OptCheck = false;
						break;
					} else {
						System.out.print("The zombie is already dead! You'd better hurry before those zombies get here.\n\n");
						OptCheck = false;
						break;
					}
				case "GET HELMET" :
					if (inv.getItem("HELMET").getItemTaken()) {
						System.out.print("You already got the policeman's helmet!\n\n");
						break;
					} else if (zombie3Dead) {
						System.out.print("You get the policeman's helmet, use it to increase your defence!\n\n");
						inv.getItem("HELMET").setItemTaken(true);
						break;
					} else {
						System.out.print("As you draw close to grab the helmet, the zombie looks at you and attacks!\n\n");
						NPC zombie3 = new NPC("feasting zombie", 10, 3, 7, 3);
						this.combat(newPlayer, zombie3);
						zombie3Dead = true;
						System.out.print("You get the policeman's helmet, use it to increase your defence!\n\n");
						inv.getItem("HELMET").setItemTaken(true);
						break;
					}
				default : 
					OptCheck = false;
					break;
			} //end switch
		} while (!OptCheck); //end dowhile
	} //end room4 - street2
	
	//room5 - street3
	public void room5() {
		if (!roomVisited[5]) {
			roomVisited[5] = true;
			System.out.printf( "Walking up the street, you notice that most of the houses look locked and\n"
							+  "shuttered. The street is cleared of cars except for one that is closed but\n"
							+  "looks to be unlocked. As you notice the car, you see a flutter of curtains in a\n"
							+  "nearby window and think you see a young girl. The door to the house is open.\n"
							+  "You could also continue up the or head back down towards your house.\n\n");
		} else {
			System.out.printf( "This section of your street looks much the same as you left it. The car is\n"
							+  "still where it was left and the door to the house where you saw the girl is\n"
							+  "open.\n");
		}//end if room5 is completed
		boolean OptCheck = false;
		String[] RoomList = { "UP", "CAR", "OPEN CAR", "OPEN CAR DOOR", "LOOK IN CAR",
							"LOOK CAR", "DOWN", "HOUSE", "GET BEEFJERKY" };
		do {
			System.out.printf("Your obvious options are: \"DOWN, CAR, HOUSE or UP\"\n");
			this.ComInput( input, RoomList );
			switch (OptInput) {
				case "UP" :
					System.out.printf("\nYou continue up the street.\n\n");
					OptCheck = true;
					this.room7();
					break;
				case "DOWN" :
					System.out.printf("You go back down the street.\n\n");
					OptCheck = true;
					this.room3();
					break;
				case "CAR" :
					System.out.printf("Perhaps you should OPEN the car door first.\n\n");
					OptCheck = false;
					break;
				case "OPEN CAR" :
				case "OPEN CAR DOOR" :
					if (openCar) {
						System.out.printf("The car door is already open, try LOOKing IN the CAR.\n\n");
						OptCheck = false;
						break;
					} else {
						System.out.printf("You open the car door, try LOOKing IN the CAR.\n\n");
						openCar = true;
						OptCheck = false;
						break;
					}
				case "LOOK IN CAR" :
				case "LOOK CAR" :
					System.out.printf( "The car looks like it was left on until it ran out of gas.\n\n");
					if (!inv.getItem("BEEFJERKY").getItemTaken()) {
						System.out.printf( "There is bag of beef jerky is here. Try typing GET BEEFJERKY.\n\n");
					}
					OptCheck = false;
					break;
				case "GET BEEFJERKY" :
					if ( !openCar ) {
						System.out.printf( "Try OPENing the CAR first!\n\n" );
						break;
					} else if (!inv.getItem("BEEFJERKY").getItemTaken()) {
						System.out.printf( "You get a bag of beef jerky. If you USE it, you will gain 10 HP!\n\n");
						inv.getItem("BEEFJERKY").setItemTaken(true);
						break;
					} else {
						System.out.printf("You already got it! Try USE BEEFJERKY to eat it!");
					}
				case "HOUSE" :
					System.out.printf( "You step into the house.\n\n" );
					OptCheck = false;
					this.room6();
					break;
				default : 
					OptCheck = false;
					break;
			} //end switch
		} while (!OptCheck); //end dowhile
	} //end room5 - street3
	
	//room6 - house1
	public void room6() {
		if (!roomVisited[6]) {
			roomVisited[6] = true;
			System.out.printf("\nThe inside of the house is a gruesome reminder of what has been happening\n"
							+ "around the city (or the world?). Blood covers the walls and furniture and in\n"
							+ "the corner is a zombie eating her former husband. As you step in, she looks up,\n"
							+ "sees you and attacks.\nA zombie wife attacks you!\n");
			NPC zombieWife = new NPC("zombie wife", 10, 2, 5, 3);
			this.combat(newPlayer, zombieWife);
			System.out.printf("\nAs you finish off the zombie housewife, you hear a feeble wimper from a nearby\n"
							+ "closet. The door swings open slowly and inside is a badly wounded young girl.\n"
							+ "She smiles at you feebly and says \"My mom hurt me badly, I think I'm dying. I\n"
							+ "think I can help you, but you'll have to help me. I have the keys to my dad's\n"
							+ "boat, but I've seen what happens if you get bitten. If you make sure I don't\n"
							+ "come back as a zombie, you can have the boat keys.\"");
			
		} else {
			System.out.printf("\nYou step back into the house to find the house much the same as you left it,\n");
			if (zombieGirl) {
				System.out.printf("the corpse of the young girl still in the closet.\n");
				if (!inv.getItem("BOATKEYS").getItemTaken()) {
					System.out.printf("She is still holding the BOATKEYS to her chest.\n");
				}
			} else {
				System.out.printf("but the girl has turned into a zombie and attacks you!\n");
				NPC zombiegirl = new NPC("zombie girl", 10, 2, 4, 2);
				this.combat(newPlayer, zombiegirl);
				zombieGirl = true;
				System.out.printf("\nThe girl drops the BOATKEYS as you kill her. You should get them!\n");
			}
		}
		boolean OptCheck = false;
		String[] RoomList = { "OUTSIDE", "KILL GIRL", "GET BOATKEYS" };
		do {
			if (!zombieGirl) {
				System.out.printf("\nYour options are: \"OUTSIDE or KILL GIRL\"\n");
			} else {
				System.out.printf("\nYour option is: \"OUTSIDE\"\n");
			}
			this.ComInput(input, RoomList);
			switch (OptInput) {
				case "OUTSIDE":
					System.out.printf("\nYou step back outside.\n");
					OptCheck = true;
					this.room5();
					break;
				case "KILL GIRL":
					OptCheck = false;
					System.out.printf("\nThe girl thanks you when you agree to kill her. She holds the key to her\n"
									+ "chest and closes her eyes.\n");
					NPC dyinggirl = new NPC("dying girl", 5,0,0,0);
					this.combat(newPlayer, dyinggirl);
					zombieGirl = true;
					System.out.printf("\nThe girl drops the BOATKEYS as you kill her. You should get them!\n");
					break;
				case "GET BOATKEYS":
					OptCheck = false;
					if (!zombieGirl) {
						System.out.printf("\nThe girl pulls the keys away: \"You'll have to kill me if you want them!\"\n");
						break;
					} else {
						System.out.printf("\nYou get the boatkeys. Written on the side of the key is: \"Safe Sailing\"\n");
						inv.getItem("BOATKEYS").setItemTaken(true);
						break;
					}
				default :
					OptCheck = false;
					break;
			}//end switch
		} while (!OptCheck);//end dowhile
	}//end of room6 - house1

	//room7 - street4
	public void room7() {
		if (!roomVisited[7]) {
			roomVisited[7] = true;
			System.out.printf( "\nThings begin to look worse the farther you get from your house. There are\n"
							+ "devoured corpses everywhere.. you are grateful most people appear to be so\n"
							+ "badly consumed that they won't be coming back to life. Ahead, the road to the\n"
							+ "harbor looks relatively clear.\n");
		} else {
			System.out.printf( "\nYou are on the street connecting your neighbourhood to the harbor. \n");
		}
		System.out.printf( "As you walk, a zombie stumbles out from a house, sees you and attacks!\n\n");
		NPC streetzombie = new NPC("street zombie", 10, 2, 5, 3);
		this.combat(newPlayer, streetzombie);
		System.out.printf( "\nYou finish off the zombie. Perhaps you should continue on before another comes.\n");
		boolean OptCheck = false;
		String[] RoomList = { "HARBOR", "BACK" };
		do {
			System.out.printf("Your options are: \"HARBOR or BACK\"\n");
			this.ComInput(input, RoomList);
			switch (OptInput) {
				case "HARBOR":
					OptCheck = true;
					System.out.printf("You head towards the harbor.\n");
					this.room8();
					break;
				case "BACK":
					OptCheck = true;
					System.out.printf("You head back towards your house.\n");
					this.room5();
					break;
				default :
					OptCheck = false;
					break;
			}//end switch
		} while (!OptCheck);//end dowhile
	}// end room7 - street4
	
	// room8 - central harbor
	public void room8() {
		if (!roomVisited[8]) {
			roomVisited[8] = true;
			System.out.printf("\nYou have arrived at the harbor. Down below you you can see a mob of zombies\n"
							+ "surrounding a small group of people on a boat. On the back of the boat you see\n"
							+ "the name \"Safe Sailing.\" One of the zombies turns around, sees you and attacks!\n");
			NPC harborzombie = new NPC("harbor zombie", 10, 2, 6, 3);
			this.combat(newPlayer, harborzombie);
			System.out.printf( "After killing the zombie you turn your attention back to the group of people on\n"
							+ "the boat. When they see you, one of them grabs a bullhorn and explains: \"We are\n"
							+ "trapped here.. we need the keys to the boat and some fuel before we can get out\n"
							+ "of here. If you grab those things, we'll clear a path for you to the boat and\n"
							+ "we can leave! Just yell \'READY\' when you are!\" He points to a the fuel shed\n"
							+ "to your right and goes back to fending off zombies.\n");
		} else {
			System.out.printf("You are back in the center of the harbor. The group of people on the boat are\n"
				+ "still fighting off the zombies. You could head into the fuel shed, back to your\n"
				+ "house or yell that you are ready.\n");
		}
		boolean OptCheck = false;
		String[] RoomList = { "BACK", "FUEL SHED", "READY" };
		do {
			System.out.printf("Your options are: \"BACK, FUEL SHED or READY\"\n");
			this.ComInput(input, RoomList);
			switch (OptInput) {
				case "BACK":
					System.out.printf("You head back towards your house.");
					OptCheck = true;
					this.room7();
					break;
				case "FUEL SHED":
					System.out.printf("You walk into the fuel shed.\n");
					OptCheck = true;
					this.room9();
					break;
				case "READY":
					if ( !inv.getItem("BOATKEYS").getItemTaken() &&
					!inv.getItem("FUELCAN").getItemTaken() ) {
						System.out.printf("\nThe man yells out to you: \"You still need the BOATKEYS and the FUELCAN!\"\n");
						OptCheck = false;
						break;
					} else if ( inv.getItem("BOATKEYS").getItemTaken() &&
					!inv.getItem("FUELCAN").getItemTaken() ) {
						System.out.printf("\nThe man yells out to you: \"You still need the FUELCAN!\"\n");
						OptCheck = false;
						break;
					} else if ( !inv.getItem("BOATKEYS").getItemTaken() &&
					inv.getItem("FUELCAN").getItemTaken() ) {
						System.out.printf("\nThe man yells out to you: \"You still need the BOATKEYS!\"\n");
					} else {
						System.out.printf("\n\"Alright then!\" the man yells, \"Clear a path for our friend here!\"\n"
										+ "The group throws a few grenades into the crowd of zombies, clearing a path for\n"
										+ "you to make it to the boat. As you run through the gap onto the boat, the\n"
										+ "zombies close in, but the other people keep them at bay while you pour in the\n"
										+ "fuel and start the engine. \"We're going to head to Catalina Island, it must be\n"
										+ "safe there\" the man says. As the boat pulls away, you feel safe for the first\n"
										+ "time since this whole thing started!\n"
										+ "CONGRATULATIONS! You have survived the Zombacalypse!\n"
										+ "Thank you for playing!\n"
										+ "THE END! (or is it?)");
						System.exit(0);
					}
				default :
					OptCheck = false;
					break;
			}//end switch
		} while (!OptCheck);//end dowhile
	}//end room8 - central harbor
	
	//room9 - harbor fuel shed
	public void room9() {
		if (!roomVisited[9]) {
			roomVisited[9] = true;
			System.out.printf( "You step into the fuel shed in slight dismay. A small fire has started. To make\n"
							+  "matters worse, a zombie that has caught flame is shambling towards you. You had\n"
							+  "better finish him off and grab that fuel quickly before the whole shed goes up.\n"
							+  "The zombie stumbles towards you, its flesh melting from its bones.\n");
			NPC flamingzombie = new NPC("flaming zombie", 15, 3, 7, 4);
			this.combat(newPlayer, flamingzombie);
			System.out.printf( "You finish off the flaming zombie. You should grab that fuel can before the\n"
							+  "whole place goes up!\n");
		} else {
			System.out.printf("\nYou step back into the fuel shed in time to see the flames reach the pile of\n"
							+ "oil drums and ignite them. There is a bright flash, a searing pain, and you are\n"
							+ "DEAD!\n\n");
			System.out.printf("Thank you for playing! Please try again!\n\n");
			System.exit(0);
		}
		boolean OptCheck = false;
		String[] RoomList = { "GET FUELCAN", "OUTSIDE" };
		do {
			System.out.printf("Your options are: \"GET FUELCAN or OUTSIDE\"\n");
			this.ComInput(input, RoomList);
			switch (OptInput) {
				case "GET FUELCAN":
					OptCheck = false;
					System.out.printf("\nYou grab the fuel can. You'd better get out NOW!\n");
					inv.getItem("FUELCAN").setItemTaken(true);
					break;
				case "OUTSIDE":
					OptCheck = true;
					System.out.printf("\nYou head out of the shed back into the harbor.\n");
					this.room8();
					break;
				default :
					OptCheck = false;
					break;
			}//end switch
		} while (!OptCheck);//end dowhile
	}//end of room9 - harbor fuel shed
	
}//end class