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

    private Renderable healthBar; // Added for assessment 2
    private Renderable armorBar; // Added for assessment 2



    private Animation<TextureRegion>[] rolls; // Added for assessment 2
    private Animation<TextureRegion>[] idleRolls; // Added for assessment 2

    int roll; // Added for assessment 2
    float rollVerticalTimer; // Added for assessment 2
    float rollHorizontalTimer; // Added for assessment 2
    float stateTime; // Added for assessment 2

    private Animation <TextureRegion> shipMove; // Added for assessment 2

    private final int PIXEL_SHIP_WIDTH = 1280; // Added for assessment 2
    private final int PIXEL_SHIP_HEIGHT = 1280; // Added for assessment 2

    private final float SHIP_FRAME_DURATION = 0.5f; // Added for assessment 2
    private String previousState = ""; // Added for assessment 2
    private float stateTimer; // Added for assessment 2

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

        /*
        Added for assessment 2
        Initialize the health and armor bar
         */
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

    /**
     * Added for assessment 2
     * update function of libgdx to update the sprite or characteristics
     *
     * Check if the health is less than 0; if yes the player is dead and game is over
     * For all other cases it measures the armor and health and makes health and armor bar
     * and set the color corresponding to its percentage.
     *
     */
    @Override
    public void update() {
//        getComponent(Renderable.class).setTexture(new Sprite(ResourceManager.getTexture("darealthang.png")));

        super.update();
        if(getHealth() > 100) {
            setHealth(100);
        }
        if(getArmor() > 100) {
            setArmor(100);
        }
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

    /**
     * Added for assessment 2
     * Hides the armor bar
     */
    public void hideArmor() {
        armorBar.hide();
    }

    /**
     * Added for assessment 2
     * Shows the armor bar
     */
    public void showArmor() {
        armorBar.show();
    }

    /**
     * Added for assessment 2
     * Updates the player direction
     * @param dir to update
     */
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

    /**
     * Added for assessment 2
     * @param s speed to set for the player
     */
    public void setPlayerSpeed(float s) {
        getComponent(PlayerController.class).setPlayerSpeed(s);
    }

    /**
     * Added for assessment 2
     * @return the player speed
     */
    public float getPlayerSpeed() {
        return getComponent(PlayerController.class).getPlayerSpeed();
    }

    /**
     * Added for assessment 2
     * Initialize the player sprite for every movement
     */
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

    /**
     * Added for assessment 2
     * @param deltaTime     delta time
     * @param currentState  state counter
     * @return the frame for that certain delta time
     */
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
