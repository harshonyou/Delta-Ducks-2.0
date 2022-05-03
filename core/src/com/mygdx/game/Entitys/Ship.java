package com.mygdx.game.Entitys;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Components.Renderable;
import com.mygdx.game.Components.RigidBody;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Faction;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.RenderLayer;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.Physics.CollisionCallBack;
import com.mygdx.game.Physics.CollisionInfo;
import com.mygdx.game.Physics.PhysicsBodyType;
import com.mygdx.utils.Utilities;

import java.util.Objects;

/**
 * Base class for game ships, Player & NPC.
 */
public class Ship extends Entity implements CollisionCallBack {
    private static int shipCount = 0;
    public static ObjectMap<Vector2, String> shipDirections;

    private final Vector2 currentDir;

    private float damageDelt; // Added for assessment 2

    private float bulletSpeed; // Added for assessment 2
    private float plunderBonus; // Added for assessment 2
    private float xpBonus; // Added for assessment 2





    /**
     * Creates a ship entity, containing Transform, Renderable, RigidBody, and Pirate components.
     */
    public Ship() {
        super(4);
        currentDir = new Vector2();
        setName("Ship (" + shipCount++ + ")"); // each ship has a unique name

        if (shipDirections == null) {
            shipDirections = new ObjectMap<>();
            shipDirections.put(new Vector2(0, 1), "-up");
            shipDirections.put(new Vector2(0, -1), "-down");
            shipDirections.put(new Vector2(1, 0), "-right");
            shipDirections.put(new Vector2(-1, 0), "-left");
            shipDirections.put(new Vector2(1, 1), "-ur");
            shipDirections.put(new Vector2(-1, 1), "-ul");
            shipDirections.put(new Vector2(1, -1), "-dr");
            shipDirections.put(new Vector2(-1, -1), "-dl");
        }

        Transform t = new Transform();
        t.setPosition(800, 800);
        Renderable r = new Renderable(3, "white-up", RenderLayer.Transparent);
        RigidBody rb = new RigidBody(PhysicsBodyType.Dynamic, r, t);
        rb.setCallback(this);

        Pirate p = new Pirate();

        // rb.setCallback(this);

        addComponents(t, r, rb, p);

        damageDelt = 10f;
        bulletSpeed = GameManager.getSettings().get("starting").getFloat("cannonSpeed");
        plunderBonus = 10f;
        xpBonus = 10f;
    }

    public boolean isAlive() {
        return getComponent(Pirate.class).getHealth() > 0;
    }

    public static float getAttackRange() {
        return Utilities.tilesToDistance(GameManager.getSettings().get("starting").getFloat("attackRange_tiles"));
    }

    public void plunder(int money) {
        getComponent(Pirate.class).addPlunder(money);
    }

    /**
     * Associates ship with faction and orients it to the default northern direction.
     *
     * @param factionId the desired faction id
     */
    public void setFaction(int factionId) {
        getComponent(Pirate.class).setFactionId(factionId);
        setShipDirection("-up");
    }

    /**
     * Added for assessment 2
     * @return the faction of the ship
     */
    public Faction getFaction () {
        return getComponent(Pirate.class).getFaction();
    }

    /**
     * gets the string representation of the direction the ship is facing
     *
     * @param dir the vector dir the ship is facing
     * @return the string representation of the direction
     */
    public String getShipDirection(Vector2 dir) {
        if (!currentDir.equals(dir) && shipDirections.containsKey(dir)) {
            currentDir.set(dir);
            return shipDirections.get(dir);
        }
        return "";
    }

    /**
     * gets the faction colour
     *
     * @return the faction colour
     */
    private String getColour() {
        return getComponent(Pirate.class).getFaction().getColour();
    }

    /**
     * will rotate the ship to face the direction (just changes the sprite doesn't actually rotate)
     *
     * @param dir the dir to face (used to get the correct sprite from the texture atlas
     */
    public void setShipDirection(Vector2 dir) {
        setShipDirection(getShipDirection(dir));
    }

    /**
     * will rotate the ship to face the direction (just changes the sprite doesn't actually rotate)
     *
     * @param direction the dir to face (used to get the correct sprite from the texture atlas
     */
    public void setShipDirection(String direction) {
        if (Objects.equals(direction, "")) {
            return;
        }
        Renderable r = getComponent(Renderable.class);

        Sprite s = ResourceManager.getSprite(3, getColour() + direction);
//        if(getFaction().id == 1) {
//            System.out.println("XD");
//            s = ResourceManager.getSprite(3, getColour() + "-up");
//            s = new Sprite(getFrame(EntityManager.getDeltaTime(), direction));
//            s.scale(2f);
//        } else {
//            s = ResourceManager.getSprite(3, getColour() + direction);
//        }
        try {
            r.setTexture(s);
        } catch (Exception ignored) {

        }
    }

    public int getHealth() {
        return getComponent(Pirate.class).getHealth();
    }

    /**
     *
     * @param h
     */
    public void setHealth(int h) {
        getComponent(Pirate.class).setHealth(h);
    }

    /**
     *
     * @return
     */
    public int getArmor() {
        return getComponent(Pirate.class).getArmor();
    }

    /**
     *
     * @param a
     */
    public void setArmor(int a) {
        getComponent(Pirate.class).setArmor(a);
    }

    /**
     *
     * @param x
     * @param y
     */
    public void setSpeed(float x, float y) {
        getComponent(RigidBody.class).setVelocity(x, y);
    }

    /**
     *
     * @return
     */
    public Vector2 getSpeed() {
        return getComponent(RigidBody.class).getVelocity();
    }

    /**
     *
     */
    public void destroy() {
        setHealth(0);
    }

    public int getPlunder() {
        return getComponent(Pirate.class).getPlunder();
    }

    /**
     * Added for assessment 2
     * @param p set the plunder
     */
    public void setPlunder(int p) {
        getComponent(Pirate.class).setPlunder(p);
    }

    /**
     * Added for assessment 2
     * @return get the plunder
     */
    public float getPlunderBonus() {
        return plunderBonus;
    }

    /**
     * Added for assessment 2
     * @param plunder set the plunder will receive on achievement
     */
    public void setPlunderBonus(float plunder) {
        plunderBonus = plunder;
    }

    /**
     * Added for assessment 2
     * @return the xp
     */
    public int getXp() {
        return getComponent(Pirate.class).getXp();
    }

    /**
     * Added for assessment 2
     * @param xp update the current XP
     */
    public void setXp(int xp) {
        getComponent(Pirate.class).setXp(xp);
    }

    /**
     * Added for assessment 2
     * @return the current XP
     */
    public float getXpBonus() {
        return xpBonus;
    }

    /**
     * Added for assessment 2
     * @param xpBonus set the xp, which will be received on any achievement
     */
    public void setXpBonus(float xpBonus) {
        this.xpBonus = xpBonus;
    }

    /**
     * Added for assessment 2
     * @return the current ammo
     */
    public int getAmmo() { return getComponent(Pirate.class).getAmmo(); }

    /**
     * Added for assessment 2
     * @param a update the current ammo
     */
    public void setAmmo(int a) {
        getComponent(Pirate.class).setAmmo(a);
    }

    /**
     * Added for assessment 2
     * @param dmgDlt update the current dmaage delt
     */
    public void setDamageDelt(float dmgDlt) {
        damageDelt = dmgDlt;
    }

    /**
     * Added for assessment 2
     * @return the bullet speed
     */
    public float getBulletSpeed() {
        return bulletSpeed;
    }

    /**
     * Added for assessment 2
     * @param bSpeed update the current bullet speed
     */
    public void setBulletSpeed(float bSpeed) {
        bulletSpeed = bSpeed;
    }

    public void shoot(Vector2 dir) {
        getComponent(Pirate.class).shoot(dir);
    }

    public void shoot() {
        getComponent(Pirate.class).shoot(currentDir);
    }

    /**
     * @return copy of the transform's position
     */
    public Vector2 getPosition() {
        return getComponent(Transform.class).getPosition().cpy();
    }

    /**
     * Added for assessment 2
     * @param pos update the position of the ship
     */
    public void setPosition(Vector2 pos) {
        getComponent(Transform.class).setPosition(pos);
    }

    /**
     * Added for assessment 2
     * @return the velicoty of the ship
     */
    public Vector2 getVelocity() {
        return getComponent(Transform.class).getVelocity();
    }

    @Override
    public void BeginContact(CollisionInfo info) {

    }

    @Override
    public void EndContact(CollisionInfo info) {

    }

    /**
     * if called on a Player against anything else call it on the other thing
     */
    @Override
    public void EnterTrigger(CollisionInfo info) {
        if (this instanceof Player && !(info.b instanceof Player)) {
            ((CollisionCallBack) info.b).EnterTrigger(info);
        }
        /*
        Added for assessment 2
        Damage the ship if the bullet does not come from the same faction as the ship (thus, it came from enemy)
         */
        if (info.a instanceof CannonBall) {
            if (((CannonBall) info.a).getShooter().getFaction() != getFaction()) {
                getComponent(Pirate.class).takeDamage(damageDelt);
                ((CannonBall) info.a).kill();
            }
        }
    }

    /**
     * if called on a Player against anything else call it on the other thing
     */
    @Override
    public void ExitTrigger(CollisionInfo info) {
        if (this instanceof Player && !(info.b instanceof Player)) {
            ((CollisionCallBack) info.b).ExitTrigger(info);
        }
    }

//    private Animation<TextureRegion>[] rolls;
//    private Animation<TextureRegion>[] idleRolls;
//
//    int roll;
//    float rollVerticalTimer;
//    float rollHorizontalTimer;
//    float stateTime;
//
//    private Animation <TextureRegion> shipMove;
//
//    private final int PIXEL_SHIP_WIDTH = 1280;
//    private final int PIXEL_SHIP_HEIGHT = 1280;
//
//    private final float SHIP_FRAME_DURATION = 0.5f;






}
