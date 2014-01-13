package com.boney.desura.dogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
//---------------------------------------------------------------------------------------------
//
//SmallDog.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This is the the small instance of the dog interface
//
//---------------------------------------------------------------------------------------------

public class SmallDog extends GenericDog {
	public static Sprite[] pics = new Sprite[10];
	private int animationBuffer;
	private int currentFrame;
	private int jumped;

	// Initializes the SmallDog
	public SmallDog() {
		super(64, 64, pics[0], 0);
		pntWorth = 25;
		jumped = 0;
	}

	// Moves the dog
	public void move() {
		super.move();

		// Calls the specialty
		if (timer > 180)
			specialty();

		// Animates the dog
		animationBuffer++;
		if (animationBuffer > 7) {
			animationBuffer = 0;
			currentFrame++;
			if (currentFrame > 9)
				currentFrame = 0;
		}
		pictura = pics[currentFrame];
	}

	// The special movement
	public void specialty() {
		if (location.x > 25 && location.x + shape.x < 750) {
			if (jumped == 0) {
				yVelocity = 350.0f;
				jumped = 1;
			}
			// If below the floor, push up
			if (location.y < floor && jumped == 2) {
				location.y = floor;
				yVelocity = 0;
			}
			// If above the floor, apply velocity
			if (location.y >= floor && jumped == 1) {
				location.y += yVelocity * Gdx.graphics.getDeltaTime();
				if (location.y > floor)
					yVelocity -= gravity * Gdx.graphics.getDeltaTime();
			} else
				jumped = 2;
		}
	}

	// Gets the point worth
	public int getWorth() {
		return pntWorth;
	}
}
// Hunter Heidenreich 2014