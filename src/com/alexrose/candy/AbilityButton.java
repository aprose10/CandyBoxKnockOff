package com.alexrose.candy;

import com.alexrose.framework.Graphics;
import com.alexrose.framework.Image;

public class AbilityButton extends Button{
private QuestAbility questAbility;
	public AbilityButton(int pos, QuestAbility questAbility) {
		super(40+(pos*150), 700, questAbility.getAbility().getImage(), "");
	this.questAbility = questAbility;
	}
	
	public void drawButton(Graphics g){
		//look for countdown on ability see if available
		if(questAbility.isCoolingDown() == false){
			g.drawImage(image, positionX, positionY);
			//g.drawString(name, 240, positionY + 75, buttonPaint); 
		}
		else{
			g.drawString(Integer.toString((int)Math.round(questAbility.getCoolDown())), positionX + 40, positionY + 75, buttonPaint);
		}
	}
	

}
