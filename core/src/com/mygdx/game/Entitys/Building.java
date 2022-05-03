package com.mygdx.game.Entitys;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Components.Renderable;
import com.mygdx.game.Components.RigidBody;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Faction;
import com.mygdx.game.Managers.*;
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

    private boolean activeQuestToggle; //Added for assessment 2

    public Faction f; //Added for assessment 2

    Building() {
        super();
        isFlag = false;
        Transform t = new Transform();
        t.setScale(BUILDING_SCALE, BUILDING_SCALE);
        Pirate p = new Pirate();
        atlas_id = ResourceManager.getId("Buildings.txt");
        Renderable r = new Renderable(atlas_id, "big", RenderLayer.Transparent);
        addComponents(t, p, r);
        activeQuestToggle = false;
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
     * Added for assessment 2
     * @return  true if the building is actually a flag
     */
    public boolean isFlag() {
        return isFlag;
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
        GameManager.getPlayer().setXp((int) (GameManager.getPlayer().getXp() + 5f));
    }

    /**
     * Destory the flag if player chooses to destroy the whole college
     */
    public void destroyFlag() {
        Sprite s = ResourceManager.getSprite(atlas_id, "white");
        Renderable r = getComponent(Renderable.class);
        r.setTexture(s);
        getComponent(Pirate.class).kill();

        int acc = 0;
        for(Ship ship : GameManager.getShips()) {
            if(ship.getFaction() == f) {
//                ship.setFaction(GameManager.getPlayer().getFaction().id);
                ship.destroy();
                acc++;
            }
        }
        CaptureManager.destroyHandler(f.getName(), acc);
    }

    /**
     * Added for assessment 2
     * @return  if the current college is active kill quest
     */
    public boolean isActiveQuest() {
        return activeQuestToggle;
    }

    /**
     * Added for assessment 2
     * set the current college active kill quest
     */
    public void setActiveQuest() {
        activeQuestToggle = true;
    }

    /**
     * Added for assessment 2
     * set the current college inactive kill quest
     */
    public void setInactiveQuest() {
        activeQuestToggle = false;
    }

    public boolean isAlive() {
        return getComponent(Pirate.class).isAlive();
    }

    /**
     * Added for assessment 2
     * @return the position of the building of a college
     */
    public Vector2 getPosition() {
        return getComponent(Transform.class).getPosition();
    }

    /**
     * Added for assessment 2
     * @return the faction of the building of a college
     */
    public Faction getFaction() {
        return f;
    }

    /**
     * Added for assessment 2
     * Set the faction of the building of a college to the faction of the player
     */
    public void setFaction() {
        f = GameManager.getPlayer().getFaction();
    }

    /**
     * Added for assessment 2
     * Set the faction of the building of a college to custom faction
     * @param f Faction
     */
    public void setFactionCustom(Faction f) {
        this.f = f;
    }

    /**
     * Added for assessment 2
     * Update the flag is the college is being captured successfully
     */
    public void updateFlag() {
        getComponent(Renderable.class).setTexture(ResourceManager.getSprite(atlas_id, f.getColour()));
        CaptureManager.captureHandler(f.getName());
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
