package com.boney.desura.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.boney.desura.BoneyGame;
import com.boney.desura.other.Boxes;
//---------------------------------------------------------------------------------------------
//
//ModeScreen.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This is a class that runs the Mode Screen
//
//---------------------------------------------------------------------------------------------

public class ModeScreen implements Screen {
	public static final String MODE_TEXTURES = "data/images/mode.atlas";
	private BitmapFont fontW;
	private boolean vN;
	private BoneyGame game;
	private Boxes vT;
	private Label version;
	private Rectangle rect[] = new Rectangle[4];
	private Sprite classic;
	private Sprite survive;
	private Sprite upgrade;
	private Sprite custom;
	private SpriteBatch batch;
	private TextureAtlas atlas;

	// Initializes the ModeScreen
	public ModeScreen(BoneyGame game) {
		this.game = game;

		// Sets up the clickable rectangles
		for (int i = 0; i < rect.length; i++) {
			rect[i] = new Rectangle();
			rect[i].setSize(Gdx.graphics.getWidth() / 2 - 20,
					Gdx.graphics.getHeight() / 2 - 20);
		}
		rect[0].setPosition(0, Gdx.graphics.getHeight() / 2 + 10);
		rect[1].setPosition(Gdx.graphics.getWidth() / 2 + 10,
				Gdx.graphics.getHeight() / 2 + 10);
		rect[2].setPosition(0, 0);
		rect[3].setPosition(Gdx.graphics.getWidth() / 2 + 10, 0);

		// Creates a "NOT AVAILABLE" box
		vT = new Boxes(3);
		vN = false;
	}

	// Updates the screen
	public void render(float delta) {
		if (BoneyGame.getAssetManager().update()) {
			// Clears the screen
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			// Draws the sprite batch
			batch.begin();
			upgrade.draw(batch);
			custom.draw(batch);
			survive.draw(batch);
			classic.draw(batch);
			// Whether we are displaying NA
			if (vN)
				version.draw(batch, 1);
			batch.end();

			// Determine whether to display or hide NA
			if (Gdx.input.justTouched()) {
				if (rect[0].contains(Gdx.input.getX(), Gdx.input.getY()))
					game.setScreen(new ShopScreen(game));
				else if (rect[1].contains(Gdx.input.getX(), Gdx.input.getY())) {
					if (BoneyGame.FREE) {
						if (vN)
							vN = false;
						else
							vN = true;
					} else
						game.setScreen(new CustomizeScreen(game));
				} else if (rect[2].contains(Gdx.input.getX(), Gdx.input.getY()))
					game.setScreen(new SelectionScreen(game));
				else if (rect[3].contains(Gdx.input.getX(), Gdx.input.getY()))
					game.setScreen(new LevelScreen(game, 1, 10));
			}
		}
	}

	// Called on resize window
	public void resize(int width, int height) {
		// Sets up the NA label
		LabelStyle ls = new LabelStyle(fontW, Color.WHITE);
		version = new Label(vT.getMessage(), ls);
		version.setAlignment(Align.center);
		version.setWidth(width);
		version.setY(200);
	}

	// Called on window show
	public void show() {
		// Initializes the batch
		batch = new SpriteBatch();

		// Grabs the fonts
		fontW = new BitmapFont(Gdx.files.internal("data/chilly.fnt"), false);

		// Grabs the textures
		atlas = BoneyGame.getAssetManager().get(MODE_TEXTURES,
				TextureAtlas.class);

		// Sets up the sprites
		classic = atlas.createSprite("modeClassic");
		classic.setPosition(rect[0].x, rect[0].y - 10);
		classic.setSize(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		survive = atlas.createSprite("modeSurvival");
		survive.setPosition(rect[1].x - 10, rect[1].y - 10);
		survive.setSize(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		upgrade = atlas.createSprite("modeUpgrade");
		upgrade.setPosition(rect[2].x, rect[2].y);
		upgrade.setSize(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		custom = atlas.createSprite("modeCustom");
		custom.setPosition(rect[3].x - 10, rect[3].y);
		custom.setSize(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
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

	// Disposes of disposable assets
	public void dispose() {
		fontW.dispose();
		batch.dispose();
	}
}
// Hunter Heidenreich 2014