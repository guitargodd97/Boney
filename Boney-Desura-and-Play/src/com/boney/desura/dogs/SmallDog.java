package com.boney.desura.dogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SmallDog extends GenericDog {
	public static Sprite[] pics = new Sprite[10];
	private int animationBuffer;
	private int currentFrame;
	private int jumped;

	public SmallDog() {
		super(64, 64, pics[0], 0);
		pntWorth = 25;
		jumped = 0;
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

	@Override
	public int getWorth() {
		return pntWorth;
	}

}