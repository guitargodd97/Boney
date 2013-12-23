package com.boney.desura.dogs;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class MediumDog extends GenericDog {
	public static Sprite[] pics = new Sprite[9];
	private int animationBuffer;
	private int currentFrame;
	protected float holdXvel;
	protected int cnt;

	public MediumDog() {
		super(64, 128, pics[0], 1);
		pntWorth = 50;
		cnt = 0;
		holdXvel = xVelocity;
	}

	public void init() {
		super.init();
		holdXvel = xVelocity;
	}

	public void specialty() {

	}

	@Override
	public int getWorth() {
		return pntWorth;
	}

	public void move() {
		super.move();
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