package com.boney.desura.other;

//---------------------------------------------------------------------------------------------
//
//Boxes.java
//Last Revised: 1/12/2014
//Author: Hunter Heidenreich
//Product of: Day Ja Voo Games
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This object controls simple messages such as pause, intros, etc.
//
//---------------------------------------------------------------------------------------------
public class Boxes {
	private int introStage;
	private String message;
	private String introMessages[] = new String[4];

	//Initializes the Boxes
	public Boxes(int type) {
		switch (type) {
		// Pause
		case (0):
			message = "Game Paused";
			break;
		// Intro
		case (1):
			message = "Click to Continue";
			break;
		// Delete Data
		case (2):
			message = "Delete Data?";
			break;
		// Not in this version
		case (3):
			message = "Not Available in This Version";
			break;
		// Not enough money
		case (4):
			message = "Not Enough Money";
			break;
		}
		introStage = 0;
		introMessages[0] = "Welcome to Boney.\n\nJump, duck, and dodge your way away from dogs\nwho want to steal your bones!";
		introMessages[1] = "Terriers are full of energy.\nThey like to hope and jump to steal your bones!";
		introMessages[2] = "Golden Retrievers are predictable.\nThey go back and forth to get your bones.";
		introMessages[3] = "Dobermans are fast and tricky.\nThey'll turn around when you least expect!";
	}

	//Updates the intro
	public String updateIntro() {
		String s = "";
		s = introMessages[introStage] + "\n\n" + message;
		introStage++;
		return s;
	}

	//Returns a message
	public String getMessage() {
		return message;
	}
}
//Hunter Heidenreich 2014