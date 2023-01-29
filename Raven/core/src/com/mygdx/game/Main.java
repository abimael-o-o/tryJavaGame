package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Main extends Game {
	SpriteBatch batch;
	Texture img;
	Player player;
	Sprite grassSprite;
	GameScreen gameScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("RavenPlayer-Sheet.png");
		Texture grass = new Texture("Grass.png");
		grassSprite = new Sprite(grass);
		player = new Player(img);
		
		//Game Viewport
		gameScreen = new GameScreen(player.position.x, player.position.y);
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1, true); //Color screen needed for buffer
		
		//Render Objects
		batch.begin();
		gameScreen.Draw(batch, player.position.x, player.position.y);
		grassSprite.draw(batch);
		player.Draw(batch);
		
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
