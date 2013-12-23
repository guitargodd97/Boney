package com.boney.desura.moveable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

//---------------------------------------------------------------------------------------------
//
//Dog.java
//Last Revised: 9/12/2013
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

	// init()
	//
	// Initializes variables through the Pool class
	//
	// Called from the LevelScreen through the Pool object
	public void init();

	// move()
	//
	// Moves the dog
	//
	// Called from the LevelScreen
	public void move();

	// draw()
	//
	// Draws the dog based on the texture it receives
	//
	// Called from the LevelScreen
	public void draw(SpriteBatch batch);

	void drawWarning();

	// getRect()
	//
	// Returns the rectangle of the dog to compare for collision
	//
	// Called from the LevelScreen for collision
	public Rectangle getRect();

	// reset()
	//
	// Turns off the dog when it is not needed
	//
	// Called from LevelScreen through the Pool object
	public void reset();

	// getDone()
	//
	// Returns whether the dog has crossed the screen
	//
	// Called from the LevelScreen
	public boolean getDone();

	public void specialty();
	
	public int getWorth();
}
// Hunter Heidenreich 2013