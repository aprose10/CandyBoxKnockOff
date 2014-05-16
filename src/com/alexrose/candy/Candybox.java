package com.alexrose.candy;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.util.Log;

import com.alexrose.framework.Screen;
import com.alexrose.framework.implementation.AndroidGame;
import com.alexrose.candy.Assets;
import com.alexrose.candy.Candybox;
import com.alexrose.candy.SaveGameState;
import com.alexrose.candy.Screens.SplashLoadingScreen;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Candybox extends AndroidGame {
	public static Candybox activity;
	public static CandyBoxGame game;
	boolean firstTimeCreate = true;

	@Override
	public Screen getInitScreen() {

		if (firstTimeCreate) {
			activity = this;
			Assets.load(this);
			firstTimeCreate = false;
			Parse.initialize(this, "xESYekeZp1d5rfpSg6PZwXUHnOfztgjNYOhIYZqM", "MkX4jN3Dj4PPi0spjGUqnWYaHKO0Sooa8ZSM017L");

			ParseQuery<ParseObject> query = ParseQuery.getQuery("GameState");
			final String android_id = Secure.getString(Candybox.activity.getContentResolver(), Secure.ANDROID_ID); 
			query.whereEqualTo("AndroidID", android_id);
			query.getFirstInBackground(new GetCallback<ParseObject>() {
				public void done(ParseObject obj, ParseException e) {
					if (obj != null) {
						Log.d("YOLO", "Found saved game on startup");
						int gold = obj.getInt("Gold");
						int crystals = obj.getInt("Crystals");
						String nameOfFarthestQuest = obj.getString("nameOfFarthestQuest");
						List<Object> itemsInInventory = obj.getList("Items");
						List<Object> selectedAbilities = obj.getList("SelectedAbilities");
						game = new CandyBoxGame(gold,crystals,nameOfFarthestQuest,itemsInInventory,null,selectedAbilities);
					}else{
						Log.d("YOLO", "Found nothing on startup");
						game = new CandyBoxGame(0,0,null,null,null,null);

					}

				}

			});
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
