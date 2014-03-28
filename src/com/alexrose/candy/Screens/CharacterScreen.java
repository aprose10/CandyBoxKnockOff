package com.alexrose.candy.Screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.alexrose.candy.Button;
import com.alexrose.candy.CandyBoxGame;
import com.alexrose.candy.Candybox;
import com.alexrose.candy.Item;
import com.alexrose.framework.Game;
import com.alexrose.framework.Graphics;
import com.alexrose.framework.Input.TouchEvent;

public class CharacterScreen extends ButtonScreen {
	Paint paint;
	public CharacterScreen(Game game) {
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
		g.drawString("Weapon: " + Candybox.game.getBestWeapon(), 20, 150, paint);
		g.drawString("Armor: " + Candybox.game.getBestArmor(), 20, 250, paint);
		g.drawString("Amulet: " + Candybox.game.getBestAmulet(), 20, 350, paint);
		
		g.drawString("Abilities: ", 20, 450, paint);
		g.drawString("Damage: ", 20, 550, paint);
		g.drawString("Defensive: ", 20, 650, paint);
		g.drawString("Healing: ", 20, 750, paint);


	}
}
