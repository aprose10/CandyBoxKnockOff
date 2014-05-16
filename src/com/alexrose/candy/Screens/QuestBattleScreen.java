package com.alexrose.candy.Screens;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.alexrose.candy.Ability;
import com.alexrose.candy.AbilityButton;
import com.alexrose.candy.Assets;
import com.alexrose.candy.Button;
import com.alexrose.candy.CandyBoxGame;
import com.alexrose.candy.Candybox;
import com.alexrose.candy.QuestAbility;
import com.alexrose.candy.QuestBattle;
import com.alexrose.framework.Game;
import com.alexrose.framework.Graphics;
import com.alexrose.framework.Image;
import com.alexrose.framework.Screen;
import com.alexrose.framework.Input.TouchEvent;
/*
 * 
 * 
 * 
 * anyskill use resets all other skills
 */
public class QuestBattleScreen extends ButtonScreen {
	public QuestBattle battle;
	public Button winLoseButton;
	public boolean isBattleDone = false;
	boolean battleResult;
	Paint paint;
	Paint namePaint;

	public QuestBattleScreen(Game game, final QuestBattle batl) {
		super(game, false, false);
		this.battle = batl;

		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		
		namePaint = new Paint();
		namePaint.setTextSize(30);
		namePaint.setTextAlign(Paint.Align.CENTER);
		namePaint.setAntiAlias(true);
		namePaint.setColor(Color.WHITE);

		for(int a = 0; a < battle.getQuestAbilities().size(); a ++){
			AbilityButton skillButton = new AbilityButton(a, battle.getQuestAbilities().get(a));
			buttons.add(skillButton);
		}

		Thread thread = new Thread()
		{
			@Override
			public void run() {
				battleResult = battle.doQuest();
				if(battleResult == true){
					winLoseButton = new Button(20, 650, Assets.button, "You Win");
				}
				else{
					winLoseButton = new Button(20, 650, Assets.button, "You Lose");
				}
				isBattleDone = true;
			}
		};

		thread.start();
	}

	@Override
	public void update(float deltaTime) {
		//call new function from questBattle(at the bottom)
		if(battle != null && isBattleDone == false){
			battle.abilitiesCountDown(deltaTime);
		}

		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			if(isBattleDone == true && winLoseButton.inButtonBounds(event) == true){
				Candybox.game.finishQuest(battleResult, battle.getQuest());
				game.setScreen(new PrimaryScreen(game));
			}

			if(isBattleDone == false){
				for (Button button : buttons) {

					if(button.inButtonBounds(event) == true){
						QuestAbility currentAbility = getAbility(button.image);

						if(currentAbility.isCoolingDown() == false){
							battle.setAbility(currentAbility);
							Log.d("YOLO", currentAbility.getAbility().getName());
						}

					}
				}
			}
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 490, 810, Color.BLACK);
		g.drawString("Step: " + battle.getStepSlot() + " of " + battle.getTotalSteps(), 240, 100, namePaint);
		if(battle.getCurrentStep().containsMonster() == true){
			g.drawString("You have encountered a monster!", 10, 365, paint);
			//crashed here once, tried to fix with if statement
			if(battle.getEnemy()!=null){
				g.drawString("Encounter: " + battle.getEnemy().getName(), 10, 400, paint);
			}
			if(battle.getEnemy()!=null){
				g.drawString("Enemy's Health: " + battle.getEnemy().getCurrentHealth(),10,450,paint);
				//crashed here once
				g.drawString("Enemy's Defense: " + battle.getEnemy().getCurrentDefense(),10,485,paint);
			}
			if(battle.getSelectedAbility() != null){
				g.drawString("You have activated: " + battle.getSelectedAbility().getAbility().getName(), 10, 630, paint);
			}

		}
		else{
			g.drawString("This spot seems to be empty...", 10, 150,paint);
		}

		g.drawString("Hero's Health: " + Candybox.game.getCharacter().getCurrentHealth(), 10, 550, paint);
		g.drawString("Hero's Defense: " + Candybox.game.getCharacter().getCurrentDefense(),10,585,paint);

		if(isBattleDone == true){
			winLoseButton.drawButton(g);
		}
		else{
			g.drawImage(Assets.border, 0, 645);
			paintOnlyButtons(deltaTime);
		}
	}

	public QuestAbility getAbility(Image image){
		for(int a = 0; a <battle.getQuestAbilities().size(); a ++){
			if(image == battle.getQuestAbilities().get(a).getAbility().getImage()){
				return battle.getQuestAbilities().get(a);
			}
		}

		return null;
	}


	@Override
	public void pause() {


	}

	@Override
	public void resume() {


	}

	@Override
	public void dispose() {


	}

	@Override
	public void backButton() {


	}

}
