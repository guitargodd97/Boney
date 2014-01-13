package com.boney.desura.screens;

import com.badlogic.gdx.Screen;
import com.boney.desura.BoneyGame;
//---------------------------------------------------------------------------------------------
//
//CustomizeScreen.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This class is the Customize Screen for customize Boney's design.
//
//---------------------------------------------------------------------------------------------

public class CustomizeScreen implements Screen {
	private BoneyGame game;

	// Initializes the CustomizeScreen
	public CustomizeScreen(BoneyGame game) {
		this.game = game;
	}

	// Updates the screen
	public void render(float delta) {
		if (BoneyGame.getAssetManager().update()) {
			game.setScreen(new MenuScreen(game));
		}
	}

	// Called on the window resize
	public void resize(int width, int height) {
	}

	// Called on window show
	public void show() {
	}

	// Called on hide
	public void hide() {
		dispose();
	}

	// Called on pause
	public void pause() {
	}

	// Called on resume
	public void resume() {
	}

	// Disposes of disposable elements
	public void dispose() {
	}

}
// Hunter Heidenreich 2014