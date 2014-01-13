package com.boney.desura.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.boney.desura.other.Boxes;
//---------------------------------------------------------------------------------------------
//
//MenuScreen.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This is a class that runs the Menu Screen.
//
//---------------------------------------------------------------------------------------------

public class MenuScreen implements Screen {
	public static final String BACKGROUND_TEXTURES = "data/images/background.atlas";
	public static final String BUTTON_TEXTURES = "data/images/button.pack";
	private BitmapFont b;
	private BitmapFont f;
	private boolean deletion;
	private BoneyGame game;
	private Boxes del;
	private int buttonWidth;
	private int buttonHeight;
	private int buttonY;
	private Label label;
	private Label delLabel;
	private Music m;
	private Skin skin;
	private Sprite background;
	private SpriteBatch batch;
	private Stage stage;
	private Stage dStage;
	private String fontW;
	private String fontB;
	private String music;
	private TextureAtlas menuAtlas;
	private TextureAtlas buttAtlas;
	private TextButton startButton;
	private TextButton quitButton;
	private TextButton creditButton;
	private TextButton deleteButton;
	private TextButton yes;
	private TextButton no;

	// Initializes the MenuScreen
	public MenuScreen(BoneyGame game) {
		this.game = game;

		// Sets up button sizes
		buttonWidth = 200;
		buttonHeight = 75;
		buttonY = 15;

		// Places for assets
		fontB = "data/boneyfontblack.fnt";
		fontW = "data/boneyfont.fnt";
		music = "data/sound/music/Boney Theme.mp3";

		// Delete messages and bools
		deletion = false;
		del = new Boxes(2);
	}

	// Updates the screen
	public void render(float delta) {
		if (BoneyGame.getAssetManager().update()) {
			// Clears the screen
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			// Dims the sprite
			background.setColor(new Color(Color.LIGHT_GRAY));

			// Draws the batch
			batch.begin();
			background.draw(batch);
			batch.end();

			// Determines the correct stage and acts and draws it
			if (!deletion)
				stage.act(delta);
			else
				dStage.act(delta);
			batch.begin();
			if (!deletion)
				stage.draw();
			else
				dStage.draw();
			batch.end();
		}
	}

	// Called on screen resize
	public void resize(int width, int height) {
		// Initializes the stages
		if (stage == null)
			stage = new Stage(width, height, true);
		if (dStage == null)
			dStage = new Stage(width, height, true);
		stage.clear();
		dStage.clear();
		if (!deletion)
			Gdx.input.setInputProcessor(stage);
		else
			Gdx.input.setInputProcessor(dStage);

		// Sets up the fonts and buttons
		TextButtonStyle tbStyle = new TextButtonStyle();
		tbStyle.up = skin.getDrawable("buttonnormal");
		tbStyle.down = skin.getDrawable("buttonpressed");
		tbStyle.font = b;

		// Setup start button
		startButton = new TextButton("Start", tbStyle);
		startButton.setWidth(buttonWidth);
		startButton.setHeight(buttonHeight);
		startButton.setX(Gdx.graphics.getWidth() / 2 - startButton.getWidth()
				/ 2);
		startButton.setY(buttonY + 2 * (buttonHeight + 10));
		startButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new ModeScreen(game));
			}
		});

		// Setup quit button
		quitButton = new TextButton("Quit", tbStyle);
		quitButton.setWidth(buttonWidth);
		quitButton.setHeight(buttonHeight);
		quitButton
				.setX(Gdx.graphics.getWidth() / 2 - quitButton.getWidth() / 2);
		quitButton.setY(buttonY);
		quitButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();
			}
		});

		// Setup credits button
		creditButton = new TextButton("Credits", tbStyle);
		creditButton.setWidth(buttonWidth);
		creditButton.setHeight(buttonHeight);
		creditButton.setX(Gdx.graphics.getWidth() / 2 - creditButton.getWidth()
				/ 2);
		creditButton.setY(buttonY + buttonHeight + 10);
		creditButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new CreditsScreen(game));
			}
		});

		// Setup delete button
		deleteButton = new TextButton("Delete", tbStyle);
		deleteButton.setWidth(buttonWidth - 45);
		deleteButton.setHeight(buttonHeight - 35);
		deleteButton.setX(5);
		deleteButton.setY(quitButton.getY());
		deleteButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				deletion = true;
				Gdx.input.setInputProcessor(dStage);
			}
		});

		// Setup yes button
		yes = new TextButton("Yes", tbStyle);
		yes.setWidth(buttonWidth);
		yes.setHeight(buttonHeight);
		yes.setX(Gdx.graphics.getWidth() / 4 - startButton.getWidth() / 2 + 75);
		yes.setY(buttonY + 2 * (buttonHeight - 30));
		yes.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				deletion = false;
				Gdx.app.log("sdfs", "deleted");
				FileHandle saveLocation, cashLocation;
				saveLocation = Gdx.files.local("data/stats.bin");
				cashLocation = Gdx.files.local("data/money.txt");
				saveLocation.delete();
				saveLocation.writeBytes(new byte[] { 0, 1, 1, 1, 1, 1, 1, 1, 1,
						0 }, false);
				cashLocation.delete();
				cashLocation.writeString("0", false);
				Gdx.input.setInputProcessor(stage);
			}
		});

		// Setup no button
		no = new TextButton("No", tbStyle);
		no.setWidth(buttonWidth);
		no.setHeight(buttonHeight);
		no.setX(3 * (Gdx.graphics.getWidth() / 4) - startButton.getWidth() / 2
				- 75);
		no.setY(buttonY + 2 * (buttonHeight - 30));
		no.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				deletion = false;
				Gdx.input.setInputProcessor(stage);
			}
		});

		// Setup labels
		LabelStyle ls = new LabelStyle(f, Color.WHITE);
		label = new Label("Boney the Skeleton", ls);
		label.setX(0);
		label.setY(400);
		label.setWidth(Gdx.graphics.getWidth());
		label.setAlignment(Align.center);

		// Adds buttons and labels to stage
		stage.addActor(startButton);
		stage.addActor(creditButton);
		stage.addActor(quitButton);
		stage.addActor(deleteButton);
		stage.addActor(label);

		// Setup delete label
		delLabel = new Label(del.getMessage(), ls);
		delLabel.setY(200);
		delLabel.setAlignment(Align.center);
		delLabel.setWidth(width);

		// Adds buttons and labels to delete stage
		dStage.addActor(delLabel);
		dStage.addActor(yes);
		dStage.addActor(no);
	}

	// Called on screen show
	public void show() {
		// Initializes the batch
		batch = new SpriteBatch();

		// Retrieves the textures
		menuAtlas = BoneyGame.getAssetManager().get(BACKGROUND_TEXTURES,
				TextureAtlas.class);
		buttAtlas = BoneyGame.getAssetManager().get(BUTTON_TEXTURES,
				TextureAtlas.class);

		// Sets up the sprites
		background = menuAtlas.createSprite("background-main");
		background.setPosition(0, 0);

		// Retrieves the fonts
		b = new BitmapFont(Gdx.files.internal(fontB));
		f = new BitmapFont(Gdx.files.internal(fontW));

		// Sets up the button textures
		skin = new Skin();
		skin.addRegions(buttAtlas);

		// Setup the music
		m = Gdx.audio.newMusic(Gdx.files.internal(music));
		m.setLooping(true);
		m.play();
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
		batch.dispose();
		skin.dispose();
		stage.dispose();
		dStage.dispose();
		f.dispose();
		b.dispose();
		m.dispose();
	}
}
// Hunter Heidenreich 2014