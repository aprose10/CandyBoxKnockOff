package com.alexrose.candy.Screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.alexrose.candy.Assets;
import com.alexrose.candy.Button;
import com.alexrose.candy.Candybox;
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

		Button forestButton = new Button (20, 125, Assets.button, "Forest");

		buttons.add(forestButton);

	}



	public void updateRunning(float deltaTime) {

		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			for (Button button : buttons) {

				if(button.inButtonBounds(event) == true){
					if(button.name == "Forest"){
						game.setScreen(new QuestSelectScreen(game,Candybox.game.getForestQuests()));
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
		g.drawString("Stamina: 0", 5, 35, paint);


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
