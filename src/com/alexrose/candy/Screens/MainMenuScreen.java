package com.alexrose.candy.Screens;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.alexrose.framework.Animation;
import com.alexrose.framework.Game;
import com.alexrose.framework.Graphics;
import com.alexrose.framework.Image;
import com.alexrose.framework.Screen;
import com.alexrose.framework.Input.TouchEvent;
import com.alexrose.framework.implementation.AndroidGame;
import com.alexrose.candy.Candybox;



public class MainMenuScreen extends Screen {

	Animation anim;
	Paint paint = new Paint();

	public MainMenuScreen(Game game) {
		super(game);
		
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

	}


	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();


		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				
				if(Candybox.game != null){
					game.setScreen(new PrimaryScreen(game));
				}
				
				
				//PURCHASE NEW PADDLE COLOR
				/*if (inBounds(event, 380, 346, 50, 36)) {
					Log.d("YOLO", "Upgrade button clicked; launching purchase flow for upgrade.");
					//setWaitScreen(true);

					/* TODO: for security, generate your payload here for verification. See the comments on 
					 *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use 
					 *        an empty string, but on a production app you should carefully generate this.
					String payload = ""; 

					Candybox.activity.mHelper.launchPurchaseFlow(Candybox.activity, "paddlecolor", 10001, 
							Candybox.activity.mPurchaseFinishedListener, payload);
				}

				if (inBounds(event, 50, 346, 100, 100)) {
					Log.d("YOLO", "Facebook login button clicked; time to sign in");

					//AndroidGame.androidGame.onClickLogin();
				}
				
				if (inBounds(event, 100, 200, 72, 21)) {
					Log.d("YOLO", "Facebook logout button clicked; time to sign in");

					//AndroidGame.androidGame.onClickLogout();
				}
 */
			}
		}

	}


	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}


	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawString("Tap to begin", 240, 400, paint);
		//if(AndroidGame.loggedIn() == true){
			//g.drawImage(Assets.facebookLogout, 100, 200);
		//}
		//else{
		//	g.drawImage(Assets.facebookButton, 50, 346);
	//	}
		
		//anim.update(deltaTime);
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
		//Display "Exit Game?" Box
		android.os.Process.killProcess(android.os.Process.myPid());

	}
}