package com.mygdx.game.Quests;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Entitys.Player;

import static com.mygdx.utils.Constants.TILE_SIZE;

/**
 * Competed once the player has gone to a specific position
 */
public class LocateQuest extends Quest {
    private final Vector2 loc;
    private float radius;

    public LocateQuest() {
        super();
        name = "Find a chest";
        description = "North east";
        reward = 100;
        loc = new Vector2();
        radius = -1;
    }

    /**
     * The loc to go to and radius that the play has to be in to completed it
     *
     * @param pos location to find
     * @param r   leeway in completion
     */
    public LocateQuest(Vector2 pos, float r) {
        this();
        loc.set(pos);
        radius = r * r;
        pos.scl(1 / TILE_SIZE).sub(50, 50); // centres on 0, 0
        description = "";
        if (pos.y > 0) {
            description += "North ";
        } else if (pos.y < 0) {
            description += "South ";
        }
        if (pos.x > 0) {
            description += "East";
        } else if (pos.x < 0) {
            description += "West";
        }
        description += "\n(In Golden)";
    }

    @Override
    public boolean checkCompleted(Player p) {
        if (radius == -1) {
            return false;
        }
        Vector2 delta = p.getPosition().cpy();
        delta.sub(loc);
        float l = delta.len2();
        isCompleted = l < radius;
        return isCompleted;
    }

    public Vector2 getLocation() {
        return loc;
    }
}
