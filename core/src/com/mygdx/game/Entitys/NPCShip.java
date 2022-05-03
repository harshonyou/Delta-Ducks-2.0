package com.mygdx.game.Entitys;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.AI.EnemyState;
import com.mygdx.game.Components.*;
import com.mygdx.game.Managers.EntityManager;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.RenderLayer;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.Physics.CollisionCallBack;
import com.mygdx.game.Physics.CollisionInfo;
import com.mygdx.utils.QueueFIFO;
import com.mygdx.utils.Utilities;

import java.util.Objects;

/**
 * NPC ship entity class.
 */
public class NPCShip extends Ship implements CollisionCallBack {
    public StateMachine<NPCShip, EnemyState> stateMachine;
    private static JsonValue AISettings;
    private final QueueFIFO<Vector2> path;
    private long shootTime;

    private Renderable healthBar; // Added for assessment 2

    private static float FREEZE_TIME = GameManager.getSettings().get("AI").getFloat("cannonTimeout"); // Added for assessment 2
    private float freezeTimer; // Added for assessment 2

    private float damageDelt; // Added for assessment 2

    private boolean bonusGained = false; // Added for assessment 2

//    private float bulletSpeed;

    /**
     * Creates an initial state machine
     */
    public NPCShip() {
        super();
        path = new QueueFIFO<>();
        shootTime = 0;

        if (AISettings == null) {
            AISettings = GameManager.getSettings().get("AI");
        }

        stateMachine = new DefaultStateMachine<>(this, EnemyState.WANDER);

        setName("NPC");
        AINavigation nav = new AINavigation();

        addComponent(nav);
        healthBar = new Renderable(ResourceManager.getId("blank.png"), RenderLayer.Transparent); // Added for assessment 2

        RigidBody rb = getComponent(RigidBody.class);
        // rb.setCallback(this);

        JsonValue starting = GameManager.getSettings().get("starting");

        // agro trigger
        rb.addTrigger(Utilities.tilesToDistance(starting.getFloat("argoRange_tiles")), "agro");

        /*
        Added for assessment 2
        Initialized the health bar
         */
        addComponents(healthBar);
        healthBar.show();
        healthBar.setDisplacement(-3, 0);

        freezeTimer = 0;

        damageDelt = 20f;

//        bulletSpeed = GameManager.getSettings().get("starting").getFloat("cannonSpeed");
    }

    /**
     * gets the top of targets from pirate component
     *
     * @return the top target
     */
    private Ship getTarget() {
        return getComponent(Pirate.class).getTarget();
    }

    /**
     * updates the state machine
     */
    @Override
    public void update() {
        freezeTimer += EntityManager.getDeltaTime(); // Added for assessment 2
        super.update();

        /*
        Added for assessment 2
        Check if the health is less than 0; if yes the player is dead and game is over
        For all other cases it measures the armor and health and makes health and armor bar
        and set the color corresponding to its percentage.
         */
        if (getHealth() <= 0) {
            removeOnDeath();
            healthBar.hide();
            if(!bonusGained) {
                GameManager.getPlayer().setPlunder((int) (GameManager.getPlayer().getPlunder() + getPlunderBonus()));
                GameManager.getPlayer().setXp((int) (GameManager.getPlayer().getXp() + getXpBonus()));
                bonusGained = true;
            }
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
            healthBar.setSize(2, 3*(getHealth()/10));
        }
        if (getComponent(Pirate.class).canAttack()) {
            if (freezeTimer >= FREEZE_TIME) {
                freezeTimer = 0;
                super.shoot(getComponent(Pirate.class).targetPos());
            }
        }
        stateMachine.update();
        // System.out.println(getComponent(Pirate.class).targetCount());
    }

    /**
     * is meant to path find to the target but didn't work
     */
    public void goToTarget() {
        /*path = GameManager.getPath(
                Utilities.distanceToTiles(getPosition()),
                Utilities.distanceToTiles(getTarget().getPosition()));*/
    }

    /**
     * creates a new steering behaviour that will make the NPC beeline for the target doesn't factor in obstetrical
     */
    public void followTarget() {
        if (getTarget() == null) {
            stopMovement();
            return;
        }
        AINavigation nav = getComponent(AINavigation.class);

        Arrive<Vector2> arrives = new Arrive<>(nav,
                getTarget().getComponent(Transform.class))
                .setTimeToTarget(AISettings.getFloat("accelerationTime"))
                .setArrivalTolerance(AISettings.getFloat("arrivalTolerance"))
                .setDecelerationRadius(AISettings.getFloat("slowRadius"));

        nav.setBehavior(arrives);
    }

    /**
     * stops all movement and sets the behaviour to null
     */
    public void stopMovement() {
        AINavigation nav = getComponent(AINavigation.class);
        nav.setBehavior(null);
        nav.stop();
    }

    /**
     * Meant to cause the npc to wander
     */
    public void wander() {

    }

    /**
     * Added for assessment 2
     * @param dmgDlt amount of damage to deal
     */
    public void setDamageDelt(float dmgDlt) {
        damageDelt = dmgDlt;
    }

//    public float getBulletSpeed() {
//        return bulletSpeed;
//    }

    @Override
    public void BeginContact(CollisionInfo info) {

    }

    @Override
    public void EndContact(CollisionInfo info) {

    }

    /**
     * if the agro fixture hit a ship set it as the target
     *
     * @param info the collision info
     */
    @Override
    public void EnterTrigger(CollisionInfo info) {
        if (info.a instanceof Ship) {
            Ship other = (Ship) info.a;
            if (Objects.equals(other.getComponent(Pirate.class).getFaction().getName(), getComponent(Pirate.class).getFaction().getName())) {
                // is the same faction
                return;
            }
            // add the new collision as a new target
            Pirate pirate = getComponent(Pirate.class);
            pirate.addTarget(other);
        }
        /*
        Added for assessment 2
        if the damage is done by any other ship than the NPC college's ships then it will receive the damage
         */
        if (info.a instanceof CannonBall) {
            if (((CannonBall) info.a).getShooter().getFaction() != super.getFaction()) {
                getComponent(Pirate.class).takeDamage(damageDelt);
                ((CannonBall) info.a).kill();
            }
        }
    }

    /**
     * if a target has left remove it from the potential targets Queue
     *
     * @param info collision info
     */
    @Override
    public void ExitTrigger(CollisionInfo info) {
        if (!(info.a instanceof Ship)) {
            return;
        }
        Pirate pirate = getComponent(Pirate.class);
        Ship o = (Ship) info.a;
        // remove the object from the targets list
        for (Ship targ : pirate.getTargets()) {
            if (targ == o) {
                pirate.getTargets().remove(targ);
                break;
            }
        }
    }

    /**
     * Added for assessment 2
     * Remove the NPC ship once they take sufficient damage
     */
    private void removeOnDeath() {
        stopMovement();
        getComponent(Renderable.class).hide();
        Transform t = getComponent(Transform.class);
        t.setPosition(-50, -50);
    }

}
