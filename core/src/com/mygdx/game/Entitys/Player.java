package com.mygdx.game.Entitys;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Components.*;
import com.mygdx.game.Managers.EntityManager;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.RenderLayer;
import com.mygdx.game.Managers.ResourceManager;

/**
 * Player's ship entity.
 */
public class Player extends Ship {

    private Renderable healthBar;
    private Renderable armorBar;



    private Animation<TextureRegion>[] rolls;
    private Animation<TextureRegion>[] idleRolls;

    int roll;
    float rollVerticalTimer;
    float rollHorizontalTimer;
    float stateTime;

    private Animation <TextureRegion> shipMove;

    private final int PIXEL_SHIP_WIDTH = 1280;
    private final int PIXEL_SHIP_HEIGHT = 1280;

    private final float SHIP_FRAME_DURATION = 0.5f;
    private String previousState = "";
    private float stateTimer;

    /**
     * Adds ship with PlayerController component and sets its speed.
     *
     * @param speed of movement across map
     */
    private Player(float speed) {
        super();

        PlayerController pc = new PlayerController(this, speed);
        addComponent(pc);

        setName("Player");

        healthBar = new Renderable(ResourceManager.getId("blank.png"), RenderLayer.Transparent);
        armorBar = new Renderable(ResourceManager.getId("blank.png"), RenderLayer.Transparent);

        addComponents(healthBar);
        addComponents(armorBar);

        healthBar.show();
        armorBar.show();

        healthBar.setDisplacement(0, -15);
        armorBar.setDisplacement(0, -5);
        armorBar.setColor(Color.DARK_GRAY);

        initPlayerShip();

//        getComponent(RigidBody.class).setVelocity();
        getComponent(Transform.class).setScale(2.3f, 2.3f);
//        getComponent(Renderable.class).setSize(50, 50);
        getComponent(RigidBody.class).setRadius(30);
//        getComponent(Renderable.class).setSize(50, 50);

    }

    @Override
    public void update() {
//        getComponent(Renderable.class).setTexture(new Sprite(ResourceManager.getTexture("darealthang.png")));
        super.update();
        if (getHealth() <= 0) {
            healthBar.hide();
        } else {
            if (getHealth() > 80f)
                healthBar.setColor(Color.valueOf("26ff05"));
            else if (getHealth() > 70f)
                healthBar.setColor(Color.valueOf("8ee600"));
            else if (getHealth() > 60f)
                healthBar.setColor(Color.valueOf("dbc500"));
            else if (getHealth() > 50f)
                healthBar.setColor(Color.valueOf("ffa024"));
            else if (getHealth() > 40f)
                healthBar.setColor(Color.valueOf("ff401a"));
            else if (getHealth() > 30f)
                healthBar.setColor(Color.valueOf("fa0011"));
            else
                healthBar.setColor(Color.valueOf("cc0007"));
            healthBar.setSize(2, 2.3f*(getHealth()/10));
            healthBar.setDisplacement(-25, -10);

            armorBar.setSize(2, 2.3f*(getArmor()/10));
            armorBar.setDisplacement(-30, -10);
        }
    }

    public void updatePlayerDirection(Vector2 dir) {
        getComponent(Renderable.class).setTexture(new Sprite(getFrame(EntityManager.getDeltaTime(), getShipDirection(dir))));
    }

    /**
     * Adds ship with PlayerController component, loading its speed from GameManager settings.
     */
    public Player() {
        this(GameManager.getSettings().get("starting").getFloat("playerSpeed"));
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
    }

    public int getAmmo() {
        return getComponent(Pirate.class).getAmmo();
    }

    public void setPlayerSpeed(float s) {
        getComponent(PlayerController.class).setPlayerSpeed(s);
    }

    public float getPlayerSpeed() {
        return getComponent(PlayerController.class).getPlayerSpeed();
    }

    public void initPlayerShip () {
        roll = 4;
        rolls = new Animation[8];
        idleRolls = new Animation[8];
        rollVerticalTimer = 2f;
        rollHorizontalTimer = 2f;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=2; i<4; i++) {
            frames.add(new TextureRegion(ResourceManager.getTexture("custom/ALL DUCK BOAT FINAL.png"), i * PIXEL_SHIP_WIDTH, 0, PIXEL_SHIP_WIDTH, PIXEL_SHIP_HEIGHT));
        }

        frames.add(new TextureRegion(ResourceManager.getTexture("custom/ALL DUCK BOAT FINAL.png"), 0, 0, PIXEL_SHIP_WIDTH, PIXEL_SHIP_HEIGHT));

        shipMove = new Animation(0.1f, frames);

        frames.clear();
        for(int i=0; i<8; i++) {
            frames.add(new TextureRegion(ResourceManager.getTexture("custom/ALL DUCK BOAT FINAL.png"), i * PIXEL_SHIP_WIDTH, 0, PIXEL_SHIP_WIDTH, PIXEL_SHIP_HEIGHT));
        }

        idleRolls[0] = new Animation(SHIP_FRAME_DURATION, frames.get(0)); // Down
        idleRolls[1] = new Animation(SHIP_FRAME_DURATION, frames.get(1));
        idleRolls[2] = new Animation(SHIP_FRAME_DURATION, frames.get(2)); // Left
        idleRolls[3] = new Animation(SHIP_FRAME_DURATION, frames.get(3));
        idleRolls[4] = new Animation(SHIP_FRAME_DURATION, frames.get(4)); // Up
        idleRolls[5] = new Animation(SHIP_FRAME_DURATION, frames.get(5));
        idleRolls[6] = new Animation(SHIP_FRAME_DURATION, frames.get(6)); // Right
        idleRolls[7] = new Animation(SHIP_FRAME_DURATION, frames.get(7));

        frames.clear();
    }

    public TextureRegion getFrame(float deltaTime, String currentState) {
//            shipDirections.put(new Vector2(0, 1), "-up");
//            shipDirections.put(new Vector2(0, -1), "-down");
//            shipDirections.put(new Vector2(1, 0), "-right");
//            shipDirections.put(new Vector2(-1, 0), "-left");
//            shipDirections.put(new Vector2(1, 1), "-ur");
//            shipDirections.put(new Vector2(-1, 1), "-ul");
//            shipDirections.put(new Vector2(1, -1), "-dr");
//            shipDirections.put(new Vector2(-1, -1), "-dl");
        TextureRegion region;
        switch (currentState) {
            case "-up":
                roll = 4;
                region = idleRolls[roll].getKeyFrame(stateTime, true);
                break;
            case "-down":
                roll = 0;
                region = idleRolls[roll].getKeyFrame(stateTime, true);
                break;
            case "-right":
                roll = 6;
                region = idleRolls[roll].getKeyFrame(stateTime, true);
                break;
            case "-left":
                roll = 2;
                region = idleRolls[roll].getKeyFrame(stateTime, true);
                break;
            case "-ur":
                roll = 5;
                region = idleRolls[roll].getKeyFrame(stateTime, true);
                break;
            case "-ul":
                roll = 3;
                region = idleRolls[roll].getKeyFrame(stateTime, true);
                break;
            case "-dr":
                roll = 7;
                region = idleRolls[roll].getKeyFrame(stateTime, true);
                break;
            case "-dl":
                roll = 1;
                region = idleRolls[roll].getKeyFrame(stateTime, true);
                break;
            default:
                currentState = previousState;
                region = idleRolls[roll].getKeyFrame(stateTime, true);
                break;
        }
        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;
        return region;
    }
    
}
