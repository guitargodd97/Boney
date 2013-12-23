package com.boney.desura.moveable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
//---------------------------------------------------------------------------------------------
//
//Outfit.java
//Last Revised: 9/12/2013
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This class handles the actual drawing of Boney.  SpriteBatches are passed to this object
//in order for it to draw.
//
//---------------------------------------------------------------------------------------------

public class Outfit {

	// Variables

	// Strings and Booleans
	boolean crouch;

	// Objects
	Vector2 location;

	// Outfit()
	//
	// Constructor that intializes things
	//
	// Called from Boney Constructor
	public Outfit(Vector2 location) {

		this.location = location;

		crouch = false;
	}

	// draw()
	//
	// Draws the sprite of Boney
	//
	// Called from the Boney draw() method
	public void draw(SpriteBatch batch, Sprite bone) {

		bone.setPosition(location.x, location.y);

		// Whether or not Boney is crouching
		if (!crouch)
			bone.setScale(1, 1);
		else
			bone.setScale(1, 0.5f);
		bone.draw(batch);
	}

	// setLocation()
	//
	// Sets the location that the Boney sprite should be drawn
	//
	// Called from the Boney move() method
	public void setLocation(Vector2 location) {
		this.location = location;
	}

	// crouch()
	//
	// Method that takes whether Boney should be drawn crouched or not
	//
	// Called in the Boney control() method when the buttons/controls are being
	// initiated
	public void crouch(boolean crouch) {
		this.crouch = crouch;
	}

}
