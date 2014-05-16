package com.alexrose.candy.Screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.alexrose.candy.Assets;
import com.alexrose.candy.Button;
import com.alexrose.framework.Game;
import com.alexrose.framework.Graphics;
import com.alexrose.framework.Screen;
import com.alexrose.framework.Input.TouchEvent;

public class ButtonScreen extends Screen{
	ArrayList<Button> buttons = new ArrayList<Button>();
	float lapsedTime;
	private int touchY = 0;
	private boolean isDragging = false;
	private double velocity = 0;
	private boolean isScrollable = false;
	private double velocityDecay = 1.57;
	public ButtonScreen(Game game, boolean hasBackButton, boolean isScrollable) {
		super(game);
		lapsedTime = 0;
		this.isScrollable = isScrollable;
		if(hasBackButton){
			Button backButton = new Button (10, 50, Assets.backButton, "");

			buttons.add(backButton);
		}
	}

	public void update(float deltaTime){
		lapsedTime = lapsedTime + deltaTime;
		//Number changes how long to wait before next click is possible. 10 == one tenth
		if(lapsedTime >= 25){
			this.updateRunning(deltaTime);
		}else{
			List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		}
	}



	public void updateRunning(float deltaTime) {
		Log.d("YOLO", "updated wrong");
	}

	@Override
	public void paint(float deltaTime) {

		//Log.d("YOLO", "Delta time: " + deltaTime);

		//Log.d("YOLO", "ButtonScreen Paint is active " + deltaTime);
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 490, 810, Color.BLACK);


		//tests to see if finger is not touching screen
		if(isScrollable == true){
			if(!game.getInput().isTouchDown(0)){
				scrollY((int) Math.round(velocity));
				slowScroll(deltaTime);
			}else{
				//decay if holding still (decays much faster than regularly)
				slowScroll(deltaTime);
				slowScroll(deltaTime);
				slowScroll(deltaTime);
			}
		}

		for (Button button : buttons) {
			if(button.getName() != ""){
				button.drawButton(g);
			}
		}

		if(isScrollable){
			//crashed here w index out of bounds when no items to buy
			if(buttonInLockPos(buttons.get(1)) == false){
				g.drawImage(Assets.scrollUpArrow, 0, 0);
			}

			int a = buttons.size() - 1;
			Button lastButton = buttons.get(a);

			if(buttonInLockPos(lastButton) == false){
				g.drawImage(Assets.downArrow, 0, 700);
			}
		}

		if(buttons.size() > 0){
		buttons.get(0).drawButton(g);
		}
	}

	private void slowScroll(double deltaTime){
		double oldVelocity = velocity;
		//if else to make sure it always slows down at least .99
		if(deltaTime > velocityDecay/.99){
			velocity = velocity * velocityDecay / deltaTime;
		}else{
			velocity = velocity * .99;
		}
		if((velocity < .5 && oldVelocity >= .5) ||
				(velocity > -.5 && oldVelocity <= -.5)){
			velocity = 0;
			Log.d("YOLO", "velocity set to 0 (< .5)");
		}
	}

	public boolean buttonInLockPos(Button button){
		if(button.getY() >= 100 && button.getY() <= 700){
			return true;
		}
	//	else if(button.getY() <= 700){
	//		return true;
	//	}
		return false;
	}

	//For skills on BattleScreen:
	public void paintOnlyButtons(float deltaTime){
		Graphics g = game.getGraphics();
		for (Button button : buttons) {
			button.drawButton(g);
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void backButton() {
		// TODO Auto-generated method stub

	}

	public boolean getIsDragging(){
		return isDragging;
	}

	public void setNotDragging(){
		isDragging = false;
	}
	public boolean scrollIsAllowed(double d){
		int a = buttons.size() - 1;
		Button lastButton = buttons.get(a);
		if(buttonInLockPos(lastButton) && d < 0 ){
			return false;
		}
		else if(buttonInLockPos(buttons.get(1)) && d > 0){
			return false;
		}

		return true;

	}
	public void scrollY(int d){
		//if scrolling up, and the resulting y position for top button is greater than 100,
		//then change distance to scroll
		//buttons move down
		if(d > 0 && buttons.get(1).getY() + d > 100){
			d = 100 - buttons.get(1).getY();
			velocity = 0;
		}

		Button lastButton = buttons.get(buttons.size() - 1);
		int yPos = lastButton.getY();
		if(d < 0 && yPos + d < 700){
			d = 700 - yPos;
			velocity = 0;
		}
		if(scrollIsAllowed(d) == true){
			for(int a = 0; a < buttons.size(); a++){
				if(buttons.get(a).image != Assets.backButton){
					buttons.get(a).changeYPos(d);
				}	
			}
		}
	}


	public void detectTouchDownOrDragged(TouchEvent event, float deltaTime){
		if(isScrollable == false){
			return;
		}

		if(event.type == TouchEvent.TOUCH_DOWN){
			touchY = event.y;
			velocity = 0;
		}

		if(event.type == TouchEvent.TOUCH_DRAGGED && buttons.size() > 7){
			int differenceOfY =  event.y - touchY;
			touchY = event.y;
			scrollY(differenceOfY);
			if(velocity == 0 || (velocity > 0 && differenceOfY > velocity) ||
					(velocity < 0 && differenceOfY < velocity) ){
				velocity = differenceOfY;
				isDragging = true;
			}
		}
	}

}
