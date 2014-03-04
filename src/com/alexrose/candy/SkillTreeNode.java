package com.alexrose.candy;
import java.util.ArrayList;



public class SkillTreeNode {
	private Ability ability;
	private boolean isHidden;
	private ArrayList<SkillTreeNode> children = new ArrayList<SkillTreeNode>();
	private boolean isUnlocked;
	
	public SkillTreeNode(Ability ability){
		this.ability = ability;
		isHidden = true;
		isUnlocked = false;
	}
	
	public ArrayList<SkillTreeNode> getChildren(){
		return children;
	}
	
	public void addChild(SkillTreeNode childNode){
		children.add(childNode);
	}
	
	public Ability getAbility(){
		return ability;
	}
	
	public boolean getIsUnlocked(){
		return isUnlocked;
	}
	
	public boolean getIsHidden(){
		return isHidden;
	}
	
	public void unlock(){
		isUnlocked = true;
	}
	
	public void makeVisible(){
		isHidden = false;
	}
}
