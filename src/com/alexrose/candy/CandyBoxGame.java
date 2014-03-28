package com.alexrose.candy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.alexrose.candy.Ability.Category;

import android.util.Log;


public class CandyBoxGame{
	private int gold;
	private int recoveryTime = 10000;
	private Date lastGoldUpdate;
	private Date lastHealthUpdate;
	private ArrayList<Item> allItems = new ArrayList<Item>();
	private ArrayList<Item> storeItems = new ArrayList<Item>();
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private Ability[] selectedAbilities = new Ability[3];
	private static Character character;
	private ArrayList<Quest> allForestQuests = new ArrayList<Quest>();
	private SkillTree skillTree;
	private HashMap<String, Character> allEnemies =  new HashMap<String, Character>();

	public CandyBoxGame(int gold){
		this.gold = gold + 150000;
		lastGoldUpdate = new Date();
		createItemsInGame();
		character = new Character("Hero",10000,10,10);
		createAbilities();
		createEnemies();
		createQuests();
	}

	public void update(){
		increaseGold();
		updateRecoveryTime();
		checkItemsInShop();
		checkQuests();
		
		
		/*if(gold >= 160 && inventory.size() == 0){
			purchaseItem(storeItems.get(0));
			Log.d("YOLO", "purchased the: " + inventory.get(0).getName());
		}*/

		/*
		 * Will update item list, quests, etc..
		 */
	}

	public void increaseGold(){
		Date newTime = new Date();
		long time = newTime.getTime() - lastGoldUpdate.getTime();
		if(time >= 1000){
			gold = (int) (gold + (time/1000));
			lastGoldUpdate = newTime;
		}

	}
	public int getGold(){
		return gold;
	}

	public void purchaseItem(Item purchasedItem){
		storeItems.remove(purchasedItem);
		gold = gold - purchasedItem.getPrice();
		inventory.add(purchasedItem);
		character.updateStats(purchasedItem);
		update();
	}

	public void createItemsInGame(){
		
		try {
		    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
		           Candybox.activity.getAssets().open("Items.txt")));
		    String inputString;               
		    while ((inputString = inputReader.readLine()) != null) {
		    	String[] tokens = inputString.split(";");
		    	allItems.add(new Item(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),
		    			Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4])));

		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}


	public void checkItemsInShop(){
		for(int x = 0; x <allItems.size(); x++){
			Item currentItem = allItems.get(x);
			if(currentItem.getPrice() <= gold){
				storeItems.add(currentItem);
				allItems.remove(currentItem);
			}
		}

		for(int a = 0; a <storeItems.size(); a++){
			Item currentStoreItem = storeItems.get(a);
			if(currentStoreItem.getPrice() > gold){
				allItems.add(currentStoreItem);
				storeItems.remove(currentStoreItem);
			}
		}
	}
	
	public ArrayList<Item> getStoreItems(){
		
		return storeItems;
	}
	
	public String getBestWeapon(){
		int best = 0;
		Item bestWeaponSoFar =  null;
		for(int x = 0; x < inventory.size(); x++){
			if(inventory.get(x).getIncreaseDamage() > best){
				bestWeaponSoFar = inventory.get(x);
				best = inventory.get(x).getIncreaseDamage();
			}
		}
		if(bestWeaponSoFar != null){
			return bestWeaponSoFar.getName();
		}
		else{
			return "";
		}
	}
	
	public String getBestArmor(){
		int best = 0;
		Item bestArmorSoFar =  null;
		for(int x = 0; x < inventory.size(); x++){
			if(inventory.get(x).getIncreaseDefense() > best){
				bestArmorSoFar = inventory.get(x);
				best = inventory.get(x).getIncreaseDefense();
			}
		}
		if(bestArmorSoFar != null){
			return bestArmorSoFar.getName();
		}
		else{
			return "";
		}
	}
	
	public String getBestAmulet(){
		int best = 0;
		Item bestAmuletSoFar =  null;
		for(int x = 0; x < inventory.size(); x++){
			if(inventory.get(x).getIncreaseHealth() > best){
				bestAmuletSoFar = inventory.get(x);
				best = inventory.get(x).getIncreaseHealth();
			}
		}
		if(bestAmuletSoFar != null){
			return bestAmuletSoFar.getName();
		}
		else{
			return "";
		}
	}

	/*public void createQuests(){
		aMellowMeadow meadow = new aMellowMeadow();
		allForestQuests.add(meadow);

		TrollValley valley = new TrollValley();
		allForestQuests.add(valley);
	}*/

	public void checkQuests(){
		if(allForestQuests.get(0).isUnlocked() == false){
			for(int p = 0; p < inventory.size(); p++){
				String name = inventory.get(p).getName();
				if(name.equals("Wood Sword")){
					unlockNextQuest();
				}

			}
		}

	}

	public void unlockNextQuest(){
		for(int i = 0; i < allForestQuests.size(); i++){
			if(allForestQuests.get(i).isUnlocked() == false){
				allForestQuests.get(i).unlockQuest();
				return;
			}
		}
	}
	
	public void finishQuest(boolean won, Quest selectedQuest){
		if(won == true){
			gold = gold + selectedQuest.getReward();
			unlockNextQuest();
			character.resetHealth();
		}
		else{
			lastHealthUpdate = new Date();
		}
		
	}

	private void updateRecoveryTime(){
		if(character.isAlive() == false){
			long time = new Date().getTime() - lastHealthUpdate.getTime();
			if(time > recoveryTime){
				character.resetHealth();
			}
		}
	}


	public ArrayList<Item> getItemsInStore(){
		return storeItems;
	}

	public ArrayList<Item> getInventory(){
		return inventory;
	}

	public Character getStats(){
		return character;
	}

	public Character getCharacter(){
		return character;
	}

	public int getRecoveryTime(){
		long time = new Date().getTime() - lastHealthUpdate.getTime();
		return (int) (recoveryTime/1000 - time/1000);
	}

	public void giveMeMoney(){
		//cheat for testing
		gold = gold + 1000;
	}
	
	public ArrayList<Quest> getForestQuests(){
		return allForestQuests;
	}
	
	public void createAbilities(){
		//Only activates first one because it is in damage category
		Ability sheepSpell = new Ability("BAHHH",Assets.sheepIcon, 0, 300, Category.DAMAGE, 0);
		selectedAbilities[0] = sheepSpell;
		selectedAbilities[1] = sheepSpell;
		selectedAbilities[2] = sheepSpell;
		
		 Log.d("YOLO", "SHEEP IMG "+  Assets.sheepIcon);
	     Log.d("YOLO", "UP IMG "+  Assets.upArrow);
		
		Ability shield = new Ability("Shield",Assets.shieldIcon,  10, 1000, Category.DEFENSIVE, 100);
		Ability heal = new Ability("Heal",Assets.healIcon, 10, 1000, Category.HEALING, 100);
		Ability fireball = new Ability("Fireball",Assets.fireballIcon, 10, 2000, Category.DAMAGE, 1000);
		
		skillTree = new SkillTree(shield, heal, fireball);
		
		try {
		    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
		           Candybox.activity.getAssets().open("Skills.txt")));
		    String inputString;               
		    while ((inputString = inputReader.readLine()) != null) {
		    	String[] tokens = inputString.split(";");
		    	skillTree.add(tokens[0], tokens[1], Assets.hashmap.get(tokens[2]), Integer.parseInt(tokens[3]),
		    			Integer.parseInt(tokens[4]), Category.valueOf(tokens[5]), Integer.parseInt(tokens[6]));

		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		
		
	}
	
	public Ability[] getAbilities(){
		return selectedAbilities;
	}
	
	public void createEnemies(){
		try {
		    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
		           Candybox.activity.getAssets().open("Enemies.txt")));
		    String inputString;               
		    while ((inputString = inputReader.readLine()) != null) {
		    	String[] tokens = inputString.split(";");
		    	Character addedCharacter = new Character(tokens[0], Integer.parseInt(tokens[1]), 
		    			Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]));
		    	allEnemies.put(tokens[0], addedCharacter);

		    	
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public void createQuests(){
		try {
		    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
		           Candybox.activity.getAssets().open("Quests.txt")));
		    String inputString;               
		    while ((inputString = inputReader.readLine()) != null) {
		    	String[] questTokens = inputString.split(";");
		    	String enemiesString = questTokens[4];
		    	ArrayList<Character> enemies = new ArrayList<Character>();
		    	String[] enemyTokens = enemiesString.split("~");
		    	
				for(int x = 0; x < enemyTokens.length; x++){
					String[] enemyDetailTokens = enemyTokens[x].split("#");
		
					Character enemy = new Character(allEnemies.get(enemyDetailTokens[0]));
					for(int a = 0; a < Integer.parseInt(enemyDetailTokens[1]); a++){
						enemies.add(enemy);
						
					}
				}
				
				Quest quest = new Quest(questTokens[0], Integer.parseInt(questTokens[2]), 
						Integer.parseInt(questTokens[3]), enemies);
				//Eventually look at catergory creation	
				allForestQuests.add(quest);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

}
