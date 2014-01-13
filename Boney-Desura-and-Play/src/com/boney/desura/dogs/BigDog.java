package com.boney.desura.dogs;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
//---------------------------------------------------------------------------------------------
//
//LargeDog.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This is the the large instance of the dog interface
//
//---------------------------------------------------------------------------------------------

public class BigDog extends GenericDog {
	public static Sprite[] pics = new Sprite[6];
	public static Sprite[] otherPics = new Sprite[6];
	private boolean switchSprite;
	private boolean mid;
	private int animationBuffer;
	private int currentFrame;

	// Initializes the BigDog
	public BigDog() {
		super(128, 128, pics[0], 2);
		pntWorth = 100;
		switchSprite = false;

		// Determines whether the dog will stop in the middle or the side
		Random ran = new Random();
		mid = ran.nextBoolean();
	}

	// Moves the dog
	public void move() {
		super.move();

		// Animates the dog
		animationBuffer++;
		if (animationBuffer > 7) {
			animationBuffer = 0;
			currentFrame++;
			if (currentFrame > 5)
				currentFrame = 0;
		}

		// Hyper or regular
		if (!switchSprite) {
			pictura = pics[currentFrame];
			switchSprite = normalGetDone();
		} else {
			pictura = otherPics[currentFrame];
		}
	}

	// Specialty movement
	public void specialty() {
	}

	// Get points
	public int getWorth() {
		return pntWorth;
	}

	// Gets whether the dog is done
	public boolean getDone() {
		if (side <= 0.5) {
			if (location.x > Gdx.graphics.getWidth() + 10 && switchSprite)
				return true;
		} else {
			if (location.x < 0 - shape.x - 10 && switchSprite)
				return true;
		}
		return false;
	}

	// Gets whether the dog should turn around
	public boolean normalGetDone() {
		if (mid) {
			if (side >= 0.5) {
				if (location.x > Gdx.graphics.getWidth() + 10) {
					xVelocity *= -2;
					animationBuffer = 3;
					return true;
				}
			} else {
				if (location.x < 0 - shape.x - 10) {
					xVelocity *= -2;
					animationBuffer = 3;
					return true;
				}
			}
		} else {
			if (side >= 0.5) {
				if (location.x > (Gdx.graphics.getWidth() / 2) + 10) {
					xVelocity *= -2;
					animationBuffer = 3;
					return true;
				}
			} else {
				if (location.x < (Gdx.graphics.getWidth() / 2) - shape.x - 10) {
					xVelocity *= -2;
					animationBuffer = 3;
					return true;
				}
			}
		}
		return false;
	}
}
// Hunter Heidenreich 2014