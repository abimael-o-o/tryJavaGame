package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
    public Sprite sprite;
    public Vector2 position;
    public float speed = 300;

    public Player(Texture img){
        sprite = new Sprite(img);
        sprite.setScale(3);
        position = new Vector2(0, 0);
    }
    public void Update(float deltaTime){
        //Input listener for movement
        if(Gdx.input.isKeyPressed(Keys.W)) position.y += deltaTime * speed;
        if(Gdx.input.isKeyPressed(Keys.S)) position.y -= deltaTime * speed;
        if(Gdx.input.isKeyPressed(Keys.D)) position.x += deltaTime * speed;
        if(Gdx.input.isKeyPressed(Keys.A)) position.x -= deltaTime * speed;
     
    }
    public void Draw(SpriteBatch batch){
        Update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
    
}
