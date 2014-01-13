package com.boney.desura.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.boney.desura.BoneyGame;
//---------------------------------------------------------------------------------------------
//
//SelectionScreen.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This is a class that runs the Selection Screen
//
//---------------------------------------------------------------------------------------------

public class SelectionScreen implements Screen {
	public static final String BACKGROUND_TEXTURES = "data/images/background.atlas";
	public static final String SELECTION_TEXTURES = "data/images/selection.atlas";
	private BitmapFont numbers;
	private BoneyGame game;
	private int level;
	private Label label[] = new Label[3];
	private Rectangle stages[] = new Rectangle[3];
	private ShapeRenderer render;
	private Sprite background;
	private Sprite mini;
	private Sprite plaque;
	private Sprite[] levelBox = new Sprite[3];
	private SpriteBatch batch;
	private Stage stage;
	private TextureAtlas atlas;
	private TextureAtlas selection;
	private Vector2 levelLocation;
	private Vector2 stageSize;
	private Vector2 levelSize;
	private Vector2[] stageLocations = new Vector2[3];

	// Initializes the SelectionScreen
	public SelectionScreen(BoneyGame game) {
		this.game = game;

		// Sets up the vectors for placing sprites for levels
		levelSize = new Vector2(240, 144);
		levelLocation = new Vector2((Gdx.graphics.getWidth() - 500) / 2,
				(Gdx.graphics.getHeight() - 300) / 2 + 45);
		stageSize = new Vector2(95, 95);
		for (int i = 0; i < stageLocations.length; i++) {
			stageLocations[i] = new Vector2(
					((Gdx.graphics.getWidth() + (stageSize.x + 45)) / 2 - ((stageSize.x + 45) * i)) + 15,
					levelLocation.y - (stageSize.y + 30));
		}
		for (int i = 0; i < stages.length; i++)
			stages[i] = new Rectangle(stageLocations[i].x, stageLocations[i].y,
					stageSize.x, stageSize.y);

		// Loads the data
		loadData();
	}

	// Updates the screen
	public void render(float delta) {
		if (BoneyGame.getAssetManager().update()) {
			// Clears the screen
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			// Draws the batch
			batch.begin();
			background.draw(batch);
			plaque.draw(batch);
			batch.end();

			// Draws some line based shapes
			render.begin(ShapeType.Line);
			render.rect(levelLocation.x + 130, levelLocation.y + 55,
					levelSize.x, levelSize.y);
			render.end();

			// Draws some full shapes
			render.begin(ShapeType.Filled);
			render.setColor(Color.BLACK);
			render.rect(levelLocation.x + 130, levelLocation.y + 55,
					levelSize.x, levelSize.y);
			render.end();

			// Draws textures again
			batch.begin();
			for (Sprite s : levelBox)
				s.draw(batch);
			mini.draw(batch);
			batch.end();

			// Acts the stage and then draws it
			stage.act(delta);
			batch.begin();
			stage.draw();
			batch.end();

			// Checks if there is input
			checkScreen();
		}
	}

	// Called on screen resize
	public void resize(int width, int height) {
		// Clears and initializes the screen
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		// Sets up the labels and adds them to the stages
		LabelStyle ls = new LabelStyle(numbers, Color.WHITE);
		if (level % 3 > 1)
			label[0] = new Label("3", ls);
		else
			label[0] = new Label("X", ls);
		if (level % 3 > 0)
			label[1] = new Label("2", ls);
		else
			label[1] = new Label("X", ls);
		label[2] = new Label("1", ls);
		for (int i = 0; i < label.length; i++) {
			label[i].setX(((Gdx.graphics.getWidth() + (stageSize.x + 45)) / 2 - ((stageSize.x + 45) * i)) + 60);
			label[i].setY(stageLocations[i].y + 25);
			label[i].setWidth(width);
			stage.addActor(label[i]);
		}

		// Positions the level rectangles
		for (int i = 0; i < levelBox.length; i++)
			levelBox[i].setPosition(stages[i].x, stages[i].y);
	}

	// Called on screen show
	public void show() {
		// Initializes the drawing objects
		batch = new SpriteBatch();
		render = new ShapeRenderer();

		// Retrieves textures
		atlas = BoneyGame.getAssetManager().get(BACKGROUND_TEXTURES,
				TextureAtlas.class);
		selection = BoneyGame.getAssetManager().get(SELECTION_TEXTURES,
				TextureAtlas.class);

		// Sets up sprites
		mini = atlas.createSprite("background-main");
		mini.setScale(0.3f);
		mini.setPosition(0, 25);
		background = atlas.createSprite("background-main");
		background.setPosition(0, 0);
		for (int i = 0; i < levelBox.length; i++)
			levelBox[i] = selection.createSprite("numberBox");
		plaque = selection.createSprite("graveyardCopy");
		plaque.setPosition(levelLocation.x, levelLocation.y);

		// Set up font
		numbers = new BitmapFont(Gdx.files.internal("data/chilly.fnt"), false);
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
		render.dispose();
		batch.dispose();
		stage.dispose();
		numbers.dispose();
	}

	// Loads the data from the files
	public void loadData() {
		FileHandle statLocation = Gdx.files.local("data/stats.bin");
		// If file exists
		if (statLocation.exists()) {
			// Opens it and then modifies it
			byte[] b = statLocation.readBytes();
			level = b[0];
		} else {
			// If it doesn't exists, make a new one
			statLocation.writeBytes(
					new byte[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, false);
			byte[] b = statLocation.readBytes();
			level = b[0];
		}
	}

	// Checks the screen for input
	private void checkScreen() {
		if (Gdx.input.isTouched()) {
			for (int i = 0; i < stages.length; i++) {
				if (stages[i].contains(
						Gdx.graphics.getWidth() - Gdx.input.getX(),
						Gdx.graphics.getHeight() - Gdx.input.getY()))
					if (i <= level % 3)
						game.setScreen(new LevelScreen(game, (level / 3) + 1,
								i + 1));
			}
		}
	}
}
// Hunter Heidenreich 2014