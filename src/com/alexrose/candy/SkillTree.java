package com.alexrose.candy;

import java.util.ArrayList;

import android.util.Log;

import com.alexrose.candy.Ability.Category;
import com.alexrose.framework.Image;



public class SkillTree {
	private SkillTreeNode rootNode;
	public SkillTree(Ability ability1, Ability ability2, Ability ability3){
		
		rootNode = new SkillTreeNode(null, null);
		
		SkillTreeNode firstNode = new SkillTreeNode(ability1, rootNode);
		firstNode.unlock();
		rootNode.addChild(firstNode);
		SkillTreeNode secondNode = new SkillTreeNode(ability2, rootNode);
		secondNode.unlock();
		rootNode.addChild(secondNode);
		SkillTreeNode thirdNode = new SkillTreeNode(ability3, rootNode);
		thirdNode.unlock();
		rootNode.addChild(thirdNode);
	}
	
	public void add(String parent, String name, Image img, int price, int cooldown, Category category, int value){
		//Log.d("YOLO", "Parent Node: " + parent);
		//Log.d("YOLO", "Child Node: " + name);
		Ability createdAbility = new Ability(name, img, price, cooldown, category, value);
		SkillTreeNode parentNode = findNodeByAbilityName(parent);
		SkillTreeNode newChildNode = new SkillTreeNode(createdAbility, parentNode);
		parentNode.addChild(newChildNode);
	}
	
	public SkillTreeNode findNodeByAbilityName(String name){

		return checkNodeByName(rootNode, name);
	}

	
	private SkillTreeNode checkNodeByName(SkillTreeNode givenNode, String name){
		if(givenNode.getAbility() != null && givenNode.getAbility().getName().equals(name)){
			return givenNode;
		}else{
			ArrayList<SkillTreeNode> children = givenNode.getChildren();
			for(int a = 0; a < children.size(); a++){
				SkillTreeNode node = checkNodeByName(children.get(a), name);
				if(node != null){
					return node;
				}
			}
			return null;
		}
	}
}
