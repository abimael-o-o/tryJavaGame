package com.mygdx.game;
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
        img = new Texture("idle_sheet.png");
        position = p;
        body = new BodyHelper().createBody(position.x, position.y, 42, 64, false, world);
        
        Animator anim = new Animator();

        //Cut the animation frames.
        animation = anim.AnimatorInit(img);
        stateTime = 0f;
    }
    public void Update(float deltaTime){
        float horizontalForce  = 0;
        float verticalForce = 0;
        
        //Input listener for movement
        if(Gdx.input.isKeyPressed(Keys.W)){
            verticalForce += speed;
        }
        if(Gdx.input.isKeyPressed(Keys.S)) {
            verticalForce -= speed;
        }
        if(Gdx.input.isKeyPressed(Keys.D)) {
            horizontalForce += speed;
        }
        if(Gdx.input.isKeyPressed(Keys.A)) {
            horizontalForce -= speed;
        }
        
        
        Vector2 velocity = new Vector2(horizontalForce, verticalForce).nor();
        body.setLinearVelocity(velocity.x * speed, velocity.y * speed);
        position = new Vector2(body.getPosition().x * 32f, body.getPosition().y * 32f);
        
        boolean isFacingRight = true;
        boolean isUp = false;
        if(velocity.x != 0){
            isFacingRight = isFacingRight ? velocity.x < 0 : velocity.x > 0;
            animationHandler(animation, isFacingRight, isUp);
            if(velocity.y != 0){
                isUp = isUp ? velocity.y > 0 : velocity.y < 0;
            }
        }else{
            //Set idle animation without flipping character.
            animationHandler(animation, false, false);
        }
        
        stateTime += deltaTime * .2f; //Animation speed.
    }
    public void Draw(SpriteBatch batch){
        this.batch = batch;
        Update(Gdx.graphics.getDeltaTime());
    }
    
    public void animationHandler(Animation<TextureRegion> anim, boolean isFlipx, boolean isFlipy){
        TextureRegion currentFrame = anim.getKeyFrame(stateTime, true);
        sprite = new Sprite(currentFrame);
        sprite.setScale(3);
        sprite.flip(isFlipx, isFlipy);
        sprite.setPosition(position.x - 16, position.y);
        sprite.draw(batch);
    }
    @Override
    public void dispose(){
        img.dispose();
        batch.dispose();
    }
 
}
