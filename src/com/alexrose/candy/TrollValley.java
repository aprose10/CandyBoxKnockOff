package com.alexrose.candy;
import java.util.ArrayList;


public class TrollValley extends Quest{


	public TrollValley() {
		super("Troll Valley", 30, 30);
		
	}

	public void createEnemies(){
		ArrayList<Character> enemies = new ArrayList<Character>();
		
		for(int x = 0; x < 12; x++){
			Character babyTroll = new Character("Baby Troll", 50, 10, 150);
			enemies.add(babyTroll);
		}
		
		for(int a = 0; a < 5; a++){
			Character biggerTroll = new Character("Bigger Troll", 100, 20, 200);
			enemies.add(biggerTroll);
		}
		
		for(int a = 0; a < 1; a++){
			Character kingTroll = new Character("King Troll", 200, 30, 300);
			enemies.add(kingTroll);
		}
		
		addEnemies(enemies);
	}

}

