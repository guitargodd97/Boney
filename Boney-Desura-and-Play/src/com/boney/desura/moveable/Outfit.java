package com.boney.desura.moveable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
//---------------------------------------------------------------------------------------------
//
//Outfit.java
//Last Revised: 1/12/2014
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
	private boolean crouch;
	private Vector2 location;

	// Initializes the Outfit
	public Outfit(Vector2 location) {
		this.location = location;
		crouch = false;
	}

	// Draws Boney with the specified frame
	public void draw(SpriteBatch batch, Sprite bone) {
		// Update location
		bone.setPosition(location.x, location.y);

		// Whether or not Boney is crouching
		if (!crouch)
			bone.setScale(1, 1);
		else
			bone.setScale(1, 0.5f);

		// Draw
		bone.draw(batch);
	}

	// Sets the location
	public void setLocation(Vector2 location) {
		this.location = location;
	}

	// Crouches the image
	public void crouch(boolean crouch) {
		this.crouch = crouch;
	}
}
// Hunter Heidenreich 2014