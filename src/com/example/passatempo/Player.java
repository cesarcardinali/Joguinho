package com.example.passatempo;

public class Player {
	
	int gold;
	int incoming;
	int technology;
	int walls;
	int soldiers;
	String name;
	int health;

	public Player(String name) {
		this.gold = 10;
		this.incoming = 2;
		this.technology = 0;
		this.walls = 0;
		this.soldiers = 3;
		this.name = name;
		this.health = 20;
	}

	public Player(int gold, int incoming, int technology, int walls,
			int soldiers, String name, int hp) {
		this.gold = gold;
		this.incoming = incoming;
		this.technology = technology;
		this.walls = walls;
		this.soldiers = soldiers;
		this.name = name;
		this.health = hp;
	}
	
	
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getIncoming() {
		return incoming;
	}
	public void setIncoming(int incoming) {
		this.incoming = incoming;
	}
	public int getTechnology() {
		return technology;
	}
	public void setTechnology(int technology) {
		this.technology = technology;
	}
	public int getWalls() {
		return walls;
	}
	public void setWalls(int walls) {
		this.walls = walls;
	}
	public int getSoldiers() {
		return soldiers;
	}
	public void setSoldiers(int soldiers) {
		this.soldiers = soldiers;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//Funcoes de upgrade
	void Refresh(){
		incoming = 2 + technology*7 + walls*2;
		gold = gold + incoming;
	}
	
	boolean makeSold(int num){
		if(num*5 <= gold){
			soldiers = soldiers + num;
			gold = gold - num*5;
			return true;
		} else{
			return false;
		}
	}
	
	//mudar pra int - retornar 0 se ok e o valor q faltou caso nao ok
	boolean makeTech() { 
		if (technology == 0 && gold >= 15) {
			gold = gold - 15;
			technology++;
			return true;
		} else if (technology == 1 && gold >= 30) {
			gold = gold - 30;
			technology++;
			return true;
		} else if ((technology * technology) * 15 <= gold && technology > 1) {
			gold = gold - ((technology * technology) * 15);
			technology++;
			return true;
		} else {
			return false;
		}
	}
	
	boolean makeWall(){
		if (walls == 0 && 15 <= gold) {
			gold = gold - 15;
			walls++;
			return true;
		} else if (walls == 1 && gold >= 30) {
			gold = gold - 30;
			walls++;
			return true;
		} else if ((walls * walls) * 15 <= gold && walls > 1) {
			gold = gold - ((walls * walls) * 15);
			walls++;
			return true;
		} else {
			return false;
		}
	}
	
}
