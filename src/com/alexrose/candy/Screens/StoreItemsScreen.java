package com.alexrose.candy.Screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.alexrose.candy.Assets;
import com.alexrose.candy.Button;
import com.alexrose.candy.Candybox;
import com.alexrose.candy.Item;
import com.alexrose.framework.Game;
import com.alexrose.framework.Graphics;
import com.alexrose.framework.Input.TouchEvent;

public class StoreItemsScreen extends ButtonScreen {

	public StoreItemsScreen(Game game) {
		super(game, true, true);

		// Initialize game objects here

		ArrayList<Item> storeItems = Candybox.game.getStoreItems();

		createButtons(storeItems);

	}

	public void createButtons(ArrayList<Item> storeItems){
		for(int a = 0; a < storeItems.size(); a++){
			Button itemButton = new Button (20, (100 + (105*a)), Assets.button, storeItems.get(a).getName());
			buttons.add(itemButton);
		}
	}

	public void updateRunning(float deltaTime) {
		//Log.d("YOLO", "StoreItemsScreen updateRunning is active " + deltaTime);
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			detectTouchDownOrDragged(event, deltaTime);

			if (event.type == TouchEvent.TOUCH_UP) {
				if(getIsDragging() == true){
					setNotDragging();
				}
				else{
					for (Button button : buttons) {

						if(button.inButtonBounds(event) == true){
							if(button.name == ""){
								game.setScreen(new PrimaryScreen(game));
								return;
							}

							else{
								game.setScreen(new ItemStatScreen (game, selectItem(button.name)));
								return;
							}
						}
					}
				}
			}
		}
	}


	public void paint(float deltaTime) {
		super.paint(deltaTime);


	}

	public Item selectItem(String name){
		ArrayList<Item> storeItems = Candybox.game.getStoreItems();
		for(int a = 0; a < storeItems.size(); a++){
			if(name == storeItems.get(a).getName()){
				return storeItems.get(a);
			}
		}

		return null;

	}

}
