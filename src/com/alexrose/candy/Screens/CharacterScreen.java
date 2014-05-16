package com.alexrose.candy.Screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.alexrose.candy.Assets;
import com.alexrose.candy.Button;
import com.alexrose.candy.CandyBoxGame;
import com.alexrose.candy.Candybox;
import com.alexrose.candy.Item;
import com.alexrose.framework.Game;
import com.alexrose.framework.Graphics;
import com.alexrose.framework.Input.TouchEvent;

public class CharacterScreen extends ButtonScreen {
	Paint paint;
	Paint headingPaint;
	public CharacterScreen(Game game) {
		super(game, true, false);

		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		
		headingPaint = new Paint();
		headingPaint.setFakeBoldText(true);
		headingPaint.setTextSize(30);
		headingPaint.setTextAlign(Paint.Align.LEFT);
		headingPaint.setAntiAlias(true);
		headingPaint.setColor(Color.WHITE);
		
		Button viewDamageTreeButton = new Button(380, 450, Assets.treeButton1, " ");
		Button viewDefensiveTreeButton = new Button(380, 550, Assets.treeButton1, "  ");
		Button viewHealingTreeButton = new Button(380, 650, Assets.treeButton1, "   ");
		
		buttons.add(viewDamageTreeButton);
		buttons.add(viewDefensiveTreeButton);
		buttons.add(viewHealingTreeButton);

	}

	public void updateRunning(float deltaTime) {

		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			for (Button button : buttons) {

				if(button.inButtonBounds(event) == true){
					if(button.name == ""){
						game.setScreen(new PrimaryScreen(game));
						return;
					}
					if(button.name == " "){
						game.setScreen(new SkillTreeNodeScreen
								(game, Candybox.game.getSkillTree().findNodeByAbilityName("Fireball")));
						return;
					}
					
					if(button.name == "  "){
						game.setScreen(new SkillTreeNodeScreen
								(game, Candybox.game.getSkillTree().findNodeByAbilityName("Shields Up")));
						return;
					}
					
					if(button.name == "   "){
						game.setScreen(new SkillTreeNodeScreen
								(game, Candybox.game.getSkillTree().findNodeByAbilityName("Bandages")));
						return;
					}
				}
			}
		}
	}

	public void paint(float deltaTime) {
		super.paint(deltaTime);

		Graphics g = game.getGraphics();
		g.drawString("Your Best Equipment:", 20, 150, headingPaint);
		g.drawString("Weapon: " + Candybox.game.getBestWeapon(), 50, 200, paint);
		g.drawString("Armor: " + Candybox.game.getBestArmor(), 50, 250, paint);
		g.drawString("Amulet: " + Candybox.game.getBestAmulet(), 50, 300, paint);
		
		g.drawString("Equipped Abilities: ", 20, 400, headingPaint);
		g.drawString("Damage: " + Candybox.game.getAbilities()[0].getName(), 50, 500, paint);
		g.drawString("Defensive: " + Candybox.game.getAbilities()[1].getName(), 50, 600, paint);
		g.drawString("Healing: " + Candybox.game.getAbilities()[2].getName(), 50, 700, paint);


	}
}
