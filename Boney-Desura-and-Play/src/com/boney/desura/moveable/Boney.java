package com.boney.desura.moveable;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
//---------------------------------------------------------------------------------------------
//
//Boney.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This is the actual character Boney.  It controls its movement and handles all controls.  It
//also features an Outfit object that handles the drawing of Boney.  
//
//---------------------------------------------------------------------------------------------

public class Boney {
	public static Sprite[] curIdle = new Sprite[8];
	public static Sprite[] curCrouch = new Sprite[8];
	public static Sprite[] curRunRight = new Sprite[8];
	public static Sprite[] curRunLeft = new Sprite[8];
	public static Sprite[] curJump = new Sprite[10];
	private final float gravity = 9.81f * 35;
	private final float normalFloor = 125;
	private ApplicationType appType;
	private boolean crouch;
	private boolean jump;
	private boolean poweruped;
	private FileHandle statLocation;
	private FileHandle moneyLocation;
	private float energyY;
	private float energyX;
	private float floor;
	private float sizeMod;
	private int state;
	private int curHealth;
	private int powerupcnt;
	private int animationBuffer;
	private int currentFrame;
	private int cash;
	private int speed;
	private int cashMultiplier;
	private int powerupLength;
	private int powerupFrequency;
	private int health;
	private int buffer;
	private int scoreMultiplier;
	private int size;
	private int discount;
	private int running;
	private Outfit outfit;
	private Rectangle rect;
	private Vector2 location;
	private Vector2 velocity;
	private Vector2 shape;
	private Vector2 powerupInfo;

	// Initializes the Boney
	public Boney() {
		// Called to initialize numbers from last save
		loadStat();

		// Numbers
		state = 0;
		running = 0;
		energyY = 0;
		energyX = 0;
		floor = 125;
		curHealth = health;
		powerupcnt = 300 * powerupLength;

		// Booleans
		poweruped = false;
		crouch = false;
		jump = false;

		// Locations and Outfits
		shape = new Vector2(64, 128);
		velocity = new Vector2(0, 0);
		location = new Vector2(Gdx.graphics.getWidth() / 2 - shape.x / 2, 100);
		rect = new Rectangle(location.x, location.y, shape.x, shape.y);
		outfit = new Outfit(location);
		powerupInfo = new Vector2(0, 0);

		// Called for controls
		appType = Gdx.app.getType();
		sizeMod = 1 - (0.05f * size);
		animationBuffer = 0;
		currentFrame = 0;
	}

	// Moves Boney
	public void move(Rectangle jumpRect, Rectangle crouchRect) {
		// If Boney is alive
		if (state == 2) {
			controls(jumpRect, crouchRect);
			location.add(velocity);
			rect.setPosition(location);
			outfit.setLocation(location);

			// Side to side movement
			if (location.x < 0 + speed) {
				location.x = 0 + speed + 1;
				energyX = 0;
			}
			if (location.x + shape.x > Gdx.graphics.getWidth() - speed) {
				location.x = Gdx.graphics.getWidth() - shape.x - speed - 1;
				energyX = 0;
			}

			// Gravity and Jumping
			if (location.y < floor) {
				location.y = floor;
				energyY = 0;
			}
			if (location.y >= floor) {
				location.y += energyY * Gdx.graphics.getDeltaTime();
				if (location.y > floor)
					energyY -= gravity * Gdx.graphics.getDeltaTime();
			}

			// Side to side energy reduction
			if (Math.abs(energyX) > 0) {
				location.x += energyX * Gdx.graphics.getDeltaTime();
				if (energyX > 0)
					energyX -= gravity * Gdx.graphics.getDeltaTime();
				else
					energyX += gravity * Gdx.graphics.getDeltaTime();
			} else if (Math.abs(energyX) < 1)
				energyX = 0;

			// Crouch Boney
			if (crouch) {
				shape.y = (128 * (sizeMod - .3f));
				floor = normalFloor - ((128 * (1 - sizeMod + .1f)));
				// outfit.crouch(true);
				rect.setSize(shape.x, shape.y);
			} else {
				shape.y = (128 * sizeMod);
				floor = normalFloor - (128 * (1 - sizeMod));
				// outfit.crouch(false);
				rect.setSize(shape.x, shape.y);
			}

			// If Boney is dead
			if (curHealth == 0)
				state = 0;

			// Powerups
			if (poweruped) {
				powerupcnt -= 1;
				if (powerupcnt <= 0) {
					poweruped = false;
					int i = (int) powerupInfo.x;
					switch (i) {
					case (3):
						speed = (int) powerupInfo.y;
						break;
					}
				}
			}
		}
	}

	// Draw Boney
	public void draw(SpriteBatch batch) {
		// If Boney is not invisible
		if (state > 0) {
			// Crouch animaton
			if (crouch) {
				outfit.draw(batch, curCrouch[currentFrame]);
				animationBuffer++;
				if (animationBuffer > 3) {
					animationBuffer = 0;
					if (currentFrame < 7)
						currentFrame++;
				}
				// Jump animation
			} else if (energyY > 10 && jump) {
				outfit.draw(batch, curJump[currentFrame]);
				animationBuffer++;
				if (animationBuffer > 3) {
					animationBuffer = 0;
					if (currentFrame < 7)
						currentFrame++;
					else
						jump = false;
				}
				// Running right animation
			} else if (energyX > 7 || running == 2) {
				outfit.draw(batch, curRunRight[currentFrame]);
				animationBuffer++;
				if (animationBuffer > 9) {
					animationBuffer = 0;
					currentFrame++;
					if (currentFrame > 7)
						currentFrame = 0;
				}
				// Running left animation
			} else if (energyX < -7 || running == 1) {
				outfit.draw(batch, curRunLeft[currentFrame]);
				animationBuffer++;
				if (animationBuffer > 9) {
					animationBuffer = 0;
					currentFrame++;
					if (currentFrame > 7)
						currentFrame = 0;
				}
				// Idle animation
			} else {
				outfit.draw(batch, curIdle[currentFrame]);
				animationBuffer++;
				if (animationBuffer > 10) {
					animationBuffer = 0;
					currentFrame++;
					if (currentFrame > 7)
						currentFrame = 0;
				}
			}
		}
	}

	// Apply effect to Boney
	public void applyEffect(int effect) {
		// Takes an effect and picks a method to call
		switch (effect) {
		case (0):
			cash += 1 * cashMultiplier;
			break;
		case (1):
			cash += 10 * cashMultiplier;
			break;
		case (2):
			cash += 25 * cashMultiplier;
			break;
		case (3):
			if (!poweruped) {
				powerupInfo = new Vector2(3, speed);
				speed *= 2;
				poweruped = true;
				powerupcnt = 300 * powerupLength;
			}
			break;
		}

	}

	// Turns off effect
	private void resetPower() {
		switch ((int) powerupInfo.x) {
		case (3):
			speed = (int) powerupInfo.y;
			break;
		}
	}

	// Saves Boney's Stats
	public void saveStat(int i) {
		resetPower();
		// Writes a .bin file to hold bytes (research if .bin is necessary)
		// Always should be false, because it needs to overwrite
		statLocation.writeBytes(new byte[] { (byte) i, (byte) speed,
				(byte) cashMultiplier, (byte) powerupLength,
				(byte) powerupFrequency, (byte) health, (byte) buffer,
				(byte) scoreMultiplier, (byte) size, (byte) discount }, false);
		// Writes .txt file to hold cash value
		// Always should be false
		moneyLocation.writeString("" + cash, false);
	}

	// Loads Boney's stats
	private void loadStat() {
		// File location for stats
		statLocation = Gdx.files.local("data/stats.bin");
		// if location exits
		if (statLocation.exists()) {
			// Opens it and then modifies it
			byte[] b = statLocation.readBytes();
			speed = b[1];
			cashMultiplier = b[2];
			powerupLength = b[3];
			powerupFrequency = b[4];
			health = b[5];
			buffer = b[6];
			scoreMultiplier = b[7];
			size = b[8];
			discount = b[9];
		} else {
			// If it doesn't exists, make a new one
			statLocation.writeBytes(
					new byte[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, false);
			byte[] b = statLocation.readBytes();
			speed = b[1];
			cashMultiplier = b[2];
			powerupLength = b[3];
			powerupFrequency = b[4];
			health = b[5];
			buffer = b[6];
			scoreMultiplier = b[7];
			size = b[8];
			discount = b[9];
		}
		// File location for money
		moneyLocation = Gdx.files.local("data/money.txt");
		// If file exists
		if (moneyLocation.exists()) {
			// Opens it and modifies it
			String s = moneyLocation.readString();
			cash = Integer.parseInt(s);
			// cash = 0;
		} else {
			// Write a new one and save it
			moneyLocation.writeString("0", false);
			String s = moneyLocation.readString();
			cash = Integer.parseInt(s);
		}
	}

	// Sets Boney's state
	public void setState(int state) {
		this.state = state;
	}

	// Returns Rectangle for collision
	public Rectangle getRect() {
		return rect;
	}

	// Sets the energyX to move Boney
	public void setEnergyX(Float energyX) {
		this.energyX = energyX * speed;
	}

	// Sets the energyY to move Boney up
	public void jump() {
		// If Boney is not currently in the air
		if (location.y == floor) {
			energyY = 300.0f;
			jump = true;
		}
	}

	// Sets Boney to the crouch position
	public void setCrouch(boolean crouch) {
		if (this.crouch && !crouch)
			currentFrame = 0;
		else if (!this.crouch && crouch)
			currentFrame = 0;
		this.crouch = crouch;
	}

	// The control method that updates all the other variables
	public void controls(Rectangle jumpRect, Rectangle crouchRect) {
		// Checks if the controls should be based on desktop build
		if (appType == ApplicationType.Desktop) {
			if (!crouch || location.y > floor) {
				// Side to side
				if (Gdx.input.isKeyPressed(Keys.LEFT)) {
					setEnergyX(-250.0f);
				} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
					setEnergyX(250.0f);
				}

			}
			// Up and down
			if (Gdx.input.isKeyPressed(Keys.UP))
				jump();
			if (Gdx.input.isKeyPressed(Keys.DOWN))
				setCrouch(true);
			else
				setCrouch(false);
		}
		// Checks if the controls should be based on android build
		if (appType == ApplicationType.Android) {
			// If the screen is touched
			if (Gdx.input.isTouched()) {
				// If it's on the jump button
				if (jumpRect.contains(Gdx.input.getX(), Gdx.input.getY()))
					jump();
				// If it's on the crouch button
				if (crouchRect.contains(Gdx.input.getX(), Gdx.input.getY()))
					setCrouch(true);
			} else
				setCrouch(false);
			// Rotation makes Boney go right
			if (Gdx.input.getAccelerometerY() >= 2.0) {
				location.x += 5;
				running = 2;
			}
			// Rotation makes Boney go left
			else if (Gdx.input.getAccelerometerY() <= -2.0) {
				location.x -= 5;
				running = 1;
			} else {
				running = 0;
			}
		}
	}

	// Subtracts 1 from the current health
	public void loseLive() {
		curHealth--;
	}

	// Returns whether Boney should still be alive or he should have gameover
	public boolean getLife() {
		return curHealth > 0;
	}

	// Returns the current cash
	public double getCash() {
		return (double) cash / 100;
	}

	// Gets the powerupfrequency
	public int getPowerupFrequency() {
		return powerupFrequency;
	}

	// Gets the dog buffer
	public int getBuffer() {
		return buffer;
	}

	// Gets the score multiplier
	public int getScoreMultiplier() {
		return scoreMultiplier;
	}

	// Gets the number of lives
	public int returnLives() {
		return curHealth;
	}
}
// Hunter Heidenreich 2014