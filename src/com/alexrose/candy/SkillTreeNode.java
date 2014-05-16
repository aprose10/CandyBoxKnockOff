package com.alexrose.candy;
import java.util.ArrayList;



public class SkillTreeNode {
	private Ability ability;
	private ArrayList<SkillTreeNode> children = new ArrayList<SkillTreeNode>();
	private boolean isUnlocked;
	private SkillTreeNode parentNode;
	
	public SkillTreeNode(Ability ability, SkillTreeNode parent){
		this.ability = ability;
		isUnlocked = false;
		this.parentNode = parent;
	}
	
	public SkillTreeNode getParentNode(){
		return parentNode;
		
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

	
	public void unlock(){
		isUnlocked = true;
	}

}
