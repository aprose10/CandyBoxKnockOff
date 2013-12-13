package com.alexrose.candy;
import java.util.Random;

import android.util.Log;


public class QuestBattle {
	private Quest quest;
	private Random random = new Random();
	private boolean enemiesTurn;
	private int stepSlot;
	private QuestStep currentStep;

	public QuestBattle(Quest quest){
		this.quest = new QuestCopy(quest);
	}
	
	private int damageDealt(int damage){
		//selects a random amount of damage based on the characters damage. does 80% to %120. Has a minimum of one damage no matter what
		int characterMinDamage = (int) Math.round(damage * .8);
		int characterMaxDamage = (int) Math.round(damage * 1.2);
		int dealtDamage;
		if(characterMaxDamage - characterMinDamage <= 0){
			dealtDamage = 1;
		}
		else{
			dealtDamage = random.nextInt(characterMaxDamage - characterMinDamage) + characterMinDamage;
		}
		
		return dealtDamage;
	}

	public boolean doQuest(){
		//Goes through steps of quest
		for(int x = 0; x < quest.getNumberOfSteps(); x++){
			stepSlot = x;
			currentStep = quest.getQuestSteps().get(x);
			Candybox.game.getCharacter().resetDefense();
			//checks if step has monster
			if(currentStep.containsMonster() == true){
				//find out what monster
				Character enemy = currentStep.getEnemy();
				//randomly selects a turn
				enemiesTurn = random.nextBoolean();
				//checks to see if the enemy is alive
				while(enemy.isAlive() == true){
					//checks if its enemy turn
					if(enemiesTurn == true){
						//sets amount of damage to deal
						int dealDamage = damageDealt(enemy.getDamage());
						//deals damage
						Candybox.game.getCharacter().takeDamage(dealDamage);
						//checks to see if character is dead
						if(Candybox.game.getCharacter().isAlive() == false){
							return false;
						}
						
					}

					else{
						//sets damage to deal
						int dealDamage = damageDealt(Candybox.game.getCharacter().getDamage());
						//deals damage to enemy
						enemy.takeDamage(dealDamage);

					}
			
					enemiesTurn = !enemiesTurn;
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						Log.d("Error", "could not pause");
					}
					//pause after every hit .25 sec
				}

			}
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				Log.d("Error", "could not pause");
			}
				//pause every step .5 sec
		}
		return true;
	}
	
	public boolean getEnemiesTurn(){
		return enemiesTurn;
	}
	
	public Character getEnemy(){
		return getCurrentStep().getEnemy();
	}
	
	public int getStepSlot(){
		return stepSlot + 1;
	}
	
	public int getTotalSteps(){
		return quest.getNumberOfSteps();
	}
	
	public QuestStep getCurrentStep(){
		if(currentStep == null){
			return quest.getQuestSteps().get(0);
		}
		return currentStep;
	}
	
	public Quest getQuest(){
		return quest;
	}

}
