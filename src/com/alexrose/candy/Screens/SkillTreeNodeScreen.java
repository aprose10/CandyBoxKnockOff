package com.alexrose.candy.Screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.alexrose.candy.Assets;
import com.alexrose.candy.Button;
import com.alexrose.candy.Candybox;
import com.alexrose.candy.SkillTreeAbilityButton;
import com.alexrose.candy.SkillTreeNode;
import com.alexrose.framework.Game;
import com.alexrose.framework.Graphics;
import com.alexrose.framework.Input.TouchEvent;

public class SkillTreeNodeScreen extends ButtonScreen {
	private Paint paint;
	private Paint abilitiesPaint;
	private Paint namePaint;
	private SkillTreeNode node;
	public SkillTreeNodeScreen(Game game, SkillTreeNode node) {
		super(game, true, false);
		this.node = node;

		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		namePaint = new Paint();
		namePaint.setTextSize(50);
		namePaint.setTextAlign(Paint.Align.CENTER);
		namePaint.setAntiAlias(true);
		namePaint.setColor(Color.WHITE);

		if(node.getIsUnlocked() == false){
			if(Candybox.game.getCrystals() >= node.getAbility().getPrice()){
				Button buyButton = new Button(25, 470, Assets.button, "Buy");
				buttons.add(buyButton);
			}else{
				Button buyButton = new Button(25,470,Assets.buttonLOCKED, "Buy");
				buttons.add(buyButton);
			}
		}

		if(node.getParentNode().getAbility() != null){
			Button upButton = new Button(300, 50, Assets.skillTreeUpArrow, "  ");
			buttons.add(upButton);
		}

		if(node.getIsUnlocked() == true && 
				Candybox.game.isEquipped(node.getAbility()) == false){
			Button equipButton = new Button(25, 470, Assets.button, "Equip");
			buttons.add(equipButton);
		}

		ArrayList<SkillTreeNode> children = node.getChildren();
		for(int a = 0; a < children.size(); a++){
			if(children.size() == 1){
				SkillTreeAbilityButton buttonAbility1 = new SkillTreeAbilityButton(190, 600, children.get(a).getAbility().getImage(),
						children.get(a).getAbility().getName());
				buttons.add(buttonAbility1);
			}

			else if(children.size() == 2){
				SkillTreeAbilityButton buttonAbility2 = new SkillTreeAbilityButton(110 + a*160, 600, children.get(a).getAbility().getImage(),
						children.get(a).getAbility().getName());
				buttons.add(buttonAbility2);
			}

			else if(children.size() == 3){
				SkillTreeAbilityButton buttonAbility3 = new SkillTreeAbilityButton(70 + a*120, 600, children.get(a).getAbility().getImage(),
						children.get(a).getAbility().getName());
				buttons.add(buttonAbility3);
			}


		}

	}

	public void updateRunning(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			for (Button button : buttons) {

				if(button.inButtonBounds(event) == true){

					if(button.name == "  "){
						//up button
						game.setScreen(new SkillTreeNodeScreen(game, node.getParentNode()));
						return;
					}
					else if(button.name == ""){
						//back button
						game.setScreen(new CharacterScreen(game));
						return;
					}
					else if(button.getName() == "Buy"){
						if(Candybox.game.getCrystals() >= node.getAbility().getPrice()){
							node.unlock();
							Candybox.game.spendCrystals(node.getAbility().getPrice());
							Candybox.saveToParse();
							game.setScreen(new SkillTreeNodeScreen(game, node));
						}
						return;

					}
					else if(button.getName() == "Equip"){
						Candybox.game.setEquipedAbility(node.getAbility());
						Candybox.saveToParse();
						game.setScreen(new SkillTreeNodeScreen(game, node));
						return;
					}else{
						//for children
						if(node.getIsUnlocked() == true){
							game.setScreen(new SkillTreeNodeScreen
									(game, Candybox.game.getSkillTree().findNodeByAbilityName(button.getName())));
							return;
						}
					}
				}
			}
		}
	}


	public void paint(float deltaTime) {
		super.paint(deltaTime);
		Graphics g = game.getGraphics();

		g.drawString(node.getAbility().getName(), 240, 150, namePaint);
		if(node.getIsUnlocked() == false){
			g.drawString("Price: " + node.getAbility().getPrice(),100, 450, paint);
		}
		if(node.getAbility().isDamageAbility() == true){
			g.drawString("Damage: " + node.getAbility().getValue(),100, 370, paint);
		}
		if(node.getAbility().isDefensiveAbility() == true){
			g.drawString("Armour Increase: " + node.getAbility().getValue(),100, 370, paint);
		}
		if(node.getAbility().isHealingAbility() == true){
			g.drawString("Health Restored: " + node.getAbility().getValue(),100, 370, paint);
		}
		g.drawString("Cooldown (ms): " + node.getAbility().getCooldown(),100, 420, paint);
		g.drawImage(node.getAbility().getImage(), 180, 200);

	}



}
