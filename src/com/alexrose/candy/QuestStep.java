package com.alexrose.candy;

public class QuestStep {
	private boolean containsMonster;
	private Character enemy;
	
	public QuestStep(){
		enemy = null;
		containsMonster = false;
	}
	
	public QuestStep(QuestStep step){
		if(step.containsMonster() == true){
			containsMonster = true;
			enemy = new Character(step.getEnemy());
		}
		else{
			containsMonster = false;
			enemy = null;
		}
		
	}
	
	public void setEnemy(Character enemy){
		this.enemy = enemy;
		containsMonster = true;
	}
	
	public boolean containsMonster(){
		return containsMonster;
	}
	
	//fixing null pointer exception
	public Character getEnemy(){
		if(enemy == null){
			return new Character("", 0, 0, 0);
		}
		return enemy;
	}
}
