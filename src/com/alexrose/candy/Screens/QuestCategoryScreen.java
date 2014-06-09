package com.alexrose.candy.Screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.graphics.Color;
import android.graphics.Paint;

import com.alexrose.candy.Assets;
import com.alexrose.candy.Button;
import com.alexrose.candy.Candybox;
import com.alexrose.candy.Quest;
import com.alexrose.candy.Screens.PrimaryScreen.GameState;
import com.alexrose.framework.Game;
import com.alexrose.framework.Graphics;
import com.alexrose.framework.Screen;
import com.alexrose.framework.Input.TouchEvent;

public class QuestCategoryScreen extends ButtonScreen {
	Paint paint;


	public QuestCategoryScreen(Game game) {
		super(game, true, true);

		// Initialize game objects here

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		Set<String> categories = Candybox.game.getQuestCategories();
		for(int a = 0; a < categories.size(); a++){
			Button myButton = new Button (20, 125 + a*120, Assets.buttonLOCKED, (String) categories.toArray()[a]);
			tryUnlockCategory(myButton, (String) categories.toArray()[a]);
			buttons.add(myButton);
		}


	}

	public void tryUnlockCategory(Button button, String category){
		ArrayList<Quest> questsInCategory = Candybox.game.getQuests(category);
		for(int a = 0; a < questsInCategory.size();a++){
			if(questsInCategory.get(a).isUnlocked() == true){
				button.enableButton();
				return;
			}
		}
	}



	public void updateRunning(float deltaTime) {

		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			for (Button button : buttons) {

				if(button.inButtonBounds(event) == true){
					if(button.name != ""){
						game.setScreen(new QuestSelectScreen(game,Candybox.game.getQuests(button.name)));
						return;
					}
					else if(button.name == ""){
						game.setScreen(new PrimaryScreen(game));
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
		//g.drawString("Stamina: 0", 5, 35, paint);


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
