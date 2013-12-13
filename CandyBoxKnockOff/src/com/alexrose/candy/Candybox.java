package com.alexrose.candy;

import android.content.Context;
import android.content.SharedPreferences;

import com.alexrose.framework.Screen;
import com.alexrose.framework.implementation.AndroidGame;
import com.alexrose.candy.Assets;
import com.alexrose.candy.Candybox;
import com.alexrose.candy.SaveGameState;
import com.alexrose.candy.Screens.SplashLoadingScreen;

public class Candybox extends AndroidGame {
	public static Candybox activity;
	public static CandyBoxGame game;
	boolean firstTimeCreate = true;

	@Override
	public Screen getInitScreen() {

		if (firstTimeCreate) {
			Assets.load(this);
			firstTimeCreate = false;
			activity = this;
			game = new CandyBoxGame(0);
		}

		return new SplashLoadingScreen(this);

	}

	/*public static boolean hasPaddleColorUpgrade(){
		return activity.mIsPaddleColor;
	}*/

	@Override
	public void onBackPressed() {
		getCurrentScreen().backButton();
	}

	@Override
	public void onResume() {
		super.onResume();

		Assets.theme.play();

	}

	@Override
	public void onPause() {
		super.onPause();
		Assets.theme.pause();

	}

	public static void saveGame(SaveGameState gameState){

	}

	public static SaveGameState loadGame(){
		return null;

	}
}
