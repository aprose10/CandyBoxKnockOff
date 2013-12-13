package com.alexrose.candy;

import android.graphics.Color;
import android.graphics.Paint;

import com.alexrose.framework.Graphics;
import com.alexrose.framework.Image;
import com.alexrose.framework.Input.TouchEvent;

public class Button {

	static Paint buttonPaint;
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

	}
	
	public void drawButton(Graphics g){
		g.drawImage(image, positionX, positionY);
		g.drawString(name, 240, positionY + 75, buttonPaint); 
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
}
