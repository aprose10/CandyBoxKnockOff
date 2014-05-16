package com.alexrose.candy;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Paint;

import com.alexrose.framework.Graphics;
import com.alexrose.framework.Image;

public class SkillTreeAbilityButton extends Button{
	private Image img;
	private int posX;
	private int posY;
	private String string;
	private Paint paint;
	public SkillTreeAbilityButton(int posX, int posY, Image img, String strng) {
		super(posX, posY, img, strng);
		this.img = img;
		this.posX = posX;
		this.posY = posY;
		this.string = strng;
		
		paint = new Paint();
		paint.setTextSize(15);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
	}
	
	public void drawButton(Graphics g){
		g.drawImage(img, posX, posY);
		g.drawString(string, posX, posY+150, paint);
		
	}

}
