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
	// / Variables

	// Strings and Booleans
	public static final String VERSION = "Ver. 1.1 Beta";
	private static final String LOG = "Boney";
	public static final boolean FREE = true;
	ApplicationType app;
	private static AssetManager assetManager;

	// create()
	//
	// Method initiates the creation of game by starting the splash screen
	//
	// Called naturally at the start of game
	public void create() {
		app = Gdx.app.getType();
		setAssetManager(new AssetManager());
		loadResources();
		setScreen(new SplashScreen(this));

	}

	private void loadResources() {
		getAssetManager().setLoader(TiledMap.class, new TmxMapLoader(
				new InternalFileHandleResolver()));

		// player textures
		getAssetManager().load(SplashScreen.SPLASH_TEXTURES, TextureAtlas.class);
		getAssetManager().load(CreditsScreen.BACKGROUND_TEXTURES, TextureAtlas.class);
		getAssetManager().load(GameoverScreen.BUTTON_TEXTURES, TextureAtlas.class);
		getAssetManager().load(LevelScreen.BONEY_SPRITE_SHEET, TextureAtlas.class);
		getAssetManager().load(LevelScreen.DOGS_SPRITE_SHEET, TextureAtlas.class);
		getAssetManager().load(LevelScreen.LEVEL_TEXTURES, TextureAtlas.class);
		getAssetManager().load(ModeScreen.MODE_TEXTURES, TextureAtlas.class);
		getAssetManager().load(SelectionScreen.SELECTION_TEXTURES, TextureAtlas.class);
		getAssetManager().load(ShopScreen.SHOP_TEXTURES, TextureAtlas.class);

		// do the actual loading
		getAssetManager().finishLoading();
	}

	// dispose()
	//
	// Method disposes of the game cleanly
	//
	// Called naturally at the end of the game
	public void dispose() {
		super.dispose();
		getAssetManager().dispose();
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

	/**
	 * @return the assetManager
	 */
	public static AssetManager getAssetManager() {
		return assetManager;
	}

	/**
	 * @param assetManager the assetManager to set
	 */
	public static void setAssetManager(AssetManager assetManager) {
		BoneyGame.assetManager = assetManager;
	}
}
// Hunter Heidenreich 2013