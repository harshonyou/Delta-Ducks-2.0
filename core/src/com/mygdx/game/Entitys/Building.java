package com.mygdx.game.Entitys;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
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

import java.util.Objects;

import static com.mygdx.utils.Constants.BUILDING_SCALE;

/**
 * Buildings that you see in game.
 */
public class Building extends Entity implements CollisionCallBack {
    private String buildingName;
    private static int atlas_id;
    private boolean isFlag;

    public Faction f;

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
    public void create(Vector2 pos, String name, Faction f) {
        Sprite s = ResourceManager.getSprite(atlas_id, name);
        Renderable r = getComponent(Renderable.class);
        r.setTexture(s);
        getComponent(Transform.class).setPosition(pos);
        buildingName = name;

        RigidBody rb = new RigidBody(PhysicsBodyType.Static, r, getComponent(Transform.class));
        rb.setCallback(this);
        addComponent(rb);
        this.f = f;
    }

    /**
     * Replace the building with ruins and mark as broken.
     */
    public void destroy() {
        if (isFlag) {
            return;
        }
        Sprite s = ResourceManager.getSprite(atlas_id, buildingName + "-broken");
        Renderable r = getComponent(Renderable.class);
        r.setTexture(s);
        getComponent(Pirate.class).kill();
        GameManager.getPlayer().setPlunder((int) (GameManager.getPlayer().getPlunder() + 5f));
    }

    public boolean isAlive() {
        return getComponent(Pirate.class).isAlive();
    }

    public Vector2 getPosition() {
        return getComponent(Transform.class).getPosition();
    }

    public Faction getFaction() {
        return f;
    }

    public void setFaction () {
        f = GameManager.getPlayer().getFaction();
    }

    public void updateFlag() {
        getComponent(Renderable.class).setTexture(ResourceManager.getSprite(atlas_id, f.getColour()));
    }

    @Override
    public void BeginContact(CollisionInfo info) {

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
        if (info.a instanceof CannonBall && isAlive()) {
            CannonBall b = (CannonBall) info.a;
            // the ball if from the same faction
//            if(Objects.equals(b.getShooter().getComponent(Pirate.class).getFaction().getName(),
//                    getComponent(Pirate.class).getFaction().getName())) {
//                return;
//            }
            if((((CannonBall) info.a).getShooter().getFaction() == getFaction())){
                return;
            }
            if((((CannonBall) info.a).getShooter().getFaction() == GameManager.getPlayer().getFaction())) {
                destroy();
                if(!isAlive()) {
                    ((CannonBall) info.a).kill();
                }
            }
        }
    }

    @Override
    public void ExitTrigger(CollisionInfo info) {

    }
}
