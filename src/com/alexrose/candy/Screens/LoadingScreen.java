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
 
        
        Assets.border = g.newImage("border.jpg", ImageFormat.RGB565);
        Assets.hero1 = g.newImage("hero1.png", ImageFormat.RGB565);
        Assets.hero2 = g.newImage("hero3.png", ImageFormat.RGB565);
        Assets.backButton = g.newImage("back button.png", ImageFormat.RGB565);
        Assets.downArrow = g.newImage("downArrow2.png", ImageFormat.ARGB4444);
        Assets.scrollUpArrow = g.newImage("upArrow2.png", ImageFormat.ARGB4444);  
        Assets.skillTreeUpArrow = g.newImage("upArrow.png", ImageFormat.RGB565);  
        Assets.treeButton1 = g.newImage("treeButton2.jpg", ImageFormat.ARGB4444);
        Assets.treeButton2 = g.newImage("treeButton3.jpg", ImageFormat.ARGB4444);
        

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