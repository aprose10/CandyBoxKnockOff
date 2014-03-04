package com.alexrose.candy;
import java.util.ArrayList;
import java.util.Date;

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
	private ArrayList<Ability> selectedAbilities = new ArrayList<Ability>();
	private static Character character;
	private ArrayList<Quest> allForestQuests = new ArrayList<Quest>();
	private SkillTree skillTree;

	public CandyBoxGame(int gold){
		this.gold = gold + 150000;
		lastGoldUpdate = new Date();
		createItemsInGame();
		createQuests();
		character = new Character("Hero",10000,10,10);
		createAbilities();
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
		//price, max Health, damage, defense
		//Make items more expensive
		Item woodSword = new Item("Wood Sword", 100, 0, 12, 0);
		allItems.add(woodSword);//good
		Item ironSword = new Item("Iron Sword", 300, 0, 30, 0);
		allItems.add(ironSword);//good
		Item bronzeSword = new Item("Bronze Sword", 700, 0, 100, 0);
		allItems.add(bronzeSword);
		Item machete = new Item("Manly Machete", 750, 0, 150, 0);
		allItems.add(machete);
		Item diamondSword = new Item("Diamon Sword", 1500, 0, 300, 0);
		allItems.add(diamondSword);
		Item fireSword = new Item("Fire Sword", 2500, 0, 400, 0);
		allItems.add(fireSword);
		Item darkSword = new Item("Dark Sword", 4000, 0, 600, 0);
		allItems.add(darkSword);
		Item lightSword = new Item("Light Sword", 4000, 0, 100, 0);
		allItems.add(lightSword);
		Item earthSword = new Item("Earth Sword", 2500, 0, 300, 0);
		allItems.add(earthSword);
		Item waterSword = new Item("Water Sword", 2500, 0, 400, 0);
		allItems.add(waterSword);
		Item dragonSword = new Item("Dragon Sword", 10000, 0, 2000, 0);
		allItems.add(dragonSword);
		Item leatherArmor = new Item("Leather Armor", 200, 5, 0, 10);
		allItems.add(leatherArmor);
		Item ironArmor = new Item("Iron Armor", 600, 30, 0, 40);
		allItems.add(ironArmor);
		Item bronzeArmor = new Item("Bronze Armor", 1200, 50, 0, 80);
		allItems.add(bronzeArmor);
		Item diamondArmor = new Item("Diamond Armor", 400, 100, 0, 150);
		allItems.add(diamondArmor);
		Item steelArmor = new Item("Steel Armor", 300, 70, 0, 100);
		allItems.add(steelArmor);
		Item chainMailArmor = new Item("Chainmail Armor", 1000, 200, 0, 400);
		allItems.add(chainMailArmor);
		Item dragonArmor = new Item("Dragon Scaled Armor", 10000, 3000, 0, 5000);
		allItems.add(dragonArmor);
		Item healthNecklace = new Item("Necklace Of Health", 1000, 500, 0, 0);
		allItems.add(healthNecklace);
		Item strengthNecklace = new Item("Necklace Of Strength", 1500, 0, 500, 0);
		allItems.add(strengthNecklace);
		Item defenceNecklace = new Item("Necklace Of Defense", 1500, 0, 0, 500);
		allItems.add(defenceNecklace);
		Item godNecklace = new Item("Necklace From The Gods", 20000, 3000, 3000, 3000);
		allItems.add(healthNecklace);
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

	public void createQuests(){
		aMellowMeadow meadow = new aMellowMeadow();
		allForestQuests.add(meadow);

		TrollValley valley = new TrollValley();
		allForestQuests.add(valley);
	}

	public void checkQuests(){
		if(allForestQuests.get(0).isUnlocked() == false){
			for(int p = 0; p < inventory.size(); p++){
				String name = inventory.get(p).getName();
				if(name == "Wood Sword"){
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
		Ability shield = new Ability("Shield",Assets.shieldIcon,  10, 1000, Category.DEFENSIVE, 100);
		selectedAbilities.add(shield);
		Ability heal = new Ability("Heal",Assets.healIcon, 10, 1000, Category.HEALING, 100);
		selectedAbilities.add(heal);
		Ability fireball = new Ability("Fireball",Assets.fireballIcon, 10, 2000, Category.DAMAGE, 1000);
		selectedAbilities.add(fireball);
		
		skillTree = new SkillTree(shield, heal, fireball);
		
		skillTree.add("Fireball", "Freeze", Assets.fireballIcon, 10, 500, Category.DAMAGE, 50);
		skillTree.add("Freeze", "Freeze 2", Assets.fireballIcon, 10, 250, Category.DAMAGE, 50);
		
	}
	
	public ArrayList<Ability> getAbilities(){
		return selectedAbilities;
	}

}
