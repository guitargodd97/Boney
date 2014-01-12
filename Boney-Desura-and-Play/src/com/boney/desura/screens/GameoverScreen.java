package com.boney.desura.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.boney.desura.BoneyGame;
//---------------------------------------------------------------------------------------------
//
//GameoverScreen.java
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

public class GameoverScreen implements Screen {
	BoneyGame game;
	ShapeRenderer render;
	Vector2 levelLocation, levelSize;
	BitmapFont numbers;
	Stage stage;
	Label label;
	SpriteBatch batch;
	Sprite background;
	private BitmapFont fontW;
	private Music song;
	int score, level, stageNum, dogNum;
	double cash;
	boolean levelCompleted;
	public static final String BACKGROUND_TEXTURES = "data/images/background.atlas";
	public static final String BUTTON_TEXTURES = "data/images/button.pack";
	private TextureAtlas atlas, buttAtlas;
	int buttonWidth, buttonHeight, buttonY;
	Skin skin;
	TextButton startButton, quitButton;
	private BitmapFont b;

	// CreditsScreen()
	//
	// Constructor initializes things for the screen
	//
	// Called by the previous screen
	public GameoverScreen(BoneyGame game, boolean end, double[] data) {
		this.game = game;
		levelCompleted = end;
		render = new ShapeRenderer();
		batch = new SpriteBatch();
		levelSize = new Vector2(500, 300);
		levelLocation = new Vector2(
				(Gdx.graphics.getWidth() - levelSize.x) / 2,
				(Gdx.graphics.getHeight() - levelSize.y) / 2);
		score = (int) data[0];
		cash = data[1];
		level = (int) data[2];
		stageNum = (int) data[3];
		dogNum = (int) data[4];
		buttonWidth = 200;
		buttonHeight = 75;
		buttonY = 15;
	}

	// render()
	//
	// Draws what is necessary/functions as an update
	//
	// Naturally called
	public void render(float delta) {
		if (game.getAssetManager().update()) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			render.begin(ShapeType.Filled);
			render.setColor(0.1f, 0.1f, 0.1f, 1f);
			render.rect(levelLocation.x, levelLocation.y, levelSize.x,
					levelSize.y);
			render.end();
			batch.begin();
			background.draw(batch);
			batch.end();
			stage.act(delta);
			batch.begin();
			stage.draw();
			batch.end();
		}
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
		Gdx.input.setInputProcessor(stage);
		LabelStyle ls = new LabelStyle(fontW, Color.WHITE);
		TextButtonStyle tbStyle = new TextButtonStyle();
		tbStyle.up = skin.getDrawable("buttonnormal");
		tbStyle.down = skin.getDrawable("buttonpressed");
		tbStyle.font = b;
		startButton = new TextButton("Again", tbStyle);
		startButton.setWidth(buttonWidth);
		startButton.setHeight(buttonHeight);
		startButton.setX(Gdx.graphics.getWidth() / 4 - startButton.getWidth()
				/ 2 + 75);
		startButton.setY(buttonY + 2 * (buttonHeight - 30));
		startButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new LevelScreen(game, level, stageNum));
			}
		});

		quitButton = new TextButton("Menu", tbStyle);
		quitButton.setWidth(buttonWidth);
		quitButton.setHeight(buttonHeight);
		quitButton.setX(3 * (Gdx.graphics.getWidth() / 4)
				- startButton.getWidth() / 2 - 75);
		quitButton.setY(buttonY + 2 * (buttonHeight - 30));
		quitButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MenuScreen(game));
			}
		});

		if (levelCompleted) {
			if (stageNum <= 3)
				label = new Label("Level: " + level + "." + stageNum
						+ "\nPoints: " + score + " + "
						+ ((level - 1) * 3000 + (500 * stageNum)) + " = "
						+ (score += ((level - 1) * 3000 + (500 * stageNum)))
						+ "\nLEVEL COMPLETE!", ls);
			else
				label = new Label("Dogs Dodged: " + dogNum + "\nPoints: "
						+ score + " + "
						+ ((level - 1) * 3000 + (500 * stageNum)) + " = "
						+ (score += ((level - 1) * 3000 + (500 * stageNum)))
						+ "\nLEVEL COMPLETE!", ls);
		} else {
			if (stageNum <= 3)
				label = new Label("Level: " + level + "." + stageNum
						+ "\nPoints: " + score + "\nLevel failed......", ls);
			else
				label = new Label("Dogs Dodged: " + dogNum + "\nPoints: "
						+ score + "\nLevel failed......", ls);
		}
		label.setX(0);
		label.setY(levelLocation.y + (levelSize.y / 2) - 35);
		label.setWidth(width);
		label.setAlignment(Align.center);
		stage.addActor(label);
		stage.addActor(quitButton);
		stage.addActor(startButton);
	}

	// show()
	//
	// Initializes more things for drawing
	//
	// Called naturally when the picture loads
	public void show() {
		skin = new Skin();
		batch = new SpriteBatch();
		atlas = game.getAssetManager().get(BACKGROUND_TEXTURES, TextureAtlas.class);
		buttAtlas = game.getAssetManager().get(BUTTON_TEXTURES, TextureAtlas.class);
		skin.addRegions(buttAtlas);
		background = atlas.createSprite("background-main");
		background.setPosition(0, 0);
		background.setColor(1, 1, 1, 0.3f);
		fontW = new BitmapFont(Gdx.files.internal("data/chilly.fnt"), false);
		b = new BitmapFont(Gdx.files.internal("data/boneyfontblack.fnt"), false);
		song = Gdx.audio.newMusic(Gdx.files
				.internal("data/sound/music/Gameover.mp3"));
		song.setLooping(true);
		song.play();
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

	}

	// resume()
	//
	// Not really using this
	//
	// Naturally called
	public void resume() {

	}

	// dispose()
	//
	// Disposes of resources
	//
	// Naturally called at the end of the screen
	public void dispose() {
		render.dispose();
		stage.dispose();
		batch.dispose();
		fontW.dispose();
		b.dispose();
		song.dispose();
		skin.dispose();
	}
}
// Hunter Heidenreich 2013