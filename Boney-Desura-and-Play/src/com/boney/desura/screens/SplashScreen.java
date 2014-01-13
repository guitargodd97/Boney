package com.boney.desura.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.boney.desura.BoneyGame;
//---------------------------------------------------------------------------------------------
//
//SplashScreen.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This is a class that runs the Splash Screen.
//
//---------------------------------------------------------------------------------------------

public class SplashScreen implements Screen {
	public static final String SPLASH_TEXTURES = "data/images/splash.atlas";
	public static final String STATIC_SOUND = "data/sound/sfx/static.mp3";
	public static final String SPLASH_SOUND = "data/sound/music/splashsound.mp3";
	private boolean reverse;
	private boolean hasPlayed;
	private BoneyGame game;
	private float alpha;
	private float fade;
	private float timer;
	private int counter;
	private Music splashS;
	private Sprite stat, splash;
	private SpriteBatch batch;
	private TextureAtlas splashAtlas;

	// Constructor for SplashScreen
	public SplashScreen(BoneyGame game) {
		this.game = game;

		// Initialize alpha shifting variables
		reverse = false;
		hasPlayed = false;
		alpha = 0;
		fade = 0.01f;
		timer = 0;
		counter = 0;
	}

	// Updates the screen
	public void render(float delta) {
		if (BoneyGame.getAssetManager().update()) {
			// Clears the screen
			Gdx.gl.glClearColor(0, 0, 0, alpha);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			// Starts drawing the textures with new alphas
			batch.begin();
			Color c = batch.getColor();
			batch.setColor(c.r, c.b, c.g, 1);
			if (timer <= 2.85 || timer >= 3.15)
				splash.draw(batch);
			c = batch.getColor();
			batch.setColor(c.r, c.b, c.g, 1);
			if (timer >= 2.85 && timer <= 3.15) {
				stat.draw(batch);
				playStaticSound();
			}
			batch.end();

			// Updates the alphas
			updateAlpha();
		}
	}

	// Called when the screen changes size
	public void resize(int width, int height) {
	}

	// Called on screen startup
	public void show() {
		// Initializes the batch
		batch = new SpriteBatch();

		// Retrieves the textures
		splashAtlas = BoneyGame.getAssetManager().get(SPLASH_TEXTURES,
				TextureAtlas.class);

		// Sets up the sprites
		stat = splashAtlas.createSprite("statics");
		splash = splashAtlas.createSprite("splash");
		splash.setPosition((Gdx.graphics.getWidth() - splash.getWidth()) / 2,
				(Gdx.graphics.getHeight() - splash.getHeight()) / 2);

		// Initializes the sounds
		splashS = Gdx.audio.newMusic(Gdx.files.internal(SPLASH_SOUND));
		splashS.play();
		splashS.setLooping(false);
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
	@Override
	public void dispose() {
		batch.dispose();
		splashS.dispose();
	}

	// Switches to main menu
	private void completedSplash() {
		game.setScreen(new MenuScreen(game));
	}

	// Plays the static noise
	public void playStaticSound() {
		// If the static hasn't been played yet
		if (!hasPlayed) {
			Gdx.audio.newSound(Gdx.files.internal(STATIC_SOUND)).play();
			hasPlayed = true;
		}
	}

	// Updates the alpha of the images to create a fade-in fade-out
	private void updateAlpha() {
		splash.setColor(1, 1, 1, alpha);
		stat.setColor(1, 1, 1, alpha / 4);
		if (counter % 3 == 0) {
			if (alpha < .99f && !reverse)
				alpha += fade;
			else if (alpha > 0.01f && reverse)
				alpha -= fade;
		}
		timer += fade;
		if (timer >= 2.99f && !reverse) {
			splash.flip(true, false);
			reverse = true;
		}
		if (reverse && alpha < 0.02f)
			completedSplash();
		stat.setPosition((float) Math.random() * -1, (float) Math.random() * -1);
	}
}
// Hunter Heidenreich 2014