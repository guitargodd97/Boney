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
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This class is the Gameover Screen that a player is sent to when they lose/win.
//
//---------------------------------------------------------------------------------------------

public class GameoverScreen implements Screen {
	public static final String BACKGROUND_TEXTURES = "data/images/background.atlas";
	public static final String BUTTON_TEXTURES = "data/images/button.pack";
	private BitmapFont fontW;
	private BitmapFont b;
	private boolean levelCompleted;
	private BoneyGame game;
	private int score;
	private int level;
	private int stageNum;
	private int dogNum;
	private int buttonWidth;
	private int buttonHeight;
	private int buttonY;
	private Label label;
	private Music song;
	private ShapeRenderer render;
	private Skin skin;
	private Sprite background;
	private SpriteBatch batch;
	private Stage stage;
	private TextureAtlas atlas;
	private TextureAtlas buttAtlas;
	private TextButton startButton;
	private TextButton quitButton;
	private Vector2 levelLocation;
	private Vector2 levelSize;

	// Initializes the GameoverScreen
	public GameoverScreen(BoneyGame game, boolean end, double[] data) {
		this.game = game;

		// Parse the data from previous screen
		levelCompleted = end;
		score = (int) data[0];
		level = (int) data[2];
		stageNum = (int) data[3];
		dogNum = (int) data[4];

		// Setup location for displaying data
		levelSize = new Vector2(500, 300);
		levelLocation = new Vector2(
				(Gdx.graphics.getWidth() - levelSize.x) / 2,
				(Gdx.graphics.getHeight() - levelSize.y) / 2);

		// Setup button sizes and locations
		buttonWidth = 200;
		buttonHeight = 75;
		buttonY = 15;
	}

	// Updates the screen
	public void render(float delta) {
		if (BoneyGame.getAssetManager().update()) {
			// Clear the screen
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			// Draw shapes
			render.begin(ShapeType.Filled);
			render.setColor(0.1f, 0.1f, 0.1f, 1f);
			render.rect(levelLocation.x, levelLocation.y, levelSize.x,
					levelSize.y);
			render.end();

			// Draw textures from batch
			batch.begin();
			background.draw(batch);
			batch.end();

			// Update and draw the stage
			stage.act(delta);
			batch.begin();
			stage.draw();
			batch.end();
		}
	}

	// Called when the screen is resized
	public void resize(int width, int height) {
		// Initialize and clear the stage
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		// Setup the fonts for the buttons and labels
		LabelStyle ls = new LabelStyle(fontW, Color.WHITE);
		TextButtonStyle tbStyle = new TextButtonStyle();
		tbStyle.up = skin.getDrawable("buttonnormal");
		tbStyle.down = skin.getDrawable("buttonpressed");
		tbStyle.font = b;

		// Setup the start button
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

		// Setup the quit button
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

		// If player won the level
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

		// Position the text
		label.setX(0);
		label.setY(levelLocation.y + (levelSize.y / 2) - 35);
		label.setWidth(width);
		label.setAlignment(Align.center);

		// Add labels and buttons to the stage
		stage.addActor(label);
		stage.addActor(quitButton);
		stage.addActor(startButton);
	}

	// Called on window show
	public void show() {
		// Initialize drawing objects
		render = new ShapeRenderer();
		batch = new SpriteBatch();

		// Retrieve textures
		atlas = BoneyGame.getAssetManager().get(BACKGROUND_TEXTURES,
				TextureAtlas.class);
		buttAtlas = BoneyGame.getAssetManager().get(BUTTON_TEXTURES,
				TextureAtlas.class);

		// Initialize button skins and sprites
		skin = new Skin();
		skin.addRegions(buttAtlas);
		background = atlas.createSprite("background-main");
		background.setPosition(0, 0);
		background.setColor(1, 1, 1, 0.3f);

		// Initialize fonts
		fontW = new BitmapFont(Gdx.files.internal("data/chilly.fnt"), false);
		b = new BitmapFont(Gdx.files.internal("data/boneyfontblack.fnt"), false);

		// Intialize music
		song = Gdx.audio.newMusic(Gdx.files
				.internal("data/sound/music/Gameover.mp3"));
		song.setLooping(true);
		song.play();
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

	// Disposes of disposable resources
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
// Hunter Heidenreich 2014