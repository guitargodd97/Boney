package com.boney.desura.dogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SmallDog extends GenericDog {
	public static Sprite[] pics = new Sprite[10];
	private int animationBuffer;
	private int currentFrame;

	public SmallDog() {
		super(64, 64, pics[0], 0);
		pntWorth = 25;
	}

	public void move() {
		super.move();
		if (timer > 180)
			specialty();
		animationBuffer++;
		if (animationBuffer > 7) {
			animationBuffer = 0;
			currentFrame++;
			if (currentFrame > 9)
				currentFrame = 0;
		}
		pictura = pics[currentFrame];
	}

	@Override
	public void specialty() {
		int i = (int) (Math.random() * 10);
		if (location.y == floor && i % 3 == 0)
			yVelocity = 350.0f;
		// If below the floor, push up
		if (location.y < floor) {
			location.y = floor;
			yVelocity = 0;
		}
		// If above the floor, apply velocity
		if (location.y >= floor) {
			location.y += yVelocity * Gdx.graphics.getDeltaTime();
			if (location.y > floor)
				yVelocity -= gravity * Gdx.graphics.getDeltaTime();
		}
	}

	@Override
	public int getWorth() {
		return pntWorth;
	}

}