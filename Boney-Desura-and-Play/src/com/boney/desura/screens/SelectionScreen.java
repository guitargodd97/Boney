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

public class SelectionScreen implements Screen {
	BoneyGame game;
	ShapeRenderer render;
	Vector2[] stageLocations = new Vector2[3];
	Vector2 levelLocation, stageSize, levelSize, miniLocation;
	Sprite background, mini, plaque;
	Sprite[] levelBox = new Sprite[3];
	SpriteBatch batch;
	TextureAtlas atlas, selection;
	BitmapFont numbers;
	Stage stage;
	Label label[] = new Label[3];
	Rectangle stages[] = new Rectangle[3];
	int level;

	public SelectionScreen(BoneyGame game) {
		this.game = game;
		render = new ShapeRenderer();
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
		loadData();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		background.draw(batch);
		plaque.draw(batch);
		batch.end();
		render.begin(ShapeType.Line);
		render.rect(levelLocation.x + 130, levelLocation.y + 55, levelSize.x,
				levelSize.y);
		render.end();

		render.begin(ShapeType.Filled);
		render.setColor(Color.BLACK);
		render.rect(levelLocation.x + 130, levelLocation.y + 55, levelSize.x,
				levelSize.y);
		render.end();

		batch.begin();
		for (Sprite s : levelBox)
			s.draw(batch);
		mini.draw(batch);
		batch.end();
		stage.act(delta);
		batch.begin();
		stage.draw();
		batch.end();

		checkScreen();
	}

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

	@Override
	public void resize(int width, int height) {
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();
		Gdx.input.setInputProcessor(stage);
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
		for (int i = 0; i < levelBox.length; i++)
			levelBox[i].setPosition(stages[i].x, stages[i].y);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		atlas = new TextureAtlas("data/images/background.atlas");
		selection = new TextureAtlas("data/images/selection.atlas");
		mini = atlas.createSprite("background-main");
		mini.setScale(0.3f);
		mini.setPosition(0, 25);

		background = atlas.createSprite("background-main");
		background.setPosition(0, 0);
		for (int i = 0; i < levelBox.length; i++)
			levelBox[i] = selection.createSprite("numberBox");
		plaque = selection.createSprite("graveyardCopy");
		plaque.setPosition(levelLocation.x, levelLocation.y);
		numbers = new BitmapFont(Gdx.files.internal("data/chilly.fnt"), false);
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
		batch.dispose();
		atlas.dispose();
		selection.dispose();
		stage.dispose();
		numbers.dispose();
	}

	public void loadData() {
		FileHandle statLocation = Gdx.files.local("data/stats.bin");
		// if location exits
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
}
