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
//Last Revised: 9/12/2013
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
	protected final int y;
	protected int type, x;
	protected Vector2 size;
	protected Rectangle rect;
	protected float opacity;
	protected boolean shown, active;
	public static Sprite[] coins = new Sprite[3];
	public static Sprite[] powers = new Sprite[1];
	protected Sprite thisSprite;
	protected static int cnt = 0;

	public Powerup() {
		y = 190;
		x = -40000;
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
		rect = new Rectangle(x, y, size.x, size.y);
		opacity = 0;
		shown = false;
		active = false;
	}

	public void init() {
		active = true;
		shown = false;
		opacity = 0;
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
		x = ran.nextInt((int) (Gdx.graphics.getWidth() - size.x));
		rect = new Rectangle(x, y, size.x, size.y);
		thisSprite.setPosition(x, y);
	}

	public void draw(SpriteBatch batch) {
		if (active) {
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

	public boolean getDone() {
		return (shown && opacity < 0.01f);
	}

	public Rectangle getRect() {
		if (opacity > .35f)
			return rect;
		return new Rectangle(-300, -300, 1, 1);
	}

	public int getType() {
		return type;
	}

	public void reset() {
		active = false;
		if (cnt < 7)
			cnt++;
		else
			cnt = 0;
	}
}
// Hunter Heidenreich 2013