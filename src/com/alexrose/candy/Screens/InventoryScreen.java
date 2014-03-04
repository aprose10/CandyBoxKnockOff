package com.alexrose.candy.Screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.alexrose.candy.Button;
import com.alexrose.candy.Candybox;
import com.alexrose.candy.Item;
import com.alexrose.framework.Game;
import com.alexrose.framework.Graphics;
import com.alexrose.framework.Input.TouchEvent;

public class InventoryScreen extends ButtonScreen {
	Paint paint;
	public InventoryScreen(Game game) {
		super(game, true, false);

		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);


	}

	public void updateRunning(float deltaTime) {

		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			for (Button button : buttons) {

				if(button.inButtonBounds(event) == true){
					if(button.name == ""){
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
		for(int a = 0; a < Candybox.game.getInventory().size(); a++ ){
			String name = Candybox.game.getInventory().get(a).getName();
			g.drawString(name, 20, 200 + (a*50), paint);
		}


	}
}
