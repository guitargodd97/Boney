package com.boney.desura.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.boney.desura.BoneyGame;
import com.boney.desura.moveable.Boney;
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
	public static final String BACKGROUND_TEXTURES = "data/images/background.atlas";
	public static final String BONEY_SPRITE_SHEET = "data/images/boney.atlas";
	private Boney boney;
	private BoneyGame game;
	private Sprite back;
	private SpriteBatch batch;
	private TextureAtlas background;
	private TextureAtlas boneySheet;

	// Initializes the CustomizeScreen
	public CustomizeScreen(BoneyGame game) {
		this.game = game;
		boney = new Boney();
		boney.setState(2);
		batch = new SpriteBatch();
	}

	// Updates the screen
	public void render(float delta) {
		if (BoneyGame.getAssetManager().update()) {
			batch.begin();
			back.draw(batch);
			boney.draw(batch);
			batch.end();
		}
	}

	// Called on the window resize
	public void resize(int width, int height) {
	}

	// Called on window show
	public void show() {
		// Initialize drawing things
		batch = new SpriteBatch();

		// Retrieves the assets
		background = BoneyGame.getAssetManager().get(BACKGROUND_TEXTURES,
				TextureAtlas.class);
		boneySheet = BoneyGame.getAssetManager().get(BONEY_SPRITE_SHEET,
				TextureAtlas.class);

		// Creates background sprites
		back = background.createSprite("background-main");

		// Creates Boney sprites
		for (int i = 0; i < 8; i++) {
			Boney.curIdle[i] = boneySheet.createSprite("Boney-Idle", i);
			Boney.curCrouch[i] = boneySheet.createSprite("Boney-Crouch", i);
			Boney.curRunRight[i] = boneySheet.createSprite("Boney-Run", i);
			Boney.curRunLeft[i] = boneySheet.createSprite("Boney-Run", i);
			Boney.curRunLeft[i].flip(true, false);
			Boney.curJump[i] = boneySheet.createSprite("Boney-Jump", i);
		}
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