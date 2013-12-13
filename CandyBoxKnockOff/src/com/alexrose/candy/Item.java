package com.alexrose.candy;

public class Item {
	private String name;
	private int price;
	private int increaseHealth;
	private int increaseDamage;
	private int increaseDefense;
	Item(String name, int price, int increaseHealth, int increaseDamage, int increaseDefense){
		this.name = name;
		this.price = price;
		this.increaseDamage = increaseDamage;
		this.increaseDefense = increaseDefense;
		this.increaseHealth = increaseHealth;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPrice(){
		return price;
	}
	public int getIncreaseHealth(){
		return increaseHealth;
	}
	
	public int getIncreaseDamage(){
		return increaseDamage;
	}
	
	public int getIncreaseDefense(){
		return increaseDefense;
	}
	
}
