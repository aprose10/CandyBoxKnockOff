package com.alexrose.candy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import com.alexrose.candy.Ability.Category;

import android.util.Log;


public class CandyBoxGame{
	private String farthestUnlockedQuest;
	private int gold;
	private int crystals;
	private int recoveryTime = 10000;
	private Date lastHealthUpdate;
	private ArrayList<Item> allItems = new ArrayList<Item>();
	private ArrayList<Item> storeItems = new ArrayList<Item>();
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private Ability[] selectedAbilities = new Ability[3];
	private static Character character;
	private HashMap<String, ArrayList<Quest>> allQuests = new HashMap<String, ArrayList<Quest>>();
	private SkillTree skillTree;
	private HashMap<String, Character> allEnemies =  new HashMap<String, Character>();
	private float timePassed;
	private LinkedList<Date> clickDates;

	public CandyBoxGame(int gold, int crystals, String nameOfFarthestQuest, List<Object> itemNamesInInventory, List<Object> purchasedAbilityNames, List<Object> selectedAbilities2){
		character = new Character("Hero", 75,25,50);
		this.gold = gold + 99;
		this.crystals = crystals;
		clickDates = new LinkedList<Date>();
		createItemsInGame();
		createAbilities();
		createEnemies();
		createQuests();
		farthestUnlockedQuest = nameOfFarthestQuest;
		unlockAllUnlockedQuests(farthestUnlockedQuest);
		givePurchasedItems(itemNamesInInventory);
		equipSelectedAbilities(selectedAbilities2);
		givePurchasedAbilities(purchasedAbilityNames);
	}

	public void update(float deltaTime){

		increaseGold(deltaTime);
		updateRecoveryTime();
		checkItemsInShop();
		checkQuests();

	}

	public void increaseGold(float deltaTime){
		timePassed = timePassed + deltaTime;
		if(timePassed >= 100){
			gold = (int) (gold + 1);
			timePassed = 0;
		}

	}
	
	public void giveOneGold(){
		Date currentClick = new Date();
		if(clickDates.size() < 3){
			gold = gold +1;
			clickDates.add(currentClick);
		}else if( currentClick.getTime() - clickDates.peek().getTime() > 5000){
			gold = gold +1;
			clickDates.pop();
			clickDates.add(currentClick);
		}
	}
	
	public int getGold(){
		return gold;
	}

	public void addCrystals(int x){
		crystals = crystals + x;
	}

	public void spendCrystals(int x){
		crystals = crystals - x;
	}

	public int getCrystals(){
		return crystals;
	}

	public void purchaseItem(Item purchasedItem, boolean shouldCostGold){
		storeItems.remove(purchasedItem);
		if(shouldCostGold == true){
			gold = gold - purchasedItem.getPrice();
		}
		inventory.add(purchasedItem);
		character.updateStats(purchasedItem);
		update(0);
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


	public void checkQuests(){
		if(allQuests.get("Forest").get(0).isUnlocked() == false){
			for(int p = 0; p < inventory.size(); p++){
				String name = inventory.get(p).getName();
				if(name.equals("Wood Sword")){
					unlockNextQuest(false);
				}

			}
		}

	}

	public void unlockNextQuest(boolean recieveCrystal){
		if(recieveCrystal == true){
			crystals = crystals+1;
		}
		Object[] setOfKeys = getQuestCategories().toArray();
		for(int a = 0; a<setOfKeys.length;a++){
			ArrayList<Quest> questsInSet = getQuests((String) setOfKeys[a]);
			for(int i = 0; i<questsInSet.size();i++){
				if(questsInSet.get(i).isUnlocked() == false){
					questsInSet.get(i).unlockQuest();
					farthestUnlockedQuest = questsInSet.get(i).getName();
					return;
				}
			}
		}
	}

	public void unlockAllUnlockedQuests(String farthestQuest){
		if(farthestQuest == null || farthestQuest.equals("")){
			return;
		}
		Object[] setOfKeys = getQuestCategories().toArray();
		for(int a = 0; a<setOfKeys.length;a++){
			ArrayList<Quest> questsInSet = getQuests((String) setOfKeys[a]);
			for(int i = 0; i<questsInSet.size();i++){
				questsInSet.get(i).unlockQuest();
				if(questsInSet.get(i).getName().equals(farthestQuest)){
					return;
				}
			}
		}
	}


	public void unlockNextQuestIfNecessary(Quest selectedQuest){
		if(selectedQuest.getName().equals(farthestUnlockedQuest)){
			unlockNextQuest(true);
		}
	}

	public void finishQuest(boolean won, Quest selectedQuest){
		if(won == true){
			gold = gold + selectedQuest.getReward();
			unlockNextQuestIfNecessary(selectedQuest);
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

	public ArrayList<Quest> getQuests(String category){
		return allQuests.get(category);
	}

	public void createAbilities(){
		Log.d("YOLO", "Creating abilities ...");
		Ability sheepSpell = new Ability("BAHHH",Assets.sheepIcon, 0, 300, Category.DAMAGE, 0);
		Ability sheepSpell2 = new Ability("BAHHH",Assets.sheepIcon2, 0, 300, Category.DEFENSIVE, 0);
		Ability sheepSpell3 = new Ability("BAHHH",Assets.sheepIcon3, 0, 300, Category.HEALING, 0);
		selectedAbilities[0] = sheepSpell;
		selectedAbilities[1] = sheepSpell2;
		selectedAbilities[2] = sheepSpell3;

		Ability shield = new Ability("Shields Up",Assets.shieldIcon,  2, 1000, Category.DEFENSIVE, 100);
		Ability heal = new Ability("Bandages",Assets.healIcon, 2, 1000, Category.HEALING, 100);
		Ability fireball = new Ability("Fireball",Assets.fireballIcon, 2, 2000, Category.DAMAGE, 1000);

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

	public ArrayList<String> getSelectedAbilitiesNames(){
		ArrayList<String> names = new ArrayList<String>();
		for(int a = 0; a < selectedAbilities.length; a++){
			names.add(selectedAbilities[a].getName());
		}
		return names;
	}

	public void equipSelectedAbilities(List<Object> savedSelectedAbilities){
		if(savedSelectedAbilities == null){
			return;
		}
		for(int a = 0; a < savedSelectedAbilities.size(); a++){
			String abilityName = (String)savedSelectedAbilities.get(a);
			if(!abilityName.equals("BAHHH")){
				Ability selectedAbility = skillTree.findNodeByAbilityName(abilityName).getAbility();
				if(selectedAbility.isDamageAbility() == true){
					selectedAbilities[0]  = selectedAbility;
				}

				if(selectedAbility.isDefensiveAbility() == true){
					selectedAbilities[1] = selectedAbility;
				}

				if(selectedAbility.isHealingAbility() == true){
					selectedAbilities[2] = selectedAbility;
				}
			}
		}
	}

	public void createEnemies(){
		Log.d("YOLO", "Creating enemies ...");
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

	public boolean isEquipped(Ability ability){
		for(int a = 0; a < selectedAbilities.length; a++){
			if(selectedAbilities[a].getName() == ability.getName()){
				return true;
			}
		}
		return false;
	}

	public void setEquipedAbility(Ability ability){
		if(ability.isDamageAbility() == true){
			selectedAbilities[0] = ability;
		}

		if(ability.isDefensiveAbility() == true){
			selectedAbilities[1] = ability;
		}

		if(ability.isHealingAbility() == true){
			selectedAbilities[2] = ability;
		}
	}

	public void createQuests(){
		Log.d("YOLO", "Creating quests ...");
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
				if(allQuests.containsKey(questTokens[1]) == true){
					allQuests.get(questTokens[1]).add(quest);
				}else{
					ArrayList<Quest> questArrayList = new ArrayList<Quest>();
					questArrayList.add(quest);
					allQuests.put(questTokens[1], questArrayList);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Set<String> getQuestCategories(){
		return allQuests.keySet();
	}

	public SkillTree getSkillTree(){
		return skillTree;
	}

	public boolean areItemsStocked(){
		if(storeItems.size()> 0){
			return true;
		}
		return false;
	}

	public boolean isFirstQuestUnlocked(){
		if(allQuests.get("Forest").get(0).isUnlocked() == true){
			return true;
		}
		return false;
	}

	public boolean hasBeatFirstQuest(){
		if(allQuests.get("Forest").get(1).isUnlocked() == true){
			return true;
		}
		return false;
	}

	public String getFarthestQuest(){
		return farthestUnlockedQuest;
	}

	public ArrayList<String> getInventoryNames(){
		ArrayList<String> names = new ArrayList<String>();
		for(int a = 0; a < inventory.size(); a++){
			names.add(inventory.get(a).getName());
		}
		return names;
	}
	public void givePurchasedItems(List<Object> items){
		if(items == null){
			return;
		}

		for(int a = 0; a < items.size(); a ++){
			String itemName = (String)items.get(a);
			//Log.d("YOLO", "Name of Item: " + itemName);
			for(int i = 0; i < allItems.size(); i++){
				if(allItems.get(i).getName().equals(itemName)){
					//Log.d("YOLO", "Purchased Item: " + allItems.get(i).getName());
					purchaseItem(allItems.get(i), false);
					allItems.remove(i);
					break;
				}
			}
			for(int i = 0; i < storeItems.size(); i++){
				if(storeItems.get(i).getName().equals(itemName)){
					//Log.d("YOLO", "Purchased Item: " + storeItems.get(i).getName());
					purchaseItem(storeItems.get(i), false);
					break;
				}
			}
		}
	}

	public ArrayList<String> getPurchasedAbilities(){
		return skillTree.determinePurchased();
	}

	public void givePurchasedAbilities(List<Object> abilities){
		if(abilities == null){
			return;
		}
		for(int a = 0; a < abilities.size(); a++){
			String abilityName = (String)abilities.get(a);
			skillTree.findNodeByAbilityName(abilityName).unlock();
		}
	}

}
