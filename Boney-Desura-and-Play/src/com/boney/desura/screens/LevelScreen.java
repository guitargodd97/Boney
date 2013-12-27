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
//Last Revised: 9/12/2013
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
	// Variables

	// Numbers
	float jumpRadius, jumpX, jumpY, crouchRadius, crouchX, crouchY, score;
	private Array<Integer> waves = new Array<Integer>();
	int waveIndex, level, stageNum, introBox, introState, boxWidth, boxHeight,
			boxX, boxY;
	int powerTimer, powerLimit;
	int buttonWidth, buttonHeight, buttonY;
	String introText[] = new String[5];
	// Objects
	SpriteBatch batch;
	TextureAtlas background, boneySheet, dog, levelSheet;
	BoneyGame game;
	Stage stage, pStage;
	ShapeRenderer render;
	Rectangle jumpRect, crouchRect;
	Boney boney;
	Sprite back, jumpUp, jumpDown, crouchUp, crouchDown;
	Sprite[] dogs = new Sprite[3];
	Boxes intro, pause;
	boolean showIntroBox;
	private final Pool<SmallDog> smallDogPool = new Pool<SmallDog>() {
		@Override
		protected SmallDog newObject() {
			return new SmallDog();
		}
	};
	private final Pool<MediumDog> medDogPool = new Pool<MediumDog>() {
		@Override
		protected MediumDog newObject() {
			return new MediumDog();
		}
	};
	private final Pool<BigDog> bigDogPool = new Pool<BigDog>() {
		@Override
		protected BigDog newObject() {
			return new BigDog();
		}
	};
	private final Pool<Powerup> powerPool = new Pool<Powerup>() {
		protected Powerup newObject() {
			return new Powerup();
		}
	};
	private Music song;
	private final Array<Powerup> activePowerups = new Array<Powerup>();
	private final Array<GenericDog> activeDogs = new Array<GenericDog>();
	private BitmapFont fontW;
	private Label label, moneyLabel, pntLabel, pauseLabel, introLabel;
	private int enterP, pP;
	private boolean paused, survival;
	TextButton startButton, quitButton;
	private Skin skin;
	private TextureAtlas buttAtlas;
	private BitmapFont b;

	// LevelScreen()
	//
	// Constructor for initialization
	//
	// Called on creation
	public LevelScreen(BoneyGame game, int l, int s) {
		this.game = game;
		render = new ShapeRenderer();
		jumpRadius = 32;
		score = 0;
		introState = 0;
		crouchRadius = jumpRadius;
		jumpX = 35;
		jumpY = 35;
		powerTimer = 0;
		powerLimit = 300;
		crouchX = Gdx.graphics.getWidth() - jumpX;
		crouchY = jumpY;
		jumpRect = new Rectangle(0, 410, jumpRadius * 2, jumpRadius * 2);
		crouchRect = new Rectangle(736, 410, crouchRadius * 2, crouchRadius * 2);
		level = l;
		stageNum = s;
		paused = false;
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
		initializeImages();
		boney = new Boney();
		boney.setState(2);
		GenericDog dog = whichDog();
		dog.init();
		activeDogs.add(dog);
		Powerup p = powerPool.obtain();
		p.init();
		activePowerups.add(p);
		pause = new Boxes(0);
		intro = new Boxes(1);
		boxWidth = (int) ((int) Gdx.graphics.getWidth() * 0.85);
		boxHeight = (int) ((int) Gdx.graphics.getHeight() * 0.85);
		boxX = (int) ((int) Gdx.graphics.getWidth() - boxWidth) / 2;
		boxY = (int) ((int) Gdx.graphics.getHeight() - boxHeight) / 2;
		buttonWidth = 200;
		buttonHeight = 75;
		buttonY = 15;
	}

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

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		back.draw(batch);
		boney.draw(batch);
		for (GenericDog d : activeDogs)
			d.draw(batch);

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
		if (showIntroBox)
			doIntro();
		else if (boney.getLife() && !paused) {
			boney.move(jumpRect, crouchRect);
			for (Powerup p : activePowerups)
				p.draw(batch);
			for (int i = 0; i < activeDogs.size; i++) {
				activeDogs.get(i).move();
				if (activeDogs.get(i).getRect().overlaps(boney.getRect()))
					boney.loseLive();
				if (activeDogs.get(i).getDone()) {
					score += activeDogs.get(i).getWorth()
							* boney.getScoreMultiplier();
					activeDogs.get(i).reset();
					activeDogs.removeIndex(i);
					if (waveIndex < waves.size - 1 || survival) {
						if (survival && waveIndex > 100) {
							waveIndex = 0;
							waves.clear();
							Random ran = new Random();
							for (int is = 0; is < 100; is++)
								waves.add(ran.nextInt(3));
						}
						waveIndex++;
						GenericDog g = whichDog();
						g.init();
						activeDogs.add(g);
						i = 500;
						if (!survival)
							label.setText("Stage: " + level + "." + stageNum
									+ "\nDogs: " + (waveIndex + 1) + "/"
									+ waves.size);
						else
							label.setText("Stage: SURVIVAL.\nDogs: "
									+ (waveIndex + 1));
					} else {
						if (stageNum < 2)
							boney.saveStat((level - 1) * 3 + stageNum);
						else
							boney.saveStat((level - 1) * 3 + stageNum - 1);
						game.setScreen(new GameoverScreen(game, true,
								collectData()));
					}
				}
			}
			for (int i = 0; i < activePowerups.size; i++) {
				if (activePowerups.get(i).getRect().overlaps(boney.getRect())) {
					boney.applyEffect(activePowerups.get(i).getType());
					activePowerups.get(i).reset();
					activePowerups.removeIndex(i);
					i = 500;
					if ((boney.getCash() * 100) % 10 == 0)
						moneyLabel.setText("Cash: $" + boney.getCash() + "0");
					else
						moneyLabel.setText("Cash: $" + boney.getCash());
				}
			}
			if (powerTimer < powerLimit)
				powerTimer++;
			else {
				powerTimer = 0;
				Powerup p = powerPool.obtain();
				p.init();
				activePowerups.add(p);
			}
			score += .1f * boney.getScoreMultiplier();
			pntLabel.setText("Score: " + (int) score);
			if (!boney.getLife())
				gameover();
			if (getKeyUp(Input.Keys.P)) {
				paused = true;
				Gdx.input.setInputProcessor(pStage);
			}
			if (pP == 0 && Gdx.input.isKeyPressed(Input.Keys.P))
				pP++;
			else if (pP == 1 && !Gdx.input.isKeyPressed(Input.Keys.P))
				pP++;
		} else {
			pauseLabel.draw(batch, 1);
			if (getKeyUp(Input.Keys.P))
				paused = false;
			if (pP == 0 && Gdx.input.isKeyPressed(Input.Keys.P))
				pP++;
			else if (pP == 1 && !Gdx.input.isKeyPressed(Input.Keys.P))
				pP++;
		}
		batch.end();
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
		render.begin(ShapeType.Filled);
		for (int i = 0; i < boney.returnLives(); i++)
			render.rect((25 * i) + 15, 25, 15, 15);
		render.end();
	}

	@Override
	public void resize(int width, int height) {
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
		stage.addActor(label);
		stage.addActor(moneyLabel);
		stage.addActor(pntLabel);
		pauseLabel = new Label(pause.getMessage(), ls);
		pauseLabel.setY(200);
		pauseLabel.setWidth(width);
		pauseLabel.setAlignment(Align.center);
		introLabel = new Label(intro.updateIntro(), ls);
		introLabel.setY(200);
		introLabel.setWidth(width);
		introLabel.setAlignment(Align.center);
		TextButtonStyle tbStyle = new TextButtonStyle();
		tbStyle.up = skin.getDrawable("buttonnormal");
		tbStyle.down = skin.getDrawable("buttonpressed");
		tbStyle.font = b;
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
		pStage.addActor(startButton);
		pStage.addActor(quitButton);

	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		skin = new Skin();
		buttAtlas = new TextureAtlas("data/images/button.pack");
		skin.addRegions(buttAtlas);
		b = new BitmapFont(Gdx.files.internal("data/boneyfontblack.fnt"), false);
		fontW = new BitmapFont(Gdx.files.internal("data/chilly.fnt"), false);
		song = Gdx.audio.newMusic(Gdx.files
				.internal("data/sound/music/Lawn.mp3"));
		song.setLooping(true);
		song.play();
	}

	@Override
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
		render.dispose();
		background.dispose();
		levelSheet.dispose();
		dog.dispose();
		boneySheet.dispose();
		buttAtlas.dispose();
		skin.dispose();
		pStage.dispose();
		stage.dispose();
		batch.dispose();
		fontW.dispose();
		b.dispose();
		song.dispose();
	}

	public void gameover() {
		game.setScreen(new GameoverScreen(game, false, collectData()));
	}

	public void doIntro() {
		if (stageNum == 1 && level == 1) {

			switch (introState) {
			case (0):

				break;
			case (1):

				break;
			case (2):

				break;
			case (3):

				break;
			case (4):
				showIntroBox = false;
				break;
			}
			introLabel.draw(batch, 1);
		} else
			showIntroBox = false;
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

	public void initializeImages() {
		background = new TextureAtlas("data/images/background.atlas");
		dog = new TextureAtlas("data/images/dog.atlas");
		boneySheet = new TextureAtlas("data/images/boney.atlas");
		levelSheet = new TextureAtlas("data/images/level.atlas");

		back = background.createSprite("background-main");

		for (int i = 0; i < SmallDog.pics.length; i++)
			SmallDog.pics[i] = dog.createSprite("Dog-s", i);

		for (int i = 0; i < MediumDog.pics.length; i++)
			MediumDog.pics[i] = dog.createSprite("Dog-m", i);

		for (int i = 0; i < BigDog.pics.length; i++) {
			BigDog.pics[i] = dog.createSprite("Dog-b", i);
			BigDog.otherPics[i] = dog.createSprite("Dog-bb", i);
		}

		Powerup.coins[0] = levelSheet.createSprite("bCoin");
		Powerup.coins[1] = levelSheet.createSprite("sCoin");
		Powerup.coins[2] = levelSheet.createSprite("gCoin");
		Powerup.powers[0] = levelSheet.createSprite("p1");
		for (int i = 0; i < 8; i++) {
			Boney.curIdle[i] = boneySheet.createSprite("Boney-Idle", i);
			Boney.curCrouch[i] = boneySheet.createSprite("Boney-Crouch", i);
			Boney.curRunRight[i] = boneySheet.createSprite("Boney-Run", i);
			Boney.curRunLeft[i] = boneySheet.createSprite("Boney-Run", i);
			Boney.curRunLeft[i].flip(true, false);
			Boney.curJump[i] = boneySheet.createSprite("Boney-Jump", i);
		}
		jumpUp = levelSheet.createSprite("Jump");
		jumpDown = levelSheet.createSprite("JumpP");
		crouchUp = levelSheet.createSprite("Crouch");
		crouchDown = levelSheet.createSprite("CrouchP");
		jumpUp.setPosition(jumpX, jumpY);
		crouchUp.setPosition(crouchX, crouchY);
		jumpDown.setPosition(jumpX, jumpY);
		crouchDown.setPosition(crouchX, crouchY);
	}

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
}
