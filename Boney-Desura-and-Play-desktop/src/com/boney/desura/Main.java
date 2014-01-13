package com.boney.desura;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Boney - " + BoneyGame.VERSION;
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 480;
		cfg.addIcon("data/images/iconWin.png", Files.FileType.Internal);
		new LwjglApplication(new BoneyGame(), cfg);
	}
}
