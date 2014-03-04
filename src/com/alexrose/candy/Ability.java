package com.alexrose.candy;

import com.alexrose.framework.Image;

public class Ability {

	public enum Category {
		HEALING, DEFENSIVE, DAMAGE
	}

	private int value;
	private Category category;
	private int cooldown;
	private String name;
	private int price;
	private Image image;

	Ability(String name, Image img, int price, int cooldown, Category category, int value){
		this.name = name;
		this.price = price;
		this.cooldown = cooldown;
		this.value = value;
		this.category = category;
		this.image = img;
	}

	public Image getImage(){
		return image;
	}

	public String getName(){
		return name;
	}

	public int getPrice(){
		return price;
	}

	public int getCooldown(){
		return cooldown;
	}
	public int getValue(){
		return value;
	}

	public Boolean isHealingAbility(){
		if(category == Category.HEALING){
			return true;
		}
		else{
			return false;
		}
	}

	public Boolean isDefensiveAbility(){
		if(category == Category.DEFENSIVE){
			return true;
		}
		else{
			return false;
		}
	}

	public Boolean isDamageAbility(){
		if(category == Category.DAMAGE){
			return true;
		}
		else{
			return false;
		}
	}
}
