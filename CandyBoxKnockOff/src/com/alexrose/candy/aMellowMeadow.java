package com.alexrose.candy;
import java.util.ArrayList;


public class aMellowMeadow extends Quest{


	

	public aMellowMeadow() {
		super("A Mellow Meadow", 15, 10000);
		
	}

	public void createEnemies(){
		ArrayList<Character> enemies = new ArrayList<Character>();
		
		for(int x = 0; x < 10; x++){
			Character shrub = new Character("Angry Shrub", 100, 20, 10);
			enemies.add(shrub);
		}
		
		addEnemies(enemies);
	}

}
