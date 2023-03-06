package com.mygdx.game;
import static com.mygdx.helper.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.Animator;
import com.mygdx.helper.BodyHelper;

public class Player extends ScreenAdapter{
    public Sprite sprite;
    public Vector2 position;
    public float speed = .25f;
    Body body;
    Texture img;
    Animation<TextureRegion> animation;
    float stateTime;
    SpriteBatch batch;
    
    public Player(World world, Vector2 p){
        this.batch = new SpriteBatch();
        position = p;
        body = new BodyHelper().createBody(position.x, position.y, 32, 32, false, world);
        
        Animator anim = new Animator();
        
        img = new Texture("sprite.png");
        //Cut the animation frames.
        animation = anim.AnimatorInit(img);
        stateTime = 0f;
    }

    public void Update(float deltaTime){
        float horizontalForce  = 0;
        float verticalForce = 0;
        
        //Input listener for movement
        if(Gdx.input.isKeyPressed(Keys.D)) {
            horizontalForce += speed;
        }
        if(Gdx.input.isKeyPressed(Keys.A)) {
            horizontalForce -= speed;
        }
        
        Vector2 velocity = new Vector2(horizontalForce, verticalForce).nor();
        body.setLinearVelocity(velocity.x * speed, velocity.y * speed);
        position = new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM);
        
        //Jump listener.
        if(Gdx.input.isKeyPressed(Keys.SPACE) && isGrounded()){
            body.setLinearVelocity( horizontalForce, 1f);
        }

        boolean isFacingRight = true;
        if(velocity.x != 0){
            isFacingRight = isFacingRight ? velocity.x < 0 : velocity.x > 0;
            animationHandler(animation, isFacingRight);
        }else{
            //Set idle animation without flipping character.
            animationHandler(animation, false);
        }
        
        stateTime += deltaTime * .2f; //Animation speed.
    }
    public void Draw(SpriteBatch batch){
        this.batch = batch;
        Update(Gdx.graphics.getDeltaTime());
    }
    
    public void animationHandler(Animation<TextureRegion> anim, boolean isFlipx){
        TextureRegion currentFrame = anim.getKeyFrame(stateTime, true);
        sprite = new Sprite(currentFrame);
        sprite.flip(isFlipx, false);
        sprite.setScale(2);
        sprite.setPosition(position.x - 16 / 2, position.y - 16 / 2);
        sprite.draw(batch);
    }

    public boolean isGrounded(){
        return true;
    }

    @Override
    public void dispose(){
        img.dispose();
        batch.dispose();
    }
 
}
