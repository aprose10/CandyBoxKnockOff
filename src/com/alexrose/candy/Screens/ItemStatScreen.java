package com.alexrose.candy.Screens;

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
public class ItemStatScreen extends ButtonScreen{
	public Item item;
	Paint paint;
	public ItemStatScreen(Game game, Item currentItem) {
		super(game, true, false);

		item = currentItem;

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		
		Button buyButton = new Button (20, 700, Assets.button, "Buy");

		buttons.add(buyButton);

	}

	public void updateRunning(float deltaTime) {

		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			for (Button button : buttons) {

				if(button.inButtonBounds(event) == true){
					if(button.name == ""){
						game.setScreen(new StoreItemsScreen(game));
						return;
					}
					
					else if(button.name == "Buy"){
						Candybox.game.purchaseItem(item, true);
						Candybox.saveToParse();
						Log.d("YOLO", "You have purchased: " + item );
						game.setScreen(new PrimaryScreen(game));
						return;
					}
				}
			}
		}
	}

	public void paint(float deltaTime) {
		super.paint(deltaTime);
		Graphics g = game.getGraphics();

		//draw info	
		g.drawString(item.getName(),25, 200, paint);
		g.drawString("The price of this item is: " + item.getPrice(),25, 300, paint);
		g.drawString("Your damage will increase by: " + item.getIncreaseDamage(),25, 400, paint);
		g.drawString("Your defense will increase by: " + item.getIncreaseDefense(),25, 500, paint);
		g.drawString("Your health will increase by: " + item.getIncreaseHealth(),25, 600, paint);
	}

}
