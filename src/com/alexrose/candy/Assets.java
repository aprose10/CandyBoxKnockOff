package com.alexrose.candy;

import com.alexrose.framework.Graphics;
import com.alexrose.framework.Image;
import com.alexrose.framework.Music;
import com.alexrose.framework.Sound;
import com.alexrose.framework.Graphics.ImageFormat;

public class Assets {
	public static Music theme;
    public static Image button;
    public static Image stickMan;
    public static Image backButton;
    public static Image buttonLOCKED;
    public static Image fireballIcon;
    public static Image shieldIcon;
    public static Image healIcon;
    public static Image upArrow;
    public static Image downArrow;
    public static void load(Candybox candybox) {
        // TODO Auto-generated method stub
        theme = candybox.getAudio().createMusic("mainTheme.mp3");
        theme.setLooping(true);
        theme.setVolume(0.60f);
        theme.play();
        
        Graphics g = Candybox.activity.getGraphics();
        Assets.fireballIcon = g.newImage("fireballIcon.jpg", ImageFormat.ARGB4444);
        Assets.shieldIcon = g.newImage("shieldIcon.jpg", ImageFormat.ARGB4444);
        Assets.healIcon = g.newImage("healthIcon.jpg", ImageFormat.ARGB4444);
    }
}