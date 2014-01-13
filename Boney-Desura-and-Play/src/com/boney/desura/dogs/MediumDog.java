package com.boney.desura.dogs;

import com.badlogic.gdx.graphics.g2d.Sprite;
//---------------------------------------------------------------------------------------------
//
//MediumDog.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This is the the medium instance of the dog interface
//
//---------------------------------------------------------------------------------------------

public class MediumDog extends GenericDog {
	public static Sprite[] pics = new Sprite[9];
	private int animationBuffer;
	private int currentFrame;

	// Initializes the MediumDog
	public MediumDog() {
		super(64, 128, pics[0], 1);
		pntWorth = 50;
	}

	// Inits the dog
	public void init() {
		super.init();
	}

	// The specialty movement (leave blank)
	public void specialty() {

	}

	// Gets the point worth of the dog
	@Override
	public int getWorth() {
		return pntWorth;
	}

	// Moves the dog
	public void move() {
		super.move();

		// Animates the dog
		animationBuffer++;
		if (animationBuffer > 7) {
			animationBuffer = 0;
			currentFrame++;
			if (currentFrame > 8)
				currentFrame = 0;
		}
		pictura = pics[currentFrame];
	}
}
// Hunter Heidenreich 2014