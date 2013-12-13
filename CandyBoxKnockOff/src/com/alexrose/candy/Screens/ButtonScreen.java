package com.alexrose.candy.Screens;

import android.util.Log;

import com.alexrose.framework.Game;
import com.alexrose.framework.Screen;

public class ButtonScreen extends Screen{
	float lapsedTime;

	public ButtonScreen(Game game) {
		super(game);
		lapsedTime = 0;
		
	}
	
	public void update(float deltaTime){
		
		lapsedTime = lapsedTime + deltaTime;
		if(lapsedTime >= 100){
			this.updateRunning(deltaTime);
		}
	}

	public void updateRunning(float deltaTime) {
		Log.d("YOLO", "updated wrong");
	}

	@Override
	public void paint(float deltaTime) {
		
		
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

}
