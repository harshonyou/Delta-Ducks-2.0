package com.mygdx.game.Components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Entitys.Ship;
import com.mygdx.game.Faction;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.utils.QueueFIFO;

/**
 * Gives the concepts of health plunder, etc. Allows for firing of cannonballs, factions, death, targets
 */
public class Pirate extends Component {
    private int factionId;
    private int plunder;
    private int xp; // Added for assessment 2
    protected boolean isAlive;
    private int health;
    private int ammo;
    private int armor; // Added for assessment 2
    private final int attackDmg;

    /**
     * The enemy that is being targeted by the AI.
     */
    private final QueueFIFO<Ship> targets;

    public Pirate() {
        super();
        targets = new QueueFIFO<>();
        type = ComponentType.Pirate;
        plunder = GameManager.getSettings().get("starting").getInt("plunder");
        xp = 0;
        factionId = 1;
        isAlive = true;
        JsonValue starting = GameManager.getSettings().get("starting");
        health = starting.getInt("health");
        armor = 0;
        attackDmg = starting.getInt("damage");
        ammo = starting.getInt("ammo");
    }

    public void addTarget(Ship target) {
        targets.add(target);
    }

    public int getPlunder() {
        return plunder;
    }

    /**
     *  Added for assessment 2
     * @param p to set the plunder
     */
    public void setPlunder(int p) {
        plunder = p;
    }

    public void addPlunder(int money) {
        plunder += money;
    }

    /**
     * Added for assessment 2
     * @return xp
     */
    public int getXp() {
        return xp;
    }

    /**
     * Added for assessment 2
     * @param xp to set the entity XP
     */
    public void setXp(int xp) {
        this.xp = xp;
    }

    /**
     * Added for assessment 2
     * @param xp to add to the entity XP
     */
    public void addXp(int xp) {
        this.xp += xp;
    }


    public Faction getFaction() {
        return GameManager.getFaction(factionId);
    }

    public void setFactionId(int factionId) {
        this.factionId = factionId;
    }

    /**
     * Updated for assessment 2
     * @param dmg to make entity armor or health take damage
     */
    public void takeDamage(float dmg) {
        if(armor<=0) {
            health -= dmg;
        } else {
            if(armor-dmg*1.5 <= 0) {
                health = (int) (health + armor - dmg*1.5);
                armor = 0;
            } else {
                armor -= dmg*1.5;
            }
        }
        if (health <= 0) {
            health = 0;
            isAlive = false;
        }
    }

    /**
     * Will shoot a cannonball assigning this.parent as the cannonball's parent (must be Ship atm)
     *
     * @param dir the direction to shoot in
     */
    public void shoot(Vector2 dir) {
        if (ammo == 0) {
            return;
        }
        ammo--;
        GameManager.shoot((Ship) parent, dir);
    }

    /**
     * Adds ammo
     *
     * @param ammo amount to add
     */
    public void reload(int ammo) {
        this.ammo += ammo;
    }

    public int getHealth() {
        return health;
    }

    /**
     * Added for assessment 2
     * @param h to set the health
     */
    public void setHealth(int h) {
        health = h;
    }

    /**
     * Added for assessment 2
     * @return armror
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Added for assessment 2
     * @param a to set the armor
     */
    public void setArmor(int a) {
        armor = a;
    }

    /**
     * if dst to target is less than attack range
     * target will be null if not in agro range
     */
    public boolean canAttack() {
        if (targets.peek() != null) {
            final Ship p = (Ship) parent;
            final Vector2 pos = p.getPosition();
            final float dst = pos.dst(targets.peek().getPosition());
            // withing attack range
            return dst < Ship.getAttackRange();
        }
        return false;
    }

    /**
     * Added for assessment 2
     * @return the location of the targeted ship
     */
    public Vector2 targetPos() {
        Ship p = (Ship) parent;
        Vector2 pos = p.getPosition();
        Vector2 targetPos = targets.peek().getPosition();
        return targetPos.sub(pos);
    }

    /**
     * if dst to target is >= attack range
     * target will be null if not in agro range
     */
    public boolean isAgro() {
        if (targets.peek() != null) {
            final Ship p = (Ship) parent;
            final Vector2 pos = p.getPosition();
            final float dst = pos.dst(targets.peek().getPosition());
            // out of attack range but in agro range
            return dst >= Ship.getAttackRange();
        }
        return false;
    }

    public Ship getTarget() {
        return targets.peek();
    }

    public void removeTarget() {
        targets.pop();
    }

    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Kill its self
     */
    public void kill() {
        health = 0;
        isAlive = false;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getAmmo() {
        return ammo;
    }

    public int targetCount() {
        return targets.size();
    }

    public QueueFIFO<Ship> getTargets() {
        return targets;
    }
}
