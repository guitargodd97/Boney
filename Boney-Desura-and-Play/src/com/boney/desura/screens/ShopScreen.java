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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.boney.desura.BoneyGame;
import com.boney.desura.other.Boxes;
//---------------------------------------------------------------------------------------------
//
//ShopScreen.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This is a class that runs the Shop Screen.
//
//---------------------------------------------------------------------------------------------

public class ShopScreen implements Screen {
	public static final String BACKGROUND_TEXTURES = "data/images/background.atlas";
	public static final String SHOP_TEXTURES = "data/images/shop.atlas";
	private BitmapFont fontW;
	private boolean notEnough;
	private BoneyGame game;
	private Boxes noCash;
	private FileHandle saveLocation;
	private FileHandle cashLocation;
	private int money;
	private int click;
	private int[] cashPrice = new int[8];
	private int[] locos = new int[8];
	private int[] bytes = new int[10];
	private Label title;
	private Label cash;
	private Label noMoney;
	private Label[] prices = new Label[8];
	private Music song;
	private Rectangle goBack;
	private Rectangle[] rects = new Rectangle[8];
	private Sprite imgBack;
	private Sprite lawnBackground;
	private Sprite[] boxes = new Sprite[8];
	private SpriteBatch batch;
	private Stage stage;
	private TextureAtlas background;
	private TextureAtlas shop;
	private Vector2 shape;
	private Vector2[] topRow = new Vector2[4];
	private Vector2[] bottomRow = new Vector2[4];

	// Initializes the ShopScreen
	public ShopScreen(BoneyGame game) {
		this.game = game;

		// Setup the locations for the shop icons
		shape = new Vector2((Gdx.graphics.getWidth() - 60) / 4,
				(Gdx.graphics.getHeight() - 55) / 2 - 5);
		for (int i = 0; i < topRow.length; i++)
			topRow[i] = new Vector2((shape.x + 4) * i + 7, 30);
		for (int i = 0; i < bottomRow.length; i++)
			bottomRow[i] = new Vector2((shape.x + 4) * i + 7, shape.y + 60);
		for (int i = 0; i < 4; i++)
			rects[i] = new Rectangle(topRow[i].x, topRow[i].y, shape.x, shape.y);
		for (int i = 4; i < 8; i++)
			rects[i] = new Rectangle(bottomRow[i - 4].x, bottomRow[i - 4].y,
					shape.x, shape.y);

		// Load the files and retreive data
		saveLocation = Gdx.files.local("data/stats.bin");
		cashLocation = Gdx.files.local("data/money.txt");
		byte[] b;
		if (saveLocation.exists())
			b = saveLocation.readBytes();
		else {
			saveLocation.writeBytes(
					new byte[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, false);
			b = saveLocation.readBytes();
		}
		for (int i = 0; i < b.length; i++)
			bytes[i] = (int) b[i];

		// Set the prices
		cashPrice[0] = ((int) b[4] * 20) * 100;
		cashPrice[1] = ((int) b[7] * 20) * 100;
		cashPrice[2] = ((int) b[6] * 20) * 100;
		cashPrice[3] = ((int) b[9] * 20) * 100;
		cashPrice[4] = ((int) b[5] * 20) * 100;
		cashPrice[5] = ((int) b[1] * 20) * 100;
		cashPrice[6] = ((int) b[2] * 20) * 100;
		cashPrice[7] = ((int) b[3] * 20) * 100;

		// Set the indices for future setting and loading
		locos[0] = 4;
		locos[1] = 7;
		locos[2] = 6;
		locos[3] = 9;
		locos[4] = 5;
		locos[5] = 1;
		locos[6] = 2;
		locos[7] = 3;

		// Click buffer
		click = 0;

		// Go Back icon place
		goBack = new Rectangle(Gdx.graphics.getWidth() - 40, 10, 35, 35);

		// Not enough money initialization
		noCash = new Boxes(4);
		notEnough = false;
	}

	// Updates the screen
	public void render(float delta) {
		if (BoneyGame.getAssetManager().update()) {
			// Clears the screen
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			// Draw the batch
			batch.begin();
			lawnBackground.draw(batch);
			for (Sprite s : boxes)
				s.draw(batch);
			imgBack.draw(batch);
			if (notEnough)
				noMoney.draw(batch, 1);
			batch.end();

			// Draws the stage
			stage.draw();

			// Checks for input
			for (int i = 0; i < rects.length; i++) {
				if (Gdx.input.isTouched()) {
					if (click == 0) {
						click = 1;
					}
					if (rects[i].contains(Gdx.input.getX(), Gdx.input.getY())
							&& click == 2) {
						click = 0;

						if (money > cashPrice[i]) {
							update(i);
						} else if (notEnough) {
							notEnough = false;
						} else {
							notEnough = true;
						}
					}
					if (goBack.contains(Gdx.input.getX(), Gdx.input.getY())
							&& click == 2)
						game.setScreen(new ModeScreen(game));
				} else if (click == 1) {
					click = 2;
				}
			}
		}
	}

	// Called on screen resize
	public void resize(int width, int height) {
		// Initializes the stage
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		// Setup the labels
		LabelStyle ls = new LabelStyle(fontW, Color.WHITE);
		if (bytes[5] < 6)
			prices[4] = new Label("Health\nLevel: " + (int) bytes[5] + " = $"
					+ ((int) bytes[5] * 20) * 1.00 + "0", ls);
		else
			prices[4] = new Label("Health\nLevel: MAXED", ls);
		if (bytes[1] < 6)
			prices[5] = new Label("Speed\nLevel: " + (int) bytes[1] + " = $"
					+ ((int) bytes[1] * 20) * 1.00 + "0", ls);
		else
			prices[5] = new Label("Speed\nLevel: MAXED", ls);
		if (bytes[2] < 6)
			prices[6] = new Label("Cash Bonus\nLevel: " + (int) bytes[2]
					+ " = $" + ((int) bytes[2] * 20) * 1.00 + "0", ls);
		else
			prices[6] = new Label("Cash Bonus\nLevel: MAXED", ls);
		if (bytes[3] < 6)
			prices[7] = new Label("PP Duration\nLevel: " + (int) bytes[3]
					+ " = $" + ((int) bytes[3] * 20) * 1.00 + "0", ls);
		else
			prices[7] = new Label("PP Duration\nLevel: MAXED", ls);
		if (bytes[4] < 6)
			prices[0] = new Label("PP Frequency\nLevel: " + (int) bytes[4]
					+ " = $" + ((int) bytes[4] * 20) * 1.00 + "0", ls);
		else
			prices[0] = new Label("PP Frequency\nLevel: MAXED", ls);
		if (bytes[7] < 6)
			prices[1] = new Label("Score Bonus\nLevel: " + (int) bytes[7]
					+ " = $" + ((int) bytes[7] * 20) * 1.00 + "0", ls);
		else
			prices[1] = new Label("Score Bonus\nLevel: MAXED", ls);
		if (bytes[6] < 6)
			prices[2] = new Label("Dog Repelent\nLevel: " + (int) bytes[6]
					+ " = $" + ((int) bytes[6] * 20) * 1.00 + "0", ls);
		else
			prices[2] = new Label("Dog Repelent\nLevel: MAXED", ls);
		if (bytes[9] < 6)
			prices[3] = new Label("Discount\nLevel: " + (int) (bytes[9] + 1)
					+ " = $" + ((int) (bytes[9] + 1) * 20) * 1.00 + "0", ls);
		else
			prices[3] = new Label("Discount\nLevel: MAXED", ls);
		for (int i = 0; i < prices.length; i++) {
			prices[i].setPosition(rects[i].x + 30, rects[i].y - 30);
			prices[i].setAlignment(Align.center);
			stage.addActor(prices[i]);
		}
		title = new Label("SHOP", ls);
		title.setPosition(360, 450);
		title.setAlignment(Align.center);

		// Read the money
		String c;
		if (cashLocation.exists())
			c = cashLocation.readString();
		else {
			cashLocation.writeString("0", false);
			c = cashLocation.readString();
		}

		// Setup the money label
		money = Integer.parseInt(c);
		if (Integer.parseInt(c) % 100 == 0)
			cash = new Label("Cash: $" + (Float.parseFloat(c) / 100) + "0", ls);
		else if (Integer.parseInt(c) % 10 == 0)
			cash = new Label("Cash: $" + (Float.parseFloat(c) / 100) + "0", ls);
		else
			cash = new Label("Cash: $" + (Float.parseFloat(c) / 100), ls);
		cash.setPosition(0, 450);
		cash.setAlignment(Align.center);

		// Adds labels to stage
		stage.addActor(cash);
		stage.addActor(title);

		// Setup the no money label
		noMoney = new Label(noCash.getMessage(), ls);
		noMoney.setY(200);
		noMoney.setWidth(width);
		noMoney.setAlignment(Align.center);
	}

	// Called on screen show
	public void show() {
		// Initializes the batch
		batch = new SpriteBatch();

		// Setup the music
		song = Gdx.audio.newMusic(Gdx.files
				.internal("data/sound/music/Shop.mp3"));
		song.play();
		song.setLooping(true);

		// Retrieves the fonts
		fontW = new BitmapFont(Gdx.files.internal("data/chilly.fnt"), false);

		// Retrieves the textures
		background = BoneyGame.getAssetManager().get(BACKGROUND_TEXTURES,
				TextureAtlas.class);
		shop = BoneyGame.getAssetManager().get(SHOP_TEXTURES,
				TextureAtlas.class);

		// Setup sprites
		imgBack = shop.createSprite("back");
		imgBack.setX(Gdx.graphics.getWidth() - 40);
		imgBack.setY(Gdx.graphics.getHeight() - 40);
		lawnBackground = background.createSprite("background-main");
		lawnBackground.setPosition(0, 0);
		lawnBackground.setColor(1, 1, 1, 0.3f);
		boxes[4] = shop.createSprite("storeNA");
		boxes[4].setPosition(rects[4].x, rects[4].y);
		boxes[5] = shop.createSprite("storeSpeed");
		boxes[5].setPosition(rects[5].x, rects[5].y);
		boxes[6] = shop.createSprite("storeNA");
		boxes[6].setPosition(rects[6].x, rects[6].y);
		boxes[7] = shop.createSprite("storeDur");
		boxes[7].setPosition(rects[7].x, rects[7].y);
		boxes[0] = shop.createSprite("storeNA");
		boxes[0].setPosition(rects[0].x, rects[0].y);
		boxes[1] = shop.createSprite("storeScore");
		boxes[1].setPosition(rects[1].x, rects[1].y);
		boxes[2] = shop.createSprite("storeNA");
		boxes[2].setPosition(rects[2].x, rects[2].y);
		boxes[3] = shop.createSprite("storeNA");
		boxes[3].setPosition(rects[3].x, rects[3].y);
		for (Sprite s : boxes)
			s.setScale(0.75f);
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
		fontW.dispose();
		stage.dispose();
		song.dispose();
	}

	// Updates the stats and money
	private void update(int i) {
		// Case for which stat is being updated
		switch (i) {
		case (0):
			i = 4;
			break;
		case (1):
			i = 5;
			break;
		case (2):
			i = 6;
			break;
		case (3):
			i = 7;
			break;
		case (4):
			i = 0;
			break;
		case (5):
			i = 1;
			break;
		case (6):
			i = 2;
			break;
		case (7):
			i = 3;
			break;
		}
		// If that stat is within range
		if (bytes[locos[i]] < 6) {
			if (money >= cashPrice[i]) {
				money -= cashPrice[i];
				bytes[locos[i]] += 1;
			}
			// Save the files
			byte b[] = new byte[bytes.length];
			for (int x = 0; x < bytes.length; x++)
				b[x] = (byte) bytes[x];
			saveLocation.writeBytes(b, false);
			cashLocation.writeString("" + money, false);
			// Update the text
			switch (i) {
			case (0):
				prices[0].setText("PP Frequency\nLevel: " + (int) bytes[4]
						+ " = $" + ((int) bytes[4] * 20) * 1.00 + "0");
				break;
			case (1):
				prices[1].setText("Score Bonus\nLevel: " + (int) bytes[7]
						+ " = $" + ((int) bytes[7] * 20) * 1.00 + "0");
				break;
			case (2):
				prices[2].setText("Dog Repelent\nLevel: " + (int) bytes[6]
						+ " = $" + ((int) bytes[6] * 20) * 1.00 + "0");
				break;
			case (3):
				prices[3].setText("Discount\nLevel: " + (int) (bytes[9] + 1)
						+ " = $" + ((int) (bytes[9] + 1) * 20) * 1.00 + "0");
				break;
			case (4):
				prices[4].setText("Health\nLevel: " + (int) bytes[5] + " = $"
						+ ((int) bytes[5] * 20) * 1.00 + "0");
				break;
			case (5):
				prices[5].setText("Speed\nLevel: " + (int) bytes[1] + " = $"
						+ ((int) bytes[1] * 20) * 1.00 + "0");
				break;
			case (6):
				prices[6].setText("Cash Bonus\nLevel: " + (int) bytes[2]
						+ " = $" + ((int) bytes[2] * 20) * 1.00 + "0");
				break;
			case (7):
				prices[7].setText("PP Duration\nLevel: " + (int) bytes[3]
						+ " = $" + ((int) bytes[3] * 20) * 1.00 + "0");
				break;
			}

			// Re-read the cash
			String c;
			if (cashLocation.exists())
				c = cashLocation.readString();
			else {
				cashLocation.writeString("0", false);
				c = cashLocation.readString();
			}

			// Update the label
			money = Integer.parseInt(c);
			if (money % 100 == 0)
				cash.setText("Cash: $" + (Float.parseFloat(c) / 100) + ".00");
			else if (Integer.parseInt(c) % 10 == 0)
				cash.setText("Cash: $" + (Float.parseFloat(c) / 100) + "0");
			else
				cash.setText("Cash: $" + (Float.parseFloat(c) / 100));
		} else {
			// If the stat is maxed
			switch (i) {
			case (0):
				prices[0].setText("PP Frequency\nLevel: MAXED");
				break;
			case (1):
				prices[1].setText("Score Bonus\nLevel: MAXED");
				break;
			case (2):
				prices[2].setText("Dog Repelent\nLevel: MAXED");
				break;
			case (3):
				prices[3].setText("Discount\nLevel: MAXED");
				break;
			case (4):
				prices[4].setText("Health\nLevel: MAXED");
				break;
			case (5):
				prices[5].setText("Speed\nLevel: MAXED");
				break;
			case (6):
				prices[6].setText("Cash Bonus\nLevel: MAXED");
				break;
			case (7):
				prices[7].setText("PP Duration\nLevel: MAXED");
				break;
			}
		}
	}
}
// Hunter Heidenreich 2014