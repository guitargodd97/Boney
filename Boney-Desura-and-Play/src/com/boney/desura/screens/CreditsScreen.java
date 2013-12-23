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
//Last Revised: 9/12/2013
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
	// Variables

	// Strings and Booleans
	String aboutUs;

	// Objects
	BoneyGame game;
	BitmapFont bigFont, littleFont;
	Label title, body;
	SpriteBatch batch;
	Stage stage;

	private TextureAtlas imgAtlas;
	Sprite background;

	private Music backMusic;

	// CreditsScreen()
	//
	// Constructor initializes things for the screen
	//
	// Called by the previous screen
	public CreditsScreen(BoneyGame game) {
		this.game = game;
		aboutUs = "We ain't about that life.\nWe hardcore, though.  We rise.\nWe ride.";
	}

	// render()
	//
	// Draws what is necessary/functions as an update
	//
	// Naturally called
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// Send us back if we get input
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
		batch.begin();
		background.draw(batch);
		batch.end();
		stage.act(delta);
		batch.begin();
		stage.draw();
		batch.end();
	}

	// resize()
	//
	// Intializes more things after the screen has started
	//
	// Calls when the window is resized
	public void resize(int width, int height) {
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();
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
		stage.addActor(title);
		stage.addActor(body);
	}

	// show()
	//
	// Initializes more things for drawing
	//
	// Called naturally when the picture loads
	public void show() {
		batch = new SpriteBatch();

		bigFont = new BitmapFont(Gdx.files.internal("data/boneyfont.fnt"),
				false);
		littleFont = new BitmapFont(Gdx.files.internal("data/boneybody.fnt"),
				false);
		imgAtlas = new TextureAtlas("data/images/background.atlas");

		background = imgAtlas.createSprite("background-main");

		backMusic = Gdx.audio.newMusic(Gdx.files
				.internal("data/sound/music/Boney Theme.mp3"));
		backMusic.setLooping(true);
		backMusic.play();
	}

	// hide()
	//
	// Not really using this
	//
	// Naturally called
	public void hide() {
		dispose();
	}

	// pause()
	//
	// Not really using this
	//
	// Naturally called
	public void pause() {
		backMusic.pause();
	}

	// resume()
	//
	// Not really using this
	//
	// Naturally called
	public void resume() {
		backMusic.play();
	}

	// dispose()
	//
	// Disposes of resources
	//
	// Naturally called at the end of the screen
	public void dispose() {
		batch.dispose();
		stage.dispose();
		bigFont.dispose();
		littleFont.dispose();
		imgAtlas.dispose();
		backMusic.dispose();
	}
}
// Hunter Heidenreich 2013