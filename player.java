public class Player {
	private String name; //player name
	private int maxHP; //player max hp
	private int HP; //player current hp
	private int atk; //player attack bonus
	private int def; //player defense
	private int dmg; //player damage bonus

	
	//constructor
	public Player(String pname, int pmaxhp, int php, int patk, int pdef, int pdmg) {
		name =  pname;
		maxHP = pmaxhp;
		HP = php;
		atk = patk;
		def = pdef;
		dmg = pdmg;
	}
	
	//setters
	public void setName(String pname) {
		name = pname;
	}
	public void setMaxHP(int pmaxhp) {
		maxHP = pmaxhp;
	}
	public void setHP(int php) {
		HP = php;
	}
	public void setAtk(int patk) {
		atk = patk;
	}
	public void setDef(int pdef) {
		def = pdef;
	}
	public void setDmg(int pdmg) {
		dmg = pdmg;
	}
	
	//getters
	public String getName() {
		return name;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public int getHP() {
		return HP;
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
	
