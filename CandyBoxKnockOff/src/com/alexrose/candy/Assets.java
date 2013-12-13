package com.alexrose.candy;

import com.alexrose.framework.Image;
import com.alexrose.framework.Music;
import com.alexrose.framework.Sound;

public class Assets {
	public static Music theme;
    public static Image button;
    public static Image stickMan;
    public static Image backButton;
    public static Image buttonLOCKED;
    public static void load(Candybox candybox) {
        // TODO Auto-generated method stub
        theme = candybox.getAudio().createMusic("mainTheme.mp3");
        theme.setLooping(true);
        theme.setVolume(0.60f);
        theme.play();
    }
}