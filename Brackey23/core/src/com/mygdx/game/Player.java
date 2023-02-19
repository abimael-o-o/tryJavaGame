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
    public float speed = .1f;
    Body body;
    Animation<TextureRegion> animation;
    Animation<TextureRegion> runAnimation;
    Animation<TextureRegion> upAnimation;
    Animation<TextureRegion> downAnimation;
    float stateTime;
    SpriteBatch batch;
    
    public Player(World world, Vector2 p){
        this.batch = new SpriteBatch();
        position = p;
        body = new BodyHelper().createBody(position.x, position.y, 12, 12, false, world);

        //Cut the animation frames.
        SetAnimations();
        stateTime = 0f;
    }
    public void SetAnimations(){
        Texture idleTexture = new Texture("idle-sheet.png");
        Texture runTexture = new Texture("run-sheet.png");
        Texture upTexture = new Texture("up-sheet.png");
        Texture downTexture = new Texture("down-sheet.png");

        Animator anim = new Animator();
        animation = anim.AnimatorInit(idleTexture);
        runAnimation = anim.AnimatorInit(runTexture);
        upAnimation = anim.AnimatorInit(upTexture);
        downAnimation = anim.AnimatorInit(downTexture);
    }

    public void Update(float deltaTime){
        float horizontalForce  = 0;
        float verticalForce = 0;
        
        //Input listener for movement
        if(Gdx.input.isKeyPressed(Keys.W)){
            verticalForce += speed;
            animationHandler(upAnimation, false, false);
        }
        else if(Gdx.input.isKeyPressed(Keys.S)) {
            verticalForce -= speed;
            animationHandler(downAnimation, false, false);
        }
        else if(Gdx.input.isKeyPressed(Keys.D)) {
            horizontalForce += speed;
            animationHandler(runAnimation, false, false);
        }
        else if(Gdx.input.isKeyPressed(Keys.A)) {
            horizontalForce -= speed;
            animationHandler(runAnimation, true, false);
        }else{
            animationHandler(animation, false, false);
        }
        
        
        Vector2 velocity = new Vector2(horizontalForce, verticalForce).nor();
        body.setLinearVelocity(velocity.x * speed, velocity.y * speed);
        position = new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM); // Needed for consitant move between sprite and body

        stateTime += deltaTime * .2f; //Animation speed.
    }
    public void Draw(SpriteBatch batch){
        this.batch = batch;
        Update(Gdx.graphics.getDeltaTime());
    }
    
    public void animationHandler(Animation<TextureRegion> anim, boolean isFlipx, boolean isFlipy){
        TextureRegion currentFrame = anim.getKeyFrame(stateTime, true);
        sprite = new Sprite(currentFrame);
        sprite.flip(isFlipx, isFlipy);
        sprite.setPosition(position.x-8, position.y-6);
        sprite.draw(batch);
    }
    @Override
    public void dispose(){
        batch.dispose();
    }
 
}
