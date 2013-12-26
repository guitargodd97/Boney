package com.boney.desura.dogs;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BigDog extends GenericDog {
	public static Sprite[] pics = new Sprite[6], otherPics = new Sprite[6];
	private int animationBuffer;
	private int currentFrame;
	public boolean switchSprite;
	private boolean mid;

	public BigDog() {
		super(128, 128, pics[0], 2);
		pntWorth = 100;
		switchSprite = false;
		Random ran = new Random();
		mid = ran.nextBoolean();
	}

	public void move() {
		super.move();
		animationBuffer++;
		if (animationBuffer > 7) {
			animationBuffer = 0;
			currentFrame++;
			if (currentFrame > 5)
				currentFrame = 0;
		}
		if (!switchSprite) {
			pictura = pics[currentFrame];
			switchSprite = normalGetDone();
		} else {
			pictura = otherPics[currentFrame];
		}
	}

	// Stop and bark
	public void specialty() {
	}

	public int getWorth() {
		return pntWorth;
	}

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
			Gdx.app.log("Swithc", "Otha side");
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