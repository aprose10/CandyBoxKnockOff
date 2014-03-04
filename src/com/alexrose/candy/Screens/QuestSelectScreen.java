package com.alexrose.candy.Screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.alexrose.candy.Assets;
import com.alexrose.candy.Button;
import com.alexrose.candy.Candybox;
import com.alexrose.candy.Quest;
import com.alexrose.candy.QuestBattle;
import com.alexrose.framework.Game;
import com.alexrose.framework.Graphics;
import com.alexrose.framework.Screen;
import com.alexrose.framework.Input.TouchEvent;

public class QuestSelectScreen extends ButtonScreen {
	Paint paint;
	public ArrayList<Quest> quests;

	public QuestSelectScreen(Game game, ArrayList<Quest> quests) {
		super(game, true, true);

		this.quests = quests;

		// Initialize game objects here

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		int numberOfButtons = quests.size();

		for(int i = 0; i < numberOfButtons; i++){
			Button questButton = new Button (20, 125 + (120*i), ((quests.get(i).isUnlocked()) ? Assets.button : Assets.buttonLOCKED), quests.get(i).getName());
			buttons.add(questButton);
		}
		//Button firstQuestButton = new Button (20, 125, Assets.button, "Quest One");

		//buttons.add(firstQuestButton);
	}


	public void updateRunning(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			for (Button button : buttons) {

				if(button.inButtonBounds(event) == true){
					//back button
					if(button.name == ""){
						game.setScreen(new QuestCategoryScreen(game));
						return;
					}
					else{
						//for the quests
						game.setScreen(new QuestBattleScreen(game, createQuestBattle(button.name)));
						return;
					}

				}
			}
		}
	}


	@Override
	public void paint(float deltaTime) {
		super.paint(deltaTime);
		Graphics g = game.getGraphics();
		g.drawString("Stamina: 0", 5, 35, paint);

	}

	public QuestBattle createQuestBattle(String name){
		for(int x = 0; x < quests.size(); x++){
			if(quests.get(x).getName() == name){
				return new QuestBattle(quests.get(x), Candybox.game.getAbilities());
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

