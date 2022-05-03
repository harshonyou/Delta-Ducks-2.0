package com.mygdx.game.Entitys;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Components.Obstacles;
import com.mygdx.game.Components.Renderable;
import com.mygdx.game.Components.RigidBody;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Managers.*;
import com.mygdx.game.Physics.CollisionCallBack;
import com.mygdx.game.Physics.CollisionInfo;
import com.mygdx.game.Physics.PhysicsBodyType;

public class Monster extends Entity implements CollisionCallBack {
    private static int monsterCount = 0;

    private Animation <TextureRegion> wormIdle;

    private final int PIXEL_WORM_WIDTH = 90;
    private final int PIXEL_WORM_HEIGHT = 90;

    private float stateTime;

    private float timeoutCounter;
    private float TIMEOUT = 1f;

    private boolean contact;

    public Monster() {
        super(4);
        setName("Monster (" + monsterCount++ + ")");

        Transform t = new Transform();
        t.setPosition(800, 800);
        Renderable r = new Renderable(3, "white-up", RenderLayer.Transparent);
//        Renderable r = new Renderable(5, "Icons_14.png", RenderLayer.Transparent);
        RigidBody rb = new RigidBody(PhysicsBodyType.Static, r, t, false, true);

        rb.setCallback(this);
        rb.addTrigger(60, "corrode");

        Obstacles o = new Obstacles();

        r.setSize(100,100);
        r.setDisplacement(-38, -30);

        addComponents(t, r, rb, o);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0; i<9; i++) {
            frames.add(new TextureRegion(ResourceManager.getTexture("Idle.png"), i * PIXEL_WORM_WIDTH, 0, PIXEL_WORM_WIDTH, PIXEL_WORM_HEIGHT));
        }
        wormIdle = new Animation(0.1f, frames);
        frames.clear();

        timeoutCounter = 0;
        contact = false;
    }

    @Override
    public void update() {
        super.update();

        stateTime += EntityManager.getDeltaTime();
        timeoutCounter += EntityManager.getDeltaTime();

        getComponent(Renderable.class).setTexture(new Sprite(wormIdle.getKeyFrame(stateTime, true)));

        if(contact) {
            if (timeoutCounter > TIMEOUT) {
                if(GameManager.getPlayer().getHealth() - 1 <= 0) {
                    GameManager.getPlayer().setHealth(1);
                } else {
                    GameManager.getPlayer().setHealth(GameManager.getPlayer().getHealth() - 1);
                }
                timeoutCounter = 0;
            }
        }
    }

    @Override
    public void BeginContact(CollisionInfo info) {

    }

    @Override
    public void EndContact(CollisionInfo info) {

    }

    @Override
    public void EnterTrigger(CollisionInfo info) {
        if(info.a instanceof Player) {
            CaptionManager.setDisplay("Your health will be poisoned if you stay near the monster.");
            contact = true;
        }
    }

    @Override
    public void ExitTrigger(CollisionInfo info) {
        if(info.a instanceof Player) {
            CaptionManager.setDisplay("");
            contact = false;
        }
    }

    public void hurt() {
        if(GameManager.getPlayer().getHealth() - 1 <= 0) {
            GameManager.getPlayer().setHealth(1);
        } else {
            GameManager.getPlayer().setHealth(GameManager.getPlayer().getHealth() - 1);
        }
    }
}
