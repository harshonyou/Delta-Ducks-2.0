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
    protected College college; // added for assessment 2
    protected boolean changedColor; // added for assessment 2
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

    /**
     * added for assessment 2
     * update the color of the colleges in minimap which are currently an active quest
     */
    public void setColor() {
        if(!changedColor) {
            changedColor = true;
            for(Building building : college.getBuildings()) {
                building.setActiveQuest();
            }
        }
    }

    /**
     * added for assessment 2
     * @param college to update as current quest
     */
    public void setCollege(College college) {
        this.college = college;
    }

    /**
     * added for assessment 2
     * @return college as current quest
     */
    public College getCollege() {
        return college;
    }

    /**
     * added for assessment 2
     * remove the college color as active quest
     */
    public void removeColor() {
        for(Building building : college.getBuildings()) {
            building.setInactiveQuest();
        }
    }
}
