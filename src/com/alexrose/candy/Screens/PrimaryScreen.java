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
import com.alexrose.candy.SaveGameState;
import com.alexrose.framework.Game;
import com.alexrose.framework.Graphics;
import com.alexrose.framework.Image;
import com.alexrose.framework.Screen;
import com.alexrose.framework.Input.TouchEvent;

public class PrimaryScreen extends ButtonScreen {
	enum GameState {
		Ready, Running, GameOver
	}

	GameState state = GameState.Running;
	Paint paint;
	Paint gameOverPaint;
	Paint buttonPaint;

	public PrimaryScreen(Game game) {
		super(game, false, false);

		// Initialize game objects here

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		gameOverPaint = new Paint();
		Typeface tf = Typeface.create("Helvetica",Typeface.BOLD);
		gameOverPaint.setTypeface(tf);
		gameOverPaint.setTextSize(80);
		gameOverPaint.setTextAlign(Paint.Align.CENTER);
		gameOverPaint.setAntiAlias(true);
		gameOverPaint.setColor(Color.GREEN);


		Button storeButton = new Button(20, 375, Assets.button, "Store");
		Button questButton = new Button(20, 495, (Candybox.game.getCharacter().isAlive() ? Assets.button : Assets.buttonLOCKED), "Quests");
		Button characterButton = new Button(20, 615, Assets.button, "Character");

		buttons.add(storeButton);
		buttons.add(questButton);
		buttons.add(characterButton);

		/*SaveGameState gameState = Candybox.loadGame();
		if(gameState != null){
			//load previous game
		}
		else{
			candyBox = new CandyBoxGame(0);
		}*/

	}


	private void updateReady(List<TouchEvent> touchEvents) {

		if (touchEvents.size() > 0)
			state = GameState.Running;
	}

	public void updateRunning(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			for (Button button : buttons) {

				if(button.inButtonBounds(event) == true){
					if(button.name == "Quests"){
						game.setScreen(new QuestCategoryScreen(game));
						return;
					}
					else if(button.name == "Store"){
						Candybox.game.update();
						game.setScreen(new StoreItemsScreen(game));
						return;
					}
					else if(button.name == "Character"){
						Candybox.game.update();
						game.setScreen(new CharacterScreen(game));
						return;
					}
				}
			}
		}

		Candybox.game.update();
		if(Candybox.game.getCharacter().isAlive() == true){
			buttons.get(1).enableButton();
			//making quest button enabled if alive
		}





	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
		}

	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		super.paint(deltaTime);
		g.drawString("Gold: " + Candybox.game.getGold(), 5, 35, paint);
		paint.setTextAlign(Paint.Align.RIGHT);
		g.drawString("Crystals: 0", 475, 35, paint); 
		paint.setTextAlign(Paint.Align.LEFT);

		//PROFILE
		g.drawImage(Assets.stickMan, 115, 100);
		if(Candybox.game.getCharacter().isAlive() == false){
			g.drawString("Recovery Time: " + Candybox.game.getRecoveryTime(), 5, 60,paint);
		}


		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.GameOver)
			drawGameOverUI();

	}
	private void nullify() {

		// Set all variables to null. You will be recreating them in the
		// constructor.
		paint = null;

		// Call garbage collector to clean up memory.
		System.gc();
	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		g.drawARGB(155, 0, 0, 0);
		g.drawString("Tap to start the game.", 240, 400, paint);

	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();

	}


	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		/*
		if(AndroidGame.androidGame.loggedIn == true){
			g.drawImage(Assets.postButton, 240, 300);

		}*/
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

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

}