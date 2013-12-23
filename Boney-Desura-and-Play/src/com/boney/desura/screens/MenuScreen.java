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

public class MenuScreen implements Screen {
	// Variables

	// Numbers
	int buttonWidth, buttonHeight, buttonY;

	// Strings and booleans
	String backgroundAtlas, buttonAtlas, fontW, fontB, music;

	// Images and the Like
	Skin skin;
	Sprite background;
	TextureAtlas menuAtlas, buttAtlas;
	BitmapFont b, f;

	// Sounds
	Music m;

	// Other Objects
	BoneyGame game;
	Stage stage;
	SpriteBatch batch;
	TextButton startButton, quitButton, creditButton;
	Label label;

	public MenuScreen(BoneyGame game) {
		this.game = game;
		buttonWidth = 200;
		buttonHeight = 75;
		buttonY = 15;
		buttonAtlas = "data/images/button.pack";
		fontB = "data/boneyfontblack.fnt";
		fontW = "data/boneyfont.fnt";
		backgroundAtlas = "data/images/background.atlas";
		music = "data/sound/music/Boney Theme.mp3";
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		background.setColor(new Color(Color.LIGHT_GRAY));
		batch.begin();
		background.draw(batch);
		batch.end();
		stage.act(delta);
		batch.begin();
		stage.draw();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		TextButtonStyle tbStyle = new TextButtonStyle();
		tbStyle.up = skin.getDrawable("buttonnormal");
		tbStyle.down = skin.getDrawable("buttonpressed");
		tbStyle.font = b;
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
				// game.setScreen(new LevelScreen(game, 1, 1));
				game.setScreen(new ModeScreen(game));
			}
		});

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

		LabelStyle ls = new LabelStyle(f, Color.WHITE);
		label = new Label("Boney the Skeleton", ls);
		label.setX(0);
		label.setY(400);
		label.setWidth(Gdx.graphics.getWidth());
		label.setAlignment(Align.center);
		stage.addActor(startButton);
		stage.addActor(creditButton);
		stage.addActor(quitButton);
		stage.addActor(label);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		skin = new Skin();
		menuAtlas = new TextureAtlas(backgroundAtlas);
		buttAtlas = new TextureAtlas(buttonAtlas);
		b = new BitmapFont(Gdx.files.internal(fontB));
		f = new BitmapFont(Gdx.files.internal(fontW));
		m = Gdx.audio.newMusic(Gdx.files.internal(music));
		skin.addRegions(buttAtlas);
		background = menuAtlas.createSprite("background-main");
		background.setPosition(0, 0);
		m.setLooping(true);
		m.play();
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
		batch.dispose();
		skin.dispose();
		stage.dispose();
		f.dispose();
		b.dispose();
		menuAtlas.dispose();
		buttAtlas.dispose();
		m.dispose();
	}

}
