package com.alexrose.candy.Screens;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.provider.Settings.Secure;
import android.util.Log;

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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class PrimaryScreen extends ButtonScreen {
	enum GameState {
		Ready, Running, GameOver, Paused
	}

	private GameState state = GameState.Running;
	private Paint paint;
	private Paint gameOverPaint;
	private Button questButton;
	private Button storeButton;
	private Button characterButton;
	private float timePassed;

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


		storeButton = new Button(20, 375, Assets.button, "Store");

		//crashed here, null
		Log.d("YOLO", "Character: " + Candybox.game.getCharacter());
		questButton = new Button(20, 495, (Candybox.game.getCharacter().isAlive() ? Assets.button : Assets.buttonLOCKED), "Quests");

		characterButton = new Button(20, 615, Assets.button, "Character");

		Candybox.saveToParse();

	}

	private void saveToParseTimer(float deltaTime){
		timePassed = timePassed + deltaTime;
		if(timePassed >= 1000){
			Candybox.saveToParse();
			timePassed = 0;
		}
	}

	private void updateReady(List<TouchEvent> touchEvents) {

		if (touchEvents.size() > 0)
			state = GameState.Running;
	}

	public void updateRunning(float deltaTime) {

		updateButtons();

		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.x >= 125 && event.x <= 298 && event.y >= 60 && event.y <= 320 && event.type == event.TOUCH_DOWN){
				Candybox.game.giveOneGold();
			}
			for (Button button : buttons) {

				if(button.inButtonBounds(event) == true){
					if(button.name == "Quests"){
						game.setScreen(new QuestCategoryScreen(game));
						return;
					}
					else if(button.name == "Store"){
						Candybox.game.update(deltaTime);
						game.setScreen(new StoreItemsScreen(game));
						return;
					}
					else if(button.name == "Character"){
						Candybox.game.update(deltaTime);
						game.setScreen(new CharacterScreen(game));
						return;
					}
				}
			}
		}

		Candybox.game.update(deltaTime);
		if(Candybox.game.getCharacter().isAlive() == true && Candybox.game.isFirstQuestUnlocked() == true){
			questButton.enableButton();
			
			//making quest button enabled if alive
		}
		
		saveToParseTimer(deltaTime);
	}

	private void updateButtons() {
		if(Candybox.game.areItemsStocked() == true){
			//put merchant dialog here
			if(buttons.contains(storeButton) == false){
				buttons.add(storeButton);
			}
		}else{
			if(buttons.contains(storeButton) == true){
				buttons.remove(storeButton);
			}
		}
		if(Candybox.game.isFirstQuestUnlocked() == true && buttons.contains(questButton) == false){
			buttons.add(questButton);
		}
		if(Candybox.game.hasBeatFirstQuest() == true && buttons.contains(characterButton) == false){
			buttons.add(characterButton);
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
		g.drawString("Crystals: " + Candybox.game.getCrystals(), 475, 35, paint); 
		paint.setTextAlign(Paint.Align.LEFT);

		//PROFILE
		g.drawImage(Assets.hero2, 125, 60);
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
	public void pause() {
		if (state == GameState.Running){
			state = GameState.Paused;
			Log.d("YOLO", "Paused here");
		}

	}

	@Override
	public void resume() {
		if (state == GameState.Paused){
			state = GameState.Running;
			Log.d("YOLO", "Resuming here");	
		}
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		pause();
	}

}