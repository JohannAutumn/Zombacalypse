public class NPC {
	private String name; //npc name
	private int hp; //npc hp
	private int atk; //npc attack chance
	private int def; //npc defense
	private int dmg; //npc damage bonus
	
	//constructor
	public NPC(String npcname, int npchp, int npcatk, int npcdef, int npcdmg) {
		name = npcname;
		hp = npchp;
		atk = npcatk;
		def = npcdef;
		dmg = npcdmg;
	}
	
	//setters
	public void setName(String npcname) {
		name = npcname;
	}
	public void setHP(int npchp) {
		hp = npchp;
	}
	public void setAtk(int npcatk) {
		atk = npcatk;
	}
	public void setDef(int npcdef) {
		def = npcdef;
	}
	public void setDmg(int npcdmg) {
		dmg = npcdmg;
	}
	
	//getters
	public String getName() {
		return name;
	}
	public int getHP() {
		return hp;
	}
	public int getAtk() {
		return atk;
	}
	public int getDef() {
		return def;
	}
	public int getDmg() {
		return dmg;
	}
}	

	