package com.boney.desura.dogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.boney.desura.moveable.Dog;

public abstract class GenericDog implements Dog, Poolable {
	// Variables

	// Numbers
	protected double side;
	protected int state, timer, pntWorth, sub;
	protected float xVelocity, yVelocity;
	protected final float floor, gravity;

	// Strings and Booleans
	protected boolean warnOn, flipped;

	// Objects
	protected Rectangle rect;
	protected Vector2 shape, location;
	protected ShapeRenderer render;
	protected Sprite pictura;

	public GenericDog(int width, int height, Sprite pictura, int g) {
		state = 0;
		timer = 0;
		gravity = 9.81f * 35;
		floor = 125;
		if (g == 0) {
			sub = 30;
			shape = new Vector2(width - sub, height - sub);
		} else if (g == 1) {
			sub = 60;
			shape = new Vector2(width, height - sub);
		} else {
			sub = 60;
			shape = new Vector2(width - sub, height - sub);
		}
		render = new ShapeRenderer();
		warnOn = false;
		flipped = false;
		this.pictura = pictura;
	}

	public void init() {
		state = 1;
		// This Math.random() determines which side the dog generates on
		side = Math.random();
		if (side >= 0.5) {
			location = new Vector2(-200, 125);
			xVelocity = (float) (200 + (100 * Math.random()));
		} else {
			location = new Vector2(Gdx.graphics.getWidth() + 70, 125);
			xVelocity = (float) (-1 * (200 + (100 * Math.random())));
		}
		rect = new Rectangle(location.x + sub / 2, location.y, shape.x, shape.y);
		timer = 0;
		yVelocity = 350f;
		flipped = false;
		render = new ShapeRenderer();
	}

	public void move() {
		// If the dog is alive
		if (state == 1) {
			// If the buffer timer is done
			if (timer > 180) {
				location.x += xVelocity * Gdx.graphics.getDeltaTime();
				rect.setPosition(location.x + sub / 2, location.y);
			} else
				timer++;
		}
	}

	// draw()
	//
	// Handles the illustrating of the dog
	//
	// Called by the levels render method
	public void draw(SpriteBatch batch) {
		drawWarning();
		if (!pictura.isFlipX() && xVelocity > 0)
			pictura.flip(true, false);
		if (pictura.isFlipX() && xVelocity < 0)
			pictura.flip(true, false);
		batch.draw(pictura, location.x, location.y);
	}

	// drawWarning()
	//
	// Handles whether a warning should be drawn
	//
	// Called by the draw method
	public void drawWarning() {
		render.begin(ShapeType.Filled);
		if (timer % 30 == 0 && warnOn)
			warnOn = false;
		else if (timer % 30 == 0 && !warnOn)
			warnOn = true;
		if (timer < 180 && warnOn) {
			if (side >= 0.5)
				render.rect(10, (Gdx.graphics.getHeight() + 20) / 2, 20, 20);
			else
				render.rect(Gdx.graphics.getWidth() - 25,
						(Gdx.graphics.getHeight() + 20) / 2, 20, 20);
		}
		render.end();
	}

	// getRect()
	//
	// Returns the rectangle to other classes
	//
	// Called to check collision in level screens
	public Rectangle getRect() {
		return rect;
	}

	// reset()
	//
	// Turns the dog off
	//
	// Called by level class to turn off the dog
	public void reset() {
		state = 2;
		render.dispose();
	}

	// getDone()
	//
	// Returns whether the dog has traveled across the screen
	//
	// Called by the level class
	public boolean getDone() {
		if (side >= 0.5) {
			if (location.x > Gdx.graphics.getWidth() + 10)
				return true;
		} else {
			if (location.x < 0 - shape.x - 10)
				return true;
		}
		return false;
	}
}
