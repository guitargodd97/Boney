package com.boney.desura.screens;

import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.boney.desura.BoneyGame;
import com.boney.desura.dogs.BigDog;
import com.boney.desura.dogs.GenericDog;
import com.boney.desura.dogs.MediumDog;
import com.boney.desura.dogs.SmallDog;
import com.boney.desura.moveable.Boney;
import com.boney.desura.moveable.Powerup;
import com.boney.desura.other.Boxes;
//---------------------------------------------------------------------------------------------
//
//LevelScreen.java
//Last Revised: 1/12/2013
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This class is the Level Screen that you actually play on.
//
//---------------------------------------------------------------------------------------------

public class LevelScreen implements Screen {
	public static final String BACKGROUND_TEXTURES = "data/images/background.atlas";
	public static final String BONEY_SPRITE_SHEET = "data/images/boney.atlas";
	public static final String DOGS_SPRITE_SHEET = "data/images/dog.atlas";
	public static final String LEVEL_TEXTURES = "data/images/level.atlas";
	public static final String BUTTON_TEXTURES = "data/images/button.pack";
	private final Array<Powerup> activePowerups = new Array<Powerup>();
	private final Array<GenericDog> activeDogs = new Array<GenericDog>();
	private final Pool<SmallDog> smallDogPool = new Pool<SmallDog>() {
		protected SmallDog newObject() {
			return new SmallDog();
		}
	};
	private final Pool<MediumDog> medDogPool = new Pool<MediumDog>() {
		protected MediumDog newObject() {
			return new MediumDog();
		}
	};
	private final Pool<BigDog> bigDogPool = new Pool<BigDog>() {
		protected BigDog newObject() {
			return new BigDog();
		}
	};
	private final Pool<Powerup> powerPool = new Pool<Powerup>() {
		protected Powerup newObject() {
			return new Powerup();
		}
	};
	private Array<Integer> waves = new Array<Integer>();
	private BitmapFont b;
	private BitmapFont fontW;
	private boolean paused;
	private boolean showIntroBox;
	private boolean survival;
	private Boney boney;
	private BoneyGame game;
	private Boxes intro;
	private Boxes pause;
	private float jumpRadius;
	private float jumpX;
	private float jumpY;
	private float crouchRadius;
	private float crouchX;
	private float crouchY;
	private float score;
	private int waveIndex;
	private int level;
	private int stageNum;
	private int introState;
	private int powerTimer;
	private int powerLimit;
	private int buttonWidth;
	private int buttonHeight;
	private int enterP;
	private int pP;
	private int buttonY;
	private Label label;
	private Label moneyLabel;
	private Label pntLabel;
	private Label pauseLabel;
	private Label introLabel;
	private Music song;
	private Rectangle jumpRect;
	private Rectangle crouchRect;
	private ShapeRenderer render;
	private Skin skin;
	private Sprite back;
	private Sprite jumpUp;
	private Sprite jumpDown;
	private Sprite crouchUp;
	private Sprite crouchDown;
	private SpriteBatch batch;
	private Stage stage;
	private Stage pStage;
	private TextureAtlas background;
	private TextureAtlas boneySheet;
	private TextureAtlas dog;
	private TextureAtlas levelSheet;
	private TextureAtlas buttAtlas;
	private TextButton startButton;
	private TextButton quitButton;

	// Initializes the LevelScreen
	public LevelScreen(BoneyGame game, int l, int s) {
		this.game = game;

		// Sets the jump buttons
		jumpRadius = 32;
		crouchRadius = jumpRadius;
		jumpX = 35;
		jumpY = 35;
		crouchX = Gdx.graphics.getWidth() - jumpX;
		crouchY = jumpY;
		jumpRect = new Rectangle(0, 410, jumpRadius * 2, jumpRadius * 2);
		crouchRect = new Rectangle(736, 410, crouchRadius * 2, crouchRadius * 2);

		// Sets the other buttons
		buttonWidth = 200;
		buttonHeight = 75;
		buttonY = 15;

		// Sets the score
		score = 0;

		// Sets the intro
		introState = 0;

		// Sets the game to unpaused
		paused = false;

		// Sets the waves based on stage and level
		level = l;
		stageNum = s;
		if (level == 1 && stageNum == 1) {
			showIntroBox = true;
			for (int i = 0; i < 9; i++) {
				if (i < 3)
					waves.add(0);
				else if (i < 6)
					waves.add(1);
				else
					waves.add(2);
			}
			survival = false;
		} else if (stageNum == 10) {
			survival = true;
			Random ran = new Random();
			for (int i = 0; i < 100; i++)
				waves.add(ran.nextInt(3));
		} else {
			Random ran = new Random();
			for (int i = 0; i < 30; i++)
				waves.add(ran.nextInt(3));
			survival = false;
		}
		waveIndex = 0;

		// Initializes the images
		initializeImages();

		// Setup boney
		boney = new Boney();
		boney.setState(2);

		// Setup the dogs
		GenericDog dog = whichDog();
		dog.init();
		activeDogs.add(dog);

		// Sets up the powerup limits
		powerTimer = 0;
		powerLimit = 300;
		Powerup p = powerPool.obtain();
		p.init();
		activePowerups.add(p);

		// Sets up the info boxes
		pause = new Boxes(0);
		intro = new Boxes(1);
	}

	// Updates the screen
	public void render(float delta) {
		if (BoneyGame.getAssetManager().update()) {
			// Clears the screen
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			// Draws the batch
			batch.begin();
			back.draw(batch);
			boney.draw(batch);
			for (GenericDog d : activeDogs)
				d.draw(batch);

			// Draws Android buttons if needed
			if (game.getApp() == ApplicationType.Android) {
				jumpUp.draw(batch);
				crouchUp.draw(batch);
				if (Gdx.input.isTouched()) {
					if (jumpRect.contains(Gdx.input.getX(), Gdx.input.getY()))
						jumpDown.draw(batch);
					if (crouchRect.contains(Gdx.input.getX(), Gdx.input.getY()))
						crouchDown.draw(batch);
				}
			}

			// If the game is explaining how to be played
			if (showIntroBox)
				doIntro();
			// If not paused and Boney is alive
			else if (boney.getLife() && !paused) {
				// Move Boney
				boney.move(jumpRect, crouchRect);

				// Update the powerups
				for (Powerup p : activePowerups)
					p.draw(batch);

				// Update the dogs
				for (int i = 0; i < activeDogs.size; i++) {
					activeDogs.get(i).move();

					// If a dog touches Boney
					if (activeDogs.get(i).getRect().overlaps(boney.getRect()))
						boney.loseLive();

					// If a dog is offscreen
					if (activeDogs.get(i).getDone()) {
						// Give score and remove the dog
						score += activeDogs.get(i).getWorth()
								* boney.getScoreMultiplier();
						activeDogs.get(i).reset();
						activeDogs.removeIndex(i);

						// If things should keep going
						if (waveIndex < waves.size - 1 || survival) {
							// If this is survival and we are exceeding 100 dogs
							if (survival && waveIndex > 100) {
								// Restart
								waveIndex = 0;
								waves.clear();
								Random ran = new Random();
								for (int is = 0; is < 100; is++)
									waves.add(ran.nextInt(3));
							}

							// Increase the wave number
							waveIndex++;

							// Create a new dog
							GenericDog g = whichDog();
							g.init();
							activeDogs.add(g);

							// Exit the loop
							i = 500;

							// Update hthe label
							if (!survival)
								label.setText("Stage: " + level + "."
										+ stageNum + "\nDogs: "
										+ (waveIndex + 1) + "/" + waves.size);
							else
								label.setText("Stage: SURVIVAL.\nDogs: "
										+ (waveIndex + 1));
						} else {
							// If the game is completed, save the stats
							if (stageNum < 2)
								boney.saveStat((level - 1) * 3 + stageNum);
							else
								boney.saveStat((level - 1) * 3 + stageNum - 1);

							// Onto the next screen
							game.setScreen(new GameoverScreen(game, true,
									collectData()));
						}
					}
				}

				// Check for powerup overlap
				for (int i = 0; i < activePowerups.size; i++) {
					if (activePowerups.get(i).getRect()
							.overlaps(boney.getRect())) {

						// Give Boney the effect
						boney.applyEffect(activePowerups.get(i).getType());

						// Remove the powerup
						activePowerups.get(i).reset();
						activePowerups.removeIndex(i);

						// Exit the loop
						i = 500;

						// Update the cash
						if ((boney.getCash() * 100) % 10 == 0)
							moneyLabel.setText("Cash: $" + boney.getCash()
									+ "0");
						else
							moneyLabel.setText("Cash: $" + boney.getCash());
					}
				}

				// Increase the powerup timer
				if (powerTimer < powerLimit)
					powerTimer++;
				else {
					// Reset the powerup
					powerTimer = 0;
					Powerup p = powerPool.obtain();
					p.init();
					activePowerups.add(p);
				}

				// Increase the score by seconds
				score += .1f * boney.getScoreMultiplier();

				// Update the label
				pntLabel.setText("Score: " + (int) score);

				// Check if Boney is alive
				if (!boney.getLife())
					gameover();

				// Check if game should get paused
				if (getKeyUp(Input.Keys.P)) {
					paused = true;
					Gdx.input.setInputProcessor(pStage);
				}
				if (pP == 0 && Gdx.input.isKeyPressed(Input.Keys.P))
					pP++;
				else if (pP == 1 && !Gdx.input.isKeyPressed(Input.Keys.P))
					pP++;
			} else {
				// Check if game should get unpaused
				pauseLabel.draw(batch, 1);
				if (getKeyUp(Input.Keys.P))
					paused = false;
				if (pP == 0 && Gdx.input.isKeyPressed(Input.Keys.P))
					pP++;
				else if (pP == 1 && !Gdx.input.isKeyPressed(Input.Keys.P))
					pP++;
			}
			batch.end();

			// Determind which stage to act and draw
			if (!paused)
				stage.act(delta);
			else
				pStage.act(delta);
			batch.begin();
			if (!paused)
				stage.draw();
			else
				pStage.draw();
			batch.end();

			// Draw shapes
			render.begin(ShapeType.Filled);
			for (int i = 0; i < boney.returnLives(); i++)
				render.rect((25 * i) + 15, 25, 15, 15);
			render.end();
		}
	}

	// Called on screen resize
	public void resize(int width, int height) {
		// Initialize the stages
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();
		if (!paused)
			Gdx.input.setInputProcessor(stage);
		if (pStage == null)
			pStage = new Stage(width, height, true);
		pStage.clear();
		if (paused)
			Gdx.input.setInputProcessor(pStage);

		// Setup the labels
		LabelStyle ls = new LabelStyle(fontW, Color.WHITE);
		if (!survival)
			label = new Label("Stage: " + level + "." + stageNum + "\nDogs: "
					+ (waveIndex + 1) + "/" + waves.size, ls);
		else
			label = new Label("Stage: SURVIVAL.\nDogs: " + (waveIndex + 1), ls);
		label.setX(0);
		label.setY(0);
		label.setWidth(width);
		label.setAlignment(Align.center);
		if (boney.getCash() > 0)
			moneyLabel = new Label("Cash: $" + boney.getCash(), ls);
		else
			moneyLabel = new Label("Cash: $0.00", ls);
		moneyLabel.setX(0);
		moneyLabel.setY(Gdx.graphics.getHeight() - 30);
		moneyLabel.setWidth(width);
		moneyLabel.setAlignment(Align.left);
		pntLabel = new Label("Score: " + score, ls);
		pntLabel.setX(0);
		pntLabel.setY(Gdx.graphics.getHeight() - 30);
		pntLabel.setWidth(width);
		pntLabel.setAlignment(Align.right);

		// Add labels to stage
		stage.addActor(label);
		stage.addActor(moneyLabel);
		stage.addActor(pntLabel);

		// Setup pause labels
		pauseLabel = new Label(pause.getMessage(), ls);
		pauseLabel.setY(200);
		pauseLabel.setWidth(width);
		pauseLabel.setAlignment(Align.center);

		// Setup intro label
		introLabel = new Label(intro.updateIntro(), ls);
		introLabel.setY(200);
		introLabel.setWidth(width);
		introLabel.setAlignment(Align.center);

		// Setup buttons
		TextButtonStyle tbStyle = new TextButtonStyle();
		tbStyle.up = skin.getDrawable("buttonnormal");
		tbStyle.down = skin.getDrawable("buttonpressed");
		tbStyle.font = b;

		// Setup start button
		startButton = new TextButton("Resume", tbStyle);
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
				paused = false;
				Gdx.input.setInputProcessor(stage);
			}
		});

		// Setup quit button
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

		// Add buttons to paused stage
		pStage.addActor(startButton);
		pStage.addActor(quitButton);

	}

	// Called on screen show
	public void show() {
		// Setup drawing objects
		render = new ShapeRenderer();
		batch = new SpriteBatch();

		// Grab textures
		buttAtlas = BoneyGame.getAssetManager().get(BUTTON_TEXTURES,
				TextureAtlas.class);

		// Setup button textures
		skin = new Skin();
		skin.addRegions(buttAtlas);

		// Grab fonts
		b = new BitmapFont(Gdx.files.internal("data/boneyfontblack.fnt"), false);
		fontW = new BitmapFont(Gdx.files.internal("data/chilly.fnt"), false);

		// Setup music
		song = Gdx.audio.newMusic(Gdx.files
				.internal("data/sound/music/Lawn.mp3"));
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

	// Disposes of disposable items
	public void dispose() {
		render.dispose();
		skin.dispose();
		pStage.dispose();
		stage.dispose();
		batch.dispose();
		fontW.dispose();
		b.dispose();
		song.dispose();
	}

	// Sets the screen to gameover
	private void gameover() {
		game.setScreen(new GameoverScreen(game, false, collectData()));
	}

	// Displays intro information
	private void doIntro() {
		// If we are on the intro level
		if (stageNum == 1 && level == 1) {
			// End of tutorial
			if (introState == 4)
				showIntroBox = false;

			// Draw the tutorial
			introLabel.draw(batch, 1);
		} else
			showIntroBox = false;

		// Increase the intro state
		if (getKeyUp(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
			introState++;
			if (introState < 4)
				introLabel.setText(intro.updateIntro());
		}
		if (enterP == 0 && Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
			enterP++;
		else if (enterP == 1 && !Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
			enterP++;

	}

	// Initializes the images
	private void initializeImages() {
		// Retrieves the assets
		background = BoneyGame.getAssetManager().get(BACKGROUND_TEXTURES,
				TextureAtlas.class);
		dog = BoneyGame.getAssetManager().get(DOGS_SPRITE_SHEET,
				TextureAtlas.class);
		boneySheet = BoneyGame.getAssetManager().get(BONEY_SPRITE_SHEET,
				TextureAtlas.class);
		levelSheet = BoneyGame.getAssetManager().get(LEVEL_TEXTURES,
				TextureAtlas.class);

		// Creates background sprites
		back = background.createSprite("background-main");

		// Creates dog sprites
		for (int i = 0; i < SmallDog.pics.length; i++)
			SmallDog.pics[i] = dog.createSprite("Dog-s", i);
		for (int i = 0; i < MediumDog.pics.length; i++)
			MediumDog.pics[i] = dog.createSprite("Dog-m", i);
		for (int i = 0; i < BigDog.pics.length; i++) {
			BigDog.pics[i] = dog.createSprite("Dog-b", i);
			BigDog.otherPics[i] = dog.createSprite("Dog-bb", i);
		}

		// Creates powerup sprites
		Powerup.coins[0] = levelSheet.createSprite("bCoin");
		Powerup.coins[1] = levelSheet.createSprite("sCoin");
		Powerup.coins[2] = levelSheet.createSprite("gCoin");
		Powerup.powers[0] = levelSheet.createSprite("p1");

		// Creates Boney sprites
		for (int i = 0; i < 8; i++) {
			Boney.curIdle[i] = boneySheet.createSprite("Boney-Idle", i);
			Boney.curCrouch[i] = boneySheet.createSprite("Boney-Crouch", i);
			Boney.curRunRight[i] = boneySheet.createSprite("Boney-Run", i);
			Boney.curRunLeft[i] = boneySheet.createSprite("Boney-Run", i);
			Boney.curRunLeft[i].flip(true, false);
			Boney.curJump[i] = boneySheet.createSprite("Boney-Jump", i);
		}

		// Creates and sets button sprites
		jumpUp = levelSheet.createSprite("Jump");
		jumpDown = levelSheet.createSprite("JumpP");
		crouchUp = levelSheet.createSprite("Crouch");
		crouchDown = levelSheet.createSprite("CrouchP");
		jumpUp.setPosition(jumpX, jumpY);
		crouchUp.setPosition(crouchX, crouchY);
		jumpDown.setPosition(jumpX, jumpY);
		crouchDown.setPosition(crouchX, crouchY);
	}

	// Collects Data for next screen
	public double[] collectData() {
		// 0 = score
		// 1 = cash
		// 2 = level
		// 3 = stage
		double[] data = new double[5];
		data[0] = score;
		data[1] = boney.getCash();
		data[2] = level;
		data[3] = stageNum;
		data[4] = waveIndex;
		return data;
	}

	// A buffer for buttons and clicks to progress screens
	public boolean getKeyUp(int key) {
		if (Gdx.input.isKeyPressed(key)) {
			if (enterP == 2) {
				enterP = 0;
				return true;
			}
			if (pP == 2) {
				pP = 0;
				return true;
			}
		}
		return false;
	}

	// Returns a dog to be added to the wave
	private GenericDog whichDog() {
		switch (waves.get(waveIndex)) {
		case (0):
			return smallDogPool.obtain();
		case (1):
			return medDogPool.obtain();
		case (2):
			return bigDogPool.obtain();
		}
		return null;
	}
}
//Hunter Heidenreich 2014