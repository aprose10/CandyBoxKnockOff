package com.alexrose.candy;

import android.graphics.Color;
import android.graphics.Paint;

import com.alexrose.framework.Graphics;
import com.alexrose.framework.Image;
import com.alexrose.framework.Input.TouchEvent;

public class Button {

	static Paint buttonPaint;
	static Paint buttonPaint2;
	static Paint buttonPaint3;
	static Paint buttonPaint4;
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
		
		if(buttonPaint3 == null){
			buttonPaint3 = new Paint();
			buttonPaint3.setTextSize(30);
			buttonPaint3.setTextAlign(Paint.Align.CENTER);
			buttonPaint3.setAntiAlias(true);
			buttonPaint3.setColor(Color.WHITE);
		}
		
		if(buttonPaint4 == null){
			buttonPaint4 = new Paint();
			buttonPaint4.setTextSize(15);
			buttonPaint4.setTextAlign(Paint.Align.CENTER);
			buttonPaint4.setAntiAlias(true);
			buttonPaint4.setColor(Color.WHITE);
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
		
		g.drawString(name, 240, positionY + calculateHeightAdjustment(name.length()), getFont(name));
		
	}
	
	public int calculateHeightAdjustment(int length){
		if(length <=9){
			return 75;
		}else if(length<=17){
			return 65;
		}else if(length <30){
			return 60;
		}else{
			return 55;
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
	
	public Paint getFont(String name){
		int numberOfLetters = name.length();
		if(numberOfLetters <=9){
			return buttonPaint;
		}
		else if(numberOfLetters <17){
			return buttonPaint2;
		}else if(numberOfLetters <30){
			return buttonPaint3;
		}
		else{
			return buttonPaint4;
		}
	}
	
}
