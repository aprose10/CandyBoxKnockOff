package com.alexrose.candy.Screens;


import android.util.Log;

import com.alexrose.framework.Game;
import com.alexrose.framework.Graphics;
import com.alexrose.framework.Screen;
import com.alexrose.framework.Graphics.ImageFormat;
import com.alexrose.candy.Assets;


public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }


    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        game.setScreen(new MainMenuScreen(game));
        
        Assets.button = g.newImage("testButton.png", ImageFormat.ARGB4444);
        Assets.stickMan = g.newImage("stickman.gif", ImageFormat.RGB565);
        Assets.backButton = g.newImage("Back Button.png", ImageFormat.ARGB4444);
        Assets.buttonLOCKED = g.newImage("testButtonLOCKED.png", ImageFormat.ARGB4444);
        Assets.downArrow = g.newImage("downArrow.jpg", ImageFormat.ARGB4444);
        Assets.upArrow = g.newImage("upArrow.jpg", ImageFormat.ARGB4444);   
       
        
       
    }


    @Override
    public void paint(float deltaTime) {
    	 Graphics g = game.getGraphics();
       //dont forget this:  g.drawImage(Assets.splash, 0, 0);

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


    }
}