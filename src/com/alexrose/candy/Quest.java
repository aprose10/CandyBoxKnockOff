package com.alexrose.candy;
import java.util.ArrayList;


public abstract class Quest {
	
	private String name;
	private int numberOfSteps;
	private int reward;
	private boolean unlocked;
	private ArrayList<QuestStep> questSteps = new ArrayList<QuestStep>();
	
	public Quest(String name, int numberOfSteps, int reward){
		this.name = name;
		this.numberOfSteps = numberOfSteps;
		this.reward = reward;
		
		unlocked = false;
		
		createSteps();
		createEnemies();
	}
	
	public Quest(Quest quest){
		this.name = quest.getName();
		this.numberOfSteps = quest.getNumberOfSteps();
		this.reward = quest.getReward();
		
		this.questSteps = new ArrayList<QuestStep>();
		
		for(int x = 0; x< quest.getQuestSteps().size(); x++){
			QuestStep copyStep = new QuestStep(quest.getQuestSteps().get(x));
			questSteps.add(copyStep);
		}
		
	}
	
	public abstract void createEnemies();
	
	public void addEnemies(ArrayList<Character> enemies){
		for(int z = 0; z < enemies.size(); z++){
			int randomNum = 0 + (int)(Math.random()*questSteps.size());
			while(questSteps.get(randomNum).containsMonster() == true){
				randomNum = 0 + (int)(Math.random()*questSteps.size());
			}
			questSteps.get(randomNum).setEnemy(enemies.get(z));
		}
	}
	
	public String getName(){
		return name;
	}
	
	private void createSteps(){
		for(int a = 0; a < numberOfSteps; a++){
			QuestStep questStep = new QuestStep();
			questSteps.add(questStep);
		}
	}
	
	public int getNumberOfSteps(){
		return numberOfSteps;
	}
	
	public int getReward(){
		return reward;
	}
	
	public ArrayList<QuestStep> getQuestSteps(){
		return questSteps;
	}
	
	public void unlockQuest(){
		unlocked = true;
	}
	
	public boolean isUnlocked(){
		return unlocked;
	}
}
