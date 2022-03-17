package com.mygdx.game.Entitys;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.Renderable;
import com.mygdx.game.Components.RigidBody;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Managers.EntityManager;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.RenderLayer;
import com.mygdx.game.Physics.CollisionCallBack;
import com.mygdx.game.Physics.CollisionInfo;
import com.mygdx.game.Physics.PhysicsBodyType;

import static com.mygdx.utils.Constants.TILE_SIZE;

/**
 * Cannonball entity and the methods to get it flying.
 */
public class CannonBall extends Entity implements CollisionCallBack {
    private static float speed;
    private boolean toggleLife;
    private static final int MAX_AGE = 5;
    // private float age = 0;
    private Ship shooter;

    public CannonBall() {
        super(3);
        setName("ball");
        toggleLife = false;
        Transform t = new Transform();
        t.setPosition(-100, 100);
        t.setScale(0.5f, 0.5f);
        Renderable r = new Renderable(4, "ball", RenderLayer.Transparent);
        RigidBody rb = new RigidBody(PhysicsBodyType.Dynamic, r, t, true);
        rb.setCallback(this);

        addComponents(t, r, rb);

        speed = GameManager.getSettings().get("starting").getFloat("cannonSpeed");
        r.hide();
    }

    @Override
    public void update() {
        super.update();
        removeOnCollision();
    }

    /**
     * Removes the cannonball offscreen once it hits a target.
     */
    private void removeOnCollision() {
        if (toggleLife) {
            getComponent(Renderable.class).hide();
            Transform t = getComponent(Transform.class);
            t.setPosition(10000, 10000);

            RigidBody rb = getComponent(RigidBody.class);
            rb.setPosition(t.getPosition());
            rb.setVelocity(0, 0);
            toggleLife = false;
        }
        /*else{
            age += EntityManager.getDeltaTime();
        }
        if(age > MAX_AGE) {
            age = 0;
            kill();
        }*/
    }

    /**
     * Teleport the cannonball in from offscreen and set in flying away from the ship.
     *
     * @param pos    2D vector location from where it sets off
     * @param dir    2D vector direction for its movement
     * @param sender ship entity firing it
     */
    public void fire(Vector2 pos, Vector2 dir, Ship sender) {
        Transform t = getComponent(Transform.class);
        t.setPosition(pos);

        RigidBody rb = getComponent(RigidBody.class);
        Vector2 ta = dir.cpy().scl(speed * EntityManager.getDeltaTime());
        Vector2 o = new Vector2(TILE_SIZE * t.getScale().x, TILE_SIZE * t.getScale().y);
        Vector2 v = ta.cpy().sub(o);

        rb.setVelocity(v);

        getComponent(Renderable.class).show();
        shooter = sender;
    }

    public void fire(Vector2 pos, Vector2 dir) {
        Transform t = getComponent(Transform.class);
        t.setPosition(pos);

        RigidBody rb = getComponent(RigidBody.class);
        Vector2 ta = dir.cpy().scl(speed * EntityManager.getDeltaTime());
        Vector2 o = new Vector2(TILE_SIZE * t.getScale().x, TILE_SIZE * t.getScale().y);
        Vector2 v = ta.cpy().sub(o);

        rb.setVelocity(v);

        getComponent(Renderable.class).show();
    }

    /**
     * Marks cannonball for removal on next update.
     */
    public void kill() {
        toggleLife = false;
    }

    public Ship getShooter() {
        return shooter;
    }

    @Override
    public void BeginContact(CollisionInfo info) {

    }

    @Override
    public void EndContact(CollisionInfo info) {

    }

    @Override
    public void EnterTrigger(CollisionInfo info) {

    }

    @Override
    public void ExitTrigger(CollisionInfo info) {

    }
}
