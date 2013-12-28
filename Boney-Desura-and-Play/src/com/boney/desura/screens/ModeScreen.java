package com.boney.desura.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.boney.desura.BoneyGame;
import com.boney.desura.other.Boxes;

public class ModeScreen implements Screen {
	BoneyGame game;
	Stage stage;
	Label[] label = new Label[4];
	Label version;
	Boxes vT;
	Rectangle rect[] = new Rectangle[4];
	SpriteBatch batch;
	private BitmapFont fontW;
	TextureAtlas atlas;
	Sprite classic, survive, upgrade, custom;
	int NA;
	boolean vN;

	public ModeScreen(BoneyGame game) {
		this.game = game;
		for (int i = 0; i < rect.length; i++) {
			rect[i] = new Rectangle();
			rect[i].setSize(Gdx.graphics.getWidth() / 2 - 20,
					Gdx.graphics.getHeight() / 2 - 20);
		}
		Gdx.app.log("Boney", "" + rect[1].width + " " + rect[1].height);
		rect[0].setPosition(0, Gdx.graphics.getHeight() / 2 + 10);
		rect[1].setPosition(Gdx.graphics.getWidth() / 2 + 10,
				Gdx.graphics.getHeight() / 2 + 10);
		rect[2].setPosition(0, 0);
		rect[3].setPosition(Gdx.graphics.getWidth() / 2 + 10, 0);
		vT = new Boxes(3);
		NA = 0;
		vN = false;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		stage.draw();
		batch.end();
		batch.begin();
		upgrade.draw(batch);
		custom.draw(batch);
		survive.draw(batch);
		classic.draw(batch);
		if (vN)
			version.draw(batch, 1);
		batch.end();
		if (Gdx.input.justTouched()) {
			if (rect[0].contains(Gdx.input.getX(), Gdx.input.getY()))
				game.setScreen(new ShopScreen(game));
			else if (rect[1].contains(Gdx.input.getX(), Gdx.input.getY())) {
				if (BoneyGame.FREE) {
					NA = 0;
					if (vN)
						vN = false;
					else
						vN = true;
				}
			} else if (rect[2].contains(Gdx.input.getX(), Gdx.input.getY()))
				game.setScreen(new SelectionScreen(game));
			else if (rect[3].contains(Gdx.input.getX(), Gdx.input.getY()))
				game.setScreen(new LevelScreen(game, 1, 10));
		}

	}

	@Override
	public void resize(int width, int height) {
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		LabelStyle ls = new LabelStyle(fontW, Color.WHITE);
		label[0] = new Label("Upgrade Boney", ls);
		label[0].setX(Gdx.graphics.getWidth() / 4 - Gdx.graphics.getWidth()
				/ 10);
		label[0].setY(20);
		label[1] = new Label("Customize Boney", ls);
		label[1].setX(Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 6);
		label[1].setY(20);
		label[2] = new Label("Classic Mode", ls);
		label[2].setX(Gdx.graphics.getWidth() / 4 - Gdx.graphics.getWidth()
				/ 10);
		label[2].setY(Gdx.graphics.getHeight() / 2 + 30);
		label[3] = new Label("Survival Mode", ls);
		label[3].setX(Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 6);
		label[3].setY(Gdx.graphics.getHeight() / 2 + 30);
		for (Label l : label) {
			l.setWidth(width);
			stage.addActor(l);
		}
		version = new Label(vT.getMessage(), ls);
		version.setAlignment(Align.center);
		version.setWidth(width);
		version.setY(200);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		fontW = new BitmapFont(Gdx.files.internal("data/chilly.fnt"), false);
		atlas = new TextureAtlas("data/images/mode.atlas");

		classic = atlas.createSprite("modeClassic");
		classic.setPosition(rect[0].x, rect[0].y - 10);
		classic.setSize(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		survive = atlas.createSprite("modeSurvival");
		survive.setPosition(rect[1].x - 10, rect[1].y - 10);
		survive.setSize(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		upgrade = atlas.createSprite("modeUpgrade");
		upgrade.setPosition(rect[2].x, rect[2].y);
		upgrade.setSize(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		custom = atlas.createSprite("modeCustom");
		custom.setPosition(rect[3].x - 10, rect[3].y);
		custom.setSize(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
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
		fontW.dispose();
		stage.dispose();
		batch.dispose();
		atlas.dispose();
	}

}
