package com.mygdx.game;
import static com.mygdx.helper.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameScreen extends ScreenAdapter{
    
    private World world;
    private Box2DDebugRenderer box2dDebugRenderer;
    ExtendViewport extendViewport;
    
    //Constructs the game scene.
    public GameScreen(float playerPosX, float playerPosY){
        world = new World(new Vector2(0,0), false);
        box2dDebugRenderer = new Box2DDebugRenderer();

        //Game Viewport
		extendViewport = new ExtendViewport(800, 800);
        extendViewport.getCamera().position.set(playerPosX, playerPosY, 0);
    }
    public void Update(float deltaTime){
        //Do Stuff here.
    }
    public void Draw(SpriteBatch batch, float playerPosX, float playerPosY){
        Update(Gdx.graphics.getDeltaTime());
        //Follow Player
		extendViewport.getCamera().position.set(playerPosX, playerPosY, 0);
        //Sets scale of viewport
		extendViewport.apply();
		//This is what sets things to scale when resizing
		batch.setProjectionMatrix(extendViewport.getCamera().combined); 

        //Collision box2D
        box2dDebugRenderer.render(world, extendViewport.getCamera().combined.scl(PPM));
    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height);
    }

}
