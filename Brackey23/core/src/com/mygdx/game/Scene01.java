package com.mygdx.game;

import static com.mygdx.helper.Constants.PPM;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.helper.TileMapHelper;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Scene01 extends ScreenAdapter{

    SpriteBatch batch;
	//Texture img;
	Player player;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    
    private World world;
    private Box2DDebugRenderer box2dDebugRenderer; 

    private OrthographicCamera camera;

    private RayHandler rayHandler;
    private Light pointLight;
    
    //Constructs the game scene.
    public Scene01(OrthographicCamera camera){
        this.camera = camera;
        this.world = new World(new Vector2(0,0), false); //World y = gravity
        //this.box2dDebugRenderer = new Box2DDebugRenderer(); //Set collision box debug
        this.batch = new SpriteBatch();

        //Tiled map init.
        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setuMap();
        
		player = new Player(world, tileMapHelper.getPlayerSpawn());
        
        
        //Game Camera.
        this.camera.position.set(player.position.x, player.position.y,0);
        this.camera.zoom -= .6f;
        
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.01f);
        pointLight = new PointLight(rayHandler, 100, Color.BLACK, 100, 0, 0);
        //pointLight.attachToBody(player.getPlayerBody());
        
        Box2D.init(); //Initialize collision box2d
    }

    public void update(){
        world.step(1/120f, 6, 2);
        cameraUpdate();
        rayHandler.update();

        batch.setProjectionMatrix(camera.combined);
        rayHandler.setCombinedMatrix(camera);
        orthogonalTiledMapRenderer.setView(camera);
        //System.out.println(Gdx.graphics.getFramesPerSecond());
    }

    public void cameraUpdate(){
        camera.position.set(player.position.x, player.position.y, 0);
        pointLight.setPosition(player.position);
        camera.update();
    }

    @Override
    public void render(float delta){
        this.update();
        ScreenUtils.clear(0, 0, 0, 1, true); //Color screen needed for buffer
        orthogonalTiledMapRenderer.render(); //Render tile map
        
        //Render objects
        batch.begin();
        
        player.Draw(batch);
        
        batch.end();
        
        rayHandler.render();
        //Collision box2D Debug wireframe
        //box2dDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight  = height;
        camera.update();
    }

    @Override
	public void dispose () {
		batch.dispose();
        rayHandler.dispose();
        pointLight.dispose();
        world.dispose();
        orthogonalTiledMapRenderer.dispose();
        box2dDebugRenderer.dispose();
	}
    public World getWorld(){
        return world;
    }
}
