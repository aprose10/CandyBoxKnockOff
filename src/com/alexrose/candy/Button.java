package com.alexrose.candy;

import android.graphics.Color;
import android.graphics.Paint;

import com.alexrose.framework.Graphics;
import com.alexrose.framework.Image;
import com.alexrose.framework.Input.TouchEvent;

public class Button {

	static Paint buttonPaint;
	static Paint buttonPaint2;
	public int positionX;
	public int positionY;
	public Image image;
	public String name;
	

	public Button(int posX, int posY, Image img, String strng){
		positionX = posX;
		positionY = posY;
		image = img;
		name = strng;

		if(buttonPaint == null){
			buttonPaint = new Paint();
			buttonPaint.setTextSize(75);
			buttonPaint.setTextAlign(Paint.Align.CENTER);
			buttonPaint.setAntiAlias(true);
			buttonPaint.setColor(Color.WHITE);
		}
		
		if(buttonPaint2 == null){
			buttonPaint2 = new Paint();
			buttonPaint2.setTextSize(50);
			buttonPaint2.setTextAlign(Paint.Align.CENTER);
			buttonPaint2.setAntiAlias(true);
			buttonPaint2.setColor(Color.WHITE);
		}


	}
	
	public int getY(){
		return positionY;
	}
	
	public String getName(){
		return name;
	}
	
	public void drawButton(Graphics g){
		g.drawImage(image, positionX, positionY);
		if(smallFont(name) == false){
		g.drawString(name, 240, positionY + 75, buttonPaint); 
		}
		else if(smallFont(name) == true){
			g.drawString(name, 240, positionY + 75, buttonPaint2);
		}
	}
	
	public boolean inButtonBounds(TouchEvent e){
		if (image != Assets.buttonLOCKED && e.x > positionX && e.x < positionX + image.getWidth() - 1 && e.y > positionY
				&& e.y < positionY + image.getHeight() - 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void enableButton(){
		image = Assets.button;
	}
	
	public void changeYPos(double d){
		positionY = (int) (positionY + d);
	}
	
	public boolean smallFont(String name){
		int numberOfLetters = name.length();
	if(numberOfLetters <=11){
		return false;
	}
	else if(numberOfLetters > 11){
		return true;
	}
	return false;
	}
	
}
