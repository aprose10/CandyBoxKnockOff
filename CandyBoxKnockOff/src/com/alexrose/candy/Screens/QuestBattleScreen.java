package com.alexrose.candy.Screens;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.alexrose.candy.Assets;
import com.alexrose.candy.Button;
import com.alexrose.candy.CandyBoxGame;
import com.alexrose.candy.Candybox;
import com.alexrose.candy.QuestBattle;
import com.alexrose.framework.Game;
import com.alexrose.framework.Graphics;
import com.alexrose.framework.Screen;
import com.alexrose.framework.Input.TouchEvent;

public class QuestBattleScreen extends Screen {
	public QuestBattle battle;
	public Button winLoseButton;
	public boolean isBattleDone = false;
	boolean battleResult;
	Paint paint;

	public QuestBattleScreen(Game game, final QuestBattle battle) {
		super(game);
		this.battle = battle;

		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

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
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			if(isBattleDone == true && winLoseButton.inButtonBounds(event) == true){
				Candybox.game.finishQuest(battleResult, battle.getQuest());
				game.setScreen(new PrimaryScreen(game));
			}
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 480, 800, Color.BLACK);
		g.drawString("Step: " + battle.getStepSlot() + " of " + battle.getTotalSteps(), 10, 400, paint);
		if(battle.getCurrentStep().containsMonster() == true){
			g.drawString("You have encountered a monster!", 10, 450, paint);
			g.drawString("Encounter: " + battle.getEnemy().getName(), 10, 500, paint);
			g.drawString("Enemy's Health: " + battle.getEnemy().getCurrentHealth() + " (Defense: " + battle.getEnemy().getCurrentDefense() + ")",10,600,paint);
		}
		else{
			g.drawString("This spot seems to be empty...", 10, 450,paint);
		}

		g.drawString("Hero's Health: " + Candybox.game.getCharacter().getCurrentHealth() + " (Defense: " + Candybox.game.getCharacter().getCurrentDefense() + ")", 10, 550, paint);

		if(isBattleDone == true){
			winLoseButton.drawButton(g);
		}

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
