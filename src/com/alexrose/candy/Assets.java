package com.alexrose.candy;

import java.util.HashMap;

import com.alexrose.framework.Graphics;
import com.alexrose.framework.Image;
import com.alexrose.framework.Music;
import com.alexrose.framework.Sound;
import com.alexrose.framework.Graphics.ImageFormat;

public class Assets {
	public static Music theme;
	public static Image border;
	public static Image button;
	public static Image hero1;
	public static Image hero2;
	public static Image backButton;
	public static Image buttonLOCKED;
	public static Image fireballIcon;
	public static Image shieldIcon;
	public static Image healIcon;
	public static Image scrollUpArrow;
	public static Image downArrow;
	public static Image sheepIcon;
	public static Image sheepIcon2;
	public static Image sheepIcon3;
	public static Image treeButton1;
	public static Image treeButton2;
	public static Image skillTreeUpArrow;
	public static HashMap<String, Image> hashmap;
	public static void load(Candybox candybox) {
		
		hashmap = new HashMap<String, Image>();
		// TODO Auto-generated method stub
		theme = candybox.getAudio().createMusic("mainTheme.mp3");
		theme.setLooping(true);
		theme.setVolume(0.60f);
		theme.play();

		Graphics g = Candybox.activity.getGraphics();
		Assets.fireballIcon = g.newImage("fireballIcon.jpg", ImageFormat.ARGB4444);
		hashmap.put("fireball", Assets.fireballIcon);
		Assets.shieldIcon = g.newImage("shieldIcon.jpg", ImageFormat.ARGB4444);
		hashmap.put("shield", Assets.shieldIcon);
		Assets.healIcon = g.newImage("healthIcon.jpg", ImageFormat.ARGB4444);
		hashmap.put("heal", Assets.healIcon);
		Assets.sheepIcon = g.newImage("sheepIcon.png", ImageFormat.ARGB4444);
		Assets.sheepIcon2 = g.newImage("sheepIcon2.png", ImageFormat.ARGB4444);
		Assets.sheepIcon3 = g.newImage("sheepIcon3.png", ImageFormat.ARGB4444);
		Assets.button = g.newImage("button.png", ImageFormat.RGB565);
		Assets.buttonLOCKED = g.newImage("buttonLocked.png", ImageFormat.RGB565);
	}
}