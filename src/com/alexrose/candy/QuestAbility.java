package com.alexrose.candy;

public class QuestAbility {
	private Ability ability;
	private float coolDown;
	
	public QuestAbility(Ability ability){
		this.ability = ability;
		coolDown = ability.getCooldown();
	}
	
	public Ability getAbility(){
		return ability;
	}
	
	public float getCoolDown(){
		return coolDown;
	}
	
	public void useAbility(){
		coolDown = ability.getCooldown();
	}
	
	public void countDown(float delta){
		coolDown = coolDown - delta;
	}
	
	public Boolean isCoolingDown(){
		if(coolDown <= 0){
			return false;
		}
		return true;
	}

}
