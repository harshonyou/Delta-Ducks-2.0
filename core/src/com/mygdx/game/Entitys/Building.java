package com.mygdx.game.Entitys;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Components.Renderable;
import com.mygdx.game.Components.RigidBody;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Managers.RenderLayer;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.Physics.CollisionCallBack;
import com.mygdx.game.Physics.CollisionInfo;
import com.mygdx.game.Physics.PhysicsBodyType;

import java.util.Objects;

import static com.mygdx.utils.Constants.BUILDING_SCALE;

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

    Building(boolean isflag) {
        this();
        this.isFlag = isflag;
    }

    public void create(Vector2 pos, String name) {
        Sprite s = ResourceManager.getSprite(atlas_id, name);
        Renderable r = getComponent(Renderable.class);
        r.setTexture(s);
        getComponent(Transform.class).setPosition(pos);
        buildingName = name;

        RigidBody rb = new RigidBody(PhysicsBodyType.Static, r, getComponent(Transform.class));
        rb.setCallback(this);
        addComponent(rb);
    }

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

    }

    @Override
    public void EndContact(CollisionInfo info) {

    }

    @Override
    public void EnterTrigger(CollisionInfo info) {
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
    public void ExitTrigger(CollisionInfo info) {

    }
}
