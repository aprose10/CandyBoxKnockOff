package com.alexrose.candy;
import java.util.ArrayList;
import java.util.Random;

import android.util.Log;


public class QuestBattle {
	private Quest quest;
	private Random random = new Random();
	private boolean enemiesTurn;
	private int stepSlot;
	private QuestStep currentStep;
	private QuestAbility selectedAbility;
	private ArrayList<QuestAbility> questAbilities = new ArrayList<QuestAbility>();
	private int abilityDamage = 0;
	
	public QuestBattle(Quest quest, Ability[] abilities){
		this.quest = new Quest(quest);
		for(int a = 0; a < abilities.length; a++){
			QuestAbility questAbility = new QuestAbility (abilities[a]);
			
			questAbilities.add(questAbility);
		}
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

		return dealtDamage + abilityDamage;
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
					useSelectedAbility();

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
						abilityDamage = 0;
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

	public void setAbility(QuestAbility currentAbility){
		selectedAbility = currentAbility;
	}

	public void useSelectedAbility(){
		if(selectedAbility != null){
			if(selectedAbility.getAbility().isHealingAbility() == true){
				Candybox.game.getCharacter().healCharacter(selectedAbility.getAbility().getValue());
				selectedAbility.useAbility();
				selectedAbility = null;
			}
			else if(selectedAbility.getAbility().isDefensiveAbility() == true && enemiesTurn == true){
				Candybox.game.getCharacter().addDefense(selectedAbility.getAbility().getValue());
				selectedAbility.useAbility();
				selectedAbility = null;
			}
			else if(selectedAbility.getAbility().isDamageAbility() == true && enemiesTurn == false){
				abilityDamage = selectedAbility.getAbility().getValue();
				selectedAbility.useAbility();
				selectedAbility = null;
			}	
		}
	}

	public QuestAbility getSelectedAbility(){
		return selectedAbility;
	}

	public void abilitiesCountDown(float countDown){
		for(int a = 0; a < questAbilities.size(); a ++){
			questAbilities.get(a).countDown(countDown);
		}
	}

	public ArrayList<QuestAbility> getQuestAbilities(){
		return questAbilities;
	}

}
