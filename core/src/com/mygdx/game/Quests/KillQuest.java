package com.mygdx.game.Quests;

import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Entitys.Building;
import com.mygdx.game.Entitys.College;
import com.mygdx.game.Entitys.Entity;
import com.mygdx.game.Entitys.Player;

/**
 * A Quest to kill a college is only complete once that college is dead
 */
public class KillQuest extends Quest {
    protected College college;
    protected boolean changedColor;
    private Pirate target;

    public KillQuest() {
        super();
        name = "Destroy or Capture";
        description = "KILL KILL KILL";
        reward = 100;
        target = null;
        changedColor = false;
    }

    public KillQuest(Pirate target) {
        this();
        this.target = target;
        description = target.getFaction().getName() + " College\n(In Golden)";
    }

    public KillQuest(Entity target) {
        this(target.getComponent(Pirate.class));
        college = (College) target;
    }

    @Override
    public boolean checkCompleted(Player p) {
        isCompleted = !target.isAlive();
        return isCompleted;
    }

    public void setColor() {
        if(!changedColor) {
            changedColor = true;
            for(Building building : college.getBuildings()) {
                building.setActiveQuest();
            }
        }
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public College getCollege() {
        return college;
    }

    public void removeColor() {
        for(Building building : college.getBuildings()) {
            building.setInactiveQuest();
        }
    }
}
