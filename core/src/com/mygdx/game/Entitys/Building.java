package com.mygdx.game.Entitys;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.*;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.RenderLayer;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.Physics.CollisionCallBack;
import com.mygdx.game.Physics.CollisionInfo;
import com.mygdx.game.Physics.PhysicsBodyType;

import static com.mygdx.utils.Constants.BUILDING_SCALE;

/**
 * Buildings that you see in game.
 */
public class Building extends Entity implements CollisionCallBack {
    private String buildingName;
    private static int atlas_id;
    private boolean isFlag;

    Building() {
        super();
        isFlag = false;
        Transform t = new Transform();
        t.setScale(BUILDING_SCALE, BUILDING_SCALE);
        Pirate p = new Pirate();
        atlas_id = ResourceManager.getId("Buildings.txt");
        Renderable r = new Renderable(atlas_id, "big", RenderLayer.Transparent);
        addComponents(t, p, r);
    }

    /**
     * Flags are indestructible and mark college locations.
     *
     * @param isFlag set to true to create a flag
     */
    Building(boolean isFlag) {
        this();
        this.isFlag = isFlag;
    }

    /**
     * Creates a building with the given name at the specified location.
     *
     * @param pos  2D position vector
     * @param name name of building
     */
    public void create(Vector2 pos, String name) {
        Sprite s = ResourceManager.getSprite(atlas_id, name);
        Renderable r = getComponent(Renderable.class);
        r.setTexture(s);
        getComponent(Transform.class).setPosition(pos);
        buildingName = name;

        RigidBody rb = new RigidBody(PhysicsBodyType.Static, r, getComponent(Transform.class));
        rb.setCallback(this);
        rb.addTrigger(200, "lmao");
        addComponent(rb);
    }

    /**
     * Replace the building with ruins and mark as broken.
     */
    private void destroy() {
        if (isFlag) {
            return;
        }
        Sprite s = ResourceManager.getSprite(atlas_id, buildingName + "-broken");
        Renderable r = getComponent(Renderable.class);
        r.setTexture(s);
        getComponent(Pirate.class).kill();
    }

    public boolean isAlive() {
        return getComponent(Pirate.class).isAlive();
    }

    @Override
    public void BeginContact(CollisionInfo info) {
        System.out.println("god");
        if (info.a instanceof CannonBall && isAlive()) {
            CannonBall b = (CannonBall) info.a;
            // the ball if from the same faction
            /*if(Objects.equals(b.getShooter().getComponent(Pirate.class).getFaction().getName(),
                    getComponent(Pirate.class).getFaction().getName())) {
                return;
            }*/
            destroy();
            ((CannonBall) info.a).kill();
        }
    }

    @Override
    public void EndContact(CollisionInfo info) {

    }

    /**
     * Destroys the building and marks cannonball for removal.
     *
     * @param info CollisionInfo container
     */
    @Override
    public void EnterTrigger(CollisionInfo info) {
        System.out.println("Yikes");
        System.out.println(PlayerController.getLoc());
//        GameManager.shoot(PlayerController.getPlayer(), new Vector2(800,800));
    }

    @Override
    public void ExitTrigger(CollisionInfo info) {

    }
}
