package com.boney.desura;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.boney.desura.screens.CreditsScreen;
import com.boney.desura.screens.GameoverScreen;
import com.boney.desura.screens.LevelScreen;
import com.boney.desura.screens.ModeScreen;
import com.boney.desura.screens.SelectionScreen;
import com.boney.desura.screens.ShopScreen;
import com.boney.desura.screens.SplashScreen;

//---------------------------------------------------------------------------------------------
//
//BoneyGame.java
//Last Revised: 1/12/2014 
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
	public static final boolean FREE = false;
	public static final String VERSION = "Ver. 1.1 Beta";
	private static ApplicationType app;
	private static AssetManager assetManager;
	private static final String LOG = "Boney";

	// Creates the game
	public void create() {
		// Log the type of game for controls
		app = Gdx.app.getType();

		// Setup the AssetManager and load the resources
		setAssetManager(new AssetManager());
		loadResources();

		// Switch to a screen so the game can start
		setScreen(new SplashScreen(this));
	}

	// Load the resources for the game
	private void loadResources() {
		// Set the type of manager
		getAssetManager().setLoader(TiledMap.class,
				new TmxMapLoader(new InternalFileHandleResolver()));

		// Queue the various textures
		getAssetManager()
				.load(SplashScreen.SPLASH_TEXTURES, TextureAtlas.class);
		getAssetManager().load(CreditsScreen.BACKGROUND_TEXTURES,
				TextureAtlas.class);
		getAssetManager().load(GameoverScreen.BUTTON_TEXTURES,
				TextureAtlas.class);
		getAssetManager().load(LevelScreen.BONEY_SPRITE_SHEET,
				TextureAtlas.class);
		getAssetManager().load(LevelScreen.DOGS_SPRITE_SHEET,
				TextureAtlas.class);
		getAssetManager().load(LevelScreen.LEVEL_TEXTURES, TextureAtlas.class);
		getAssetManager().load(ModeScreen.MODE_TEXTURES, TextureAtlas.class);
		getAssetManager().load(SelectionScreen.SELECTION_TEXTURES,
				TextureAtlas.class);
		getAssetManager().load(ShopScreen.SHOP_TEXTURES, TextureAtlas.class);

		// Actually load the resources
		getAssetManager().finishLoading();
	}

	// Disposes of the game
	public void dispose() {
		super.dispose();
		getAssetManager().dispose();
	}

	// Updates the game
	public void render() {
		super.render();
	}

	// Accounts for resizing of the window
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	// Pauses rendering
	public void pause() {
		super.pause();
	}

	// Resumes rendering
	public void resume() {
		super.resume();
	}

	// Returns the log for debugging
	public static String getLog() {
		return LOG;
	}

	// Returns the ApplicationType for determining the platform
	public ApplicationType getApp() {
		return app;
	}

	// Returns the AssetManager for accessing resources
	public static AssetManager getAssetManager() {
		return assetManager;
	}

	// Sets the AssetManager
	public static void setAssetManager(AssetManager assetManager) {
		BoneyGame.assetManager = assetManager;
	}
}
// Hunter Heidenreich 2014