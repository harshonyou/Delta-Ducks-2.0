package com.mygdx.game.Entitys;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Components.Renderable;
import com.mygdx.game.Components.RigidBody;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.RenderLayer;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.Physics.CollisionCallBack;
import com.mygdx.game.Physics.CollisionInfo;
import com.mygdx.game.Physics.PhysicsBodyType;
import com.mygdx.utils.Utilities;

import java.util.Objects;

public class Ship extends Entity implements CollisionCallBack {
    private static int shipCount = 0;
    public static ObjectMap<Vector2, String> shipDirections;

    private final Vector2 currentDir;

    public Ship() {
        super(4);
        currentDir = new Vector2();
        setName("Ship (" + shipCount++ + ")");

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

        Pirate p = new Pirate();

        rb.setCallback(this);

        addComponents(t, r, rb, p);
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

    public void setFaction(int factionId) {
        getComponent(Pirate.class).setFactionId(factionId);
        setShipDirection("-up");
    }

    private String getShipDirection(Vector2 dir) {
        if (!currentDir.equals(dir) && shipDirections.containsKey(dir)){
            currentDir.set(dir);
            return shipDirections.get(dir);
        }
        return "";
    }

    private String getColour() {
        return getComponent(Pirate.class).getFaction().getColour();
    }

    public void setShipDirection(Vector2 dir) {
        setShipDirection(getShipDirection(dir));
    }
    public void setShipDirection(String direction) {
        if(Objects.equals(direction, "")) {
            return;
        }
        Renderable r = getComponent(Renderable.class);
        Sprite s = ResourceManager.getSprite(3, getColour() + direction);

        try {
            r.getSprite().setU(s.getU());
            r.getSprite().setV(s.getV());
            r.getSprite().setU2(s.getU2());
            r.getSprite().setV2(s.getV2());
        }catch (Exception ignored){

        }
    }

    public int getHealth() {
        return getComponent(Pirate.class).getHealth();
    }
    public int getPlunder() {
        return getComponent(Pirate.class).getPlunder();
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





    @Override
    public void BeginContact(CollisionInfo info) {

    }

    @Override
    public void EndContact(CollisionInfo info) {

    }

    /**
     * if the agro fixture hit a ship set it as the target
     * @param info the collision info
     */
    @Override
    public void EnterTrigger(CollisionInfo info) {
        if (info.fA.isSensor() || !info.fB.isSensor() || this != info.a) {
            throw new RuntimeException("error in triggers");
        }

        final Pirate p = info.b.getComponent(Pirate.class);
        final String data = (String) info.fB.getUserData();

        if (info.a instanceof Ship) {
            if (Objects.equals(data, "agro")) {
                p.setTarget((Ship) info.a);
            }
            else{
                throw new RuntimeException("error in determining attack state for ships");
            }
        }
    }

    /**
     * Will set the target to null
     * @param info collision info
     */
    @Override
    public void ExitTrigger(CollisionInfo info) {
        if(info.b instanceof Player){
            return;
        }
        final Pirate p = info.b.getComponent(Pirate.class);
        p.setTarget(null);
    }
}