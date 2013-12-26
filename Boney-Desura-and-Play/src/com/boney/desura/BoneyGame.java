package com.boney.desura;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.boney.desura.screens.SplashScreen;

//---------------------------------------------------------------------------------------------
//
//BoneyGame.java
//Last Revised: 9/9/2013
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This is the main game class of Boney.  It initiates the game and runs it from start to 
//finish.  It is literally the game class of Boney.
//
//---------------------------------------------------------------------------------------------

public class BoneyGame extends Game {
	/// Variables

	// Strings and Booleans
	public static final String VERSION = "Ver. 1.1 Beta";
	private static final String LOG = "Boney";
	ApplicationType app;

	// create()
	//
	// Method initiates the creation of game by starting the splash screen
	//
	// Called naturally at the start of game
	public void create() {
		app = Gdx.app.getType();
		setScreen(new SplashScreen(this));
	}

	// dispose()
	//
	// Method disposes of the game cleanly
	//
	// Called naturally at the end of the game
	public void dispose() {
		super.dispose();
	}

	// render()
	//
	// Method renders the game
	//
	// Called naturally and continuously
	public void render() {
		super.render();
	}

	// resize()
	//
	// Method accounts for changes to the game when it resized
	//
	// Called when the window is resized (Desktop build only)
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	// pause()
	//
	// Method pauses things that need to be paused (i.e. music)
	//
	// Called when needed under certain circumstances like the screen sleeping
	// (Mobile builds)
	public void pause() {
		super.pause();
	}

	// resume()
	//
	// Method resumes things that need to be resumed (i.e. music)
	//
	// Called when needed under certain circumstances like the screen
	// re-awakening (Mobile builds)
	public void resume() {
		super.resume();
	}

	public static String getLog() {
		return LOG;
	}

	public ApplicationType getApp() {
		return app;
	}
}
// Hunter Heidenreich 2013