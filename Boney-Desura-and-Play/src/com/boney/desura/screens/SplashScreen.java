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
//Last Revised: 11/20/2013
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
	// Variables
	// Numbers
	protected float alpha = 0, alphaStatic = 0, fade = 0.01f, timer = 0;
	protected int counter = 0;

	// Booleans and Strings
	protected boolean reverse, hasPlayed, assigned;
	public static final String staticSound = "data/sound/sfx/static.mp3",
			splashSound = "data/sound/music/splashsound.mp3";
	public static final String SPLASH_TEXTURES = "data/images/splash.atlas";

	// Images and the Like
	protected TextureAtlas splashAtlas;
	protected Sprite stat, splash;
	protected SpriteBatch batch;

	// Sound
	protected Music splashS;

	// Other Objects
	protected BoneyGame game;

	// Constructor
	public SplashScreen(BoneyGame game) {
		this.game = game;
		splashAtlas = game.getAssetManager().get(SPLASH_TEXTURES, TextureAtlas.class);
		reverse = false;
		hasPlayed = false;

	}

	public void render(float delta) {
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
		Gdx.gl.glClearColor(0, 0, 0, alpha);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
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
	}

	// Called when the screen changes size
	public void resize(int width, int height) {
	}

	// Called on screen startup
	public void show() {
		batch = new SpriteBatch();
		stat = splashAtlas.createSprite("statics");
		splash = splashAtlas.createSprite("splash");
		splash.setPosition((Gdx.graphics.getWidth() - splash.getWidth()) / 2,
				(Gdx.graphics.getHeight() - splash.getHeight()) / 2);
		splashS = Gdx.audio.newMusic(Gdx.files.internal(splashSound));
		splashS.play();
		splashS.setLooping(false);
		assigned = true;
	}

	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		splashS.dispose();
	}

	public void completedSplash() {
		game.setScreen(new MenuScreen(game));
	}

	public void playStaticSound() {
		if (!hasPlayed) {
			Gdx.audio.newSound(Gdx.files.internal(staticSound)).play();
			hasPlayed = true;
		}
	}
}
