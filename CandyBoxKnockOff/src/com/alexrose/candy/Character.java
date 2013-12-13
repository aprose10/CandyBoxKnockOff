package com.alexrose.candy;

public class Character {
	private int currentHealth;
	private int maxHealth;
	private int damage;
	private int maxDefense;
	private int currentDefense;
	private String name;
	
	public Character(String name, int Health, int damage, int defense){
		this.currentHealth = Health;
		this.maxHealth = Health;
		this.damage = damage;
		this.maxDefense = defense;
		this.currentDefense = defense;
		this.name = name;
	}
	
	public Character(Character copyCharacter){
		this.currentHealth = copyCharacter.getCurrentHealth();
		this.maxHealth = copyCharacter.getMaxHealth();
		this.damage = copyCharacter.getDamage();
		this.maxDefense = copyCharacter.getMaxDefense();
		this.currentDefense = copyCharacter.getCurrentDefense();
		this.name = copyCharacter.getName();
	}
	
	public int getCurrentHealth(){
		return currentHealth;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public int getMaxDefense(){
		return maxDefense;
	}
	
	public int getCurrentDefense(){
		return currentDefense;
	}
	
	public String getName(){
		return name;
	}
	
	public void updateStats(Item purchasedItem){
		maxHealth = maxHealth + purchasedItem.getIncreaseHealth();
		currentHealth = maxHealth;
		damage = damage + purchasedItem.getIncreaseDamage();
		maxDefense = maxDefense + purchasedItem.getIncreaseDefense();
		currentDefense = maxDefense;
	}
	
	public void resetDefense(){
		currentDefense = maxDefense;
	}
	
	public void resetHealth(){
		currentHealth = maxHealth;
	}
	
	public void takeDamage(int damageTaken){
		int extraDamage = currentDefense - damageTaken;
		if(extraDamage < 0){
			extraDamage = extraDamage * -1;
			currentHealth = currentHealth - extraDamage;
		}
		
		currentDefense = currentDefense - damageTaken;
		if(currentDefense <= 0){
			currentDefense = 0;
		}
		
		if(currentHealth <= 0){
			currentHealth = 0;
		}
	}
	
	public boolean isAlive(){
		if(currentHealth == 0){
			return false;
		}
		else{
			return true;
		}
	}
}
