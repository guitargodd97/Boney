package com.boney.desura.moveable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

//---------------------------------------------------------------------------------------------
//
//Dog.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Interface:
//
//Interface is the basis that all dogs implement.  All dogs inherit these key methods.
//
//---------------------------------------------------------------------------------------------

public interface Dog {

	// The init for the pool class
	public void init();

	// Moves the dog
	public void move();

	// Draws the dog
	public void draw(SpriteBatch batch);

	// Draws the warning sign
	void drawWarning();

	// Returns the rectangle for collision
	public Rectangle getRect();

	// Resets the dog
	public void reset();

	// Returns whether the dog is done
	public boolean getDone();

	// Unleashes the dogs specialty
	public void specialty();

	// Gets the point worth of the dog
	public int getWorth();
}
// Hunter Heidenreich 2014