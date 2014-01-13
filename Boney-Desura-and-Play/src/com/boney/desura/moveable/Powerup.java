package com.boney.desura.moveable;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

//---------------------------------------------------------------------------------------------
//
//Powerup.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This class handles the powerups that apply to Boney, but not their actual behaviors.
//
//---------------------------------------------------------------------------------------------
public class Powerup implements Poolable {
	public static Sprite[] coins = new Sprite[3];
	public static Sprite[] powers = new Sprite[1];
	private final int y;
	private static int cnt = 0;
	private boolean shown;
	private boolean active;
	private float opacity;
	private int type;
	private int x;
	private Rectangle rect;
	private Sprite thisSprite;
	private Vector2 size;

	// Initializes the Powerup
	public Powerup() {
		// Gives new coordinates
		y = 190;
		x = -40000;

		// Sets the type of powerup
		Random ran = new Random();
		// 0 = $0.01
		// 1 = $0.10
		// 2 = $0.25
		// 3 = speed up
		if (ran.nextInt(7) < cnt)
			type = ran.nextInt(3);
		else
			type = ran.nextInt(1) + 3;
		if (type < 3)
			size = new Vector2(20, 20);
		else
			size = new Vector2(35, 35);

		// Sets the sprite
		switch (type) {
		case (0):
			thisSprite = coins[0];
			break;
		case (1):
			thisSprite = coins[1];
			break;
		case (2):
			thisSprite = coins[2];
			break;
		case (3):
			thisSprite = powers[0];
			break;
		}

		// Sets the size and how it's being drawn
		rect = new Rectangle(x, y, size.x, size.y);
		opacity = 0;
		shown = false;
		active = false;
	}

	// Inits the Pool
	public void init() {
		// Sets the drawing
		active = true;
		shown = false;
		opacity = 0;

		// Sets the type
		Random ran = new Random();
		if (ran.nextInt(10) + 5 > cnt)
			type = ran.nextInt(3);
		else {
			type = ran.nextInt(1) + 3;
			cnt = 0;
		}
		if (type < 3)
			size = new Vector2(20, 20);
		else
			size = new Vector2(35, 35);

		// Sets the sprite
		switch (type) {
		case (0):
			thisSprite = coins[0];
			break;
		case (1):
			thisSprite = coins[1];
			break;
		case (2):
			thisSprite = coins[2];
			break;
		case (3):
			thisSprite = powers[0];
			break;
		}

		// Sets the place and size
		x = ran.nextInt((int) (Gdx.graphics.getWidth() - size.x));
		rect = new Rectangle(x, y, size.x, size.y);
		thisSprite.setPosition(x, y);
	}

	// Draws the powerup
	public void draw(SpriteBatch batch) {
		// Draws if active
		if (active) {
			// Updates the opacity
			if (opacity < 0.99f && !shown)
				opacity += 0.01f;
			else if (shown && opacity > 0.01f)
				opacity -= 0.01f;
			else
				shown = true;
			thisSprite.setColor(1, 1, 1, opacity);
			thisSprite.draw(batch);
		}
	}

	// Return if the powerup should be turned off
	public boolean getDone() {
		return (shown && opacity < 0.01f);
	}

	// Returns the Rectangle for collision
	public Rectangle getRect() {
		if (opacity > .35f)
			return rect;
		return new Rectangle(-300, -300, 1, 1);
	}

	// Returns the type of powerup for effect application
	public int getType() {
		return type;
	}

	// Turns off the powerup
	public void reset() {
		active = false;
		if (cnt < 7)
			cnt++;
		else
			cnt = 0;
	}
}
// Hunter Heidenreich 2014