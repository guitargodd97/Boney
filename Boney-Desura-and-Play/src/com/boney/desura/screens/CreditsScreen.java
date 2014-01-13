package com.boney.desura.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.boney.desura.BoneyGame;
//---------------------------------------------------------------------------------------------
//
//CreditsScreen.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This class is the Credit Screen or the "About Us."  
//
//---------------------------------------------------------------------------------------------

public class CreditsScreen implements Screen {
	public static final String BACKGROUND_TEXTURES = "data/images/background.atlas";
	private final String aboutUs = "We ain't about that life.\nWe hardcore, though.  We rise.\nWe ride.";
	private BitmapFont bigFont;
	private BitmapFont littleFont;
	private BoneyGame game;
	private Label title;
	private Label body;
	private Music backMusic;
	private Sprite background;
	private SpriteBatch batch;
	private Stage stage;
	private TextureAtlas imgAtlas;

	// Initializes CreditScreen
	public CreditsScreen(BoneyGame game) {
		this.game = game;
	}

	// Updates the screen
	public void render(float delta) {
		if (BoneyGame.getAssetManager().update()) {
			// Clears the screen each frame
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			// Return to menu on input
			if (Gdx.input.isKeyPressed(Keys.ESCAPE))
				game.setScreen(new MenuScreen(game));
			if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
				game.setScreen(new MenuScreen(game));
			if (Gdx.input.isKeyPressed(Keys.ENTER))
				game.setScreen(new MenuScreen(game));
			if (Gdx.input.isKeyPressed(Keys.SPACE))
				game.setScreen(new MenuScreen(game));
			if (Gdx.input.isTouched())
				game.setScreen(new MenuScreen(game));

			// Begin batch for drawing textures
			batch.begin();
			background.draw(batch);
			batch.end();

			// Act and draw the stage
			stage.act(delta);
			batch.begin();
			stage.draw();
			batch.end();
		}
	}

	// Called on the resizing the window
	public void resize(int width, int height) {
		// Initialize and clear the screen
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();

		// Set up the labels
		LabelStyle ls1 = new LabelStyle(bigFont, Color.WHITE);
		title = new Label("About Us", ls1);
		title.setWidth(width);
		title.setY(400);
		title.setAlignment(Align.center);
		ls1 = new LabelStyle(littleFont, Color.WHITE);
		body = new Label(aboutUs, ls1);
		body.setWidth(width);
		body.setY(100);
		body.setAlignment(Align.center);

		// Add the labels to the stage
		stage.addActor(title);
		stage.addActor(body);
	}

	// Called on screen show
	public void show() {
		// Initialize batch
		batch = new SpriteBatch();

		// Initialize fonts
		bigFont = new BitmapFont(Gdx.files.internal("data/boneyfont.fnt"),
				false);
		littleFont = new BitmapFont(Gdx.files.internal("data/boneybody.fnt"),
				false);

		// Grab textures
		imgAtlas = BoneyGame.getAssetManager().get(BACKGROUND_TEXTURES,
				TextureAtlas.class);

		// Setup sprites
		background = imgAtlas.createSprite("background-main");

		// Setup music
		backMusic = Gdx.audio.newMusic(Gdx.files
				.internal("data/sound/music/Boney Theme.mp3"));
		backMusic.setLooping(true);
		backMusic.play();
	}

	// Called on hide
	public void hide() {
		dispose();
	}

	// Called on pause
	public void pause() {
		backMusic.pause();
	}

	// Called on resume
	public void resume() {
		// Play the music again
		backMusic.play();

		// May have to reload textures here
		// Test that later
	}

	// Dispose of elements that need to be disposed
	public void dispose() {
		batch.dispose();
		stage.dispose();
		bigFont.dispose();
		littleFont.dispose();
		backMusic.dispose();
	}
}
// Hunter Heidenreich 2014