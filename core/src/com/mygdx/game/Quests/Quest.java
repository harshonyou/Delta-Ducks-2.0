package com.mygdx.game.Quests;

import com.mygdx.game.Entitys.Building;
import com.mygdx.game.Entitys.College;
import com.mygdx.game.Entitys.Player;

/**
 * Base class for all quests facilitates the checking of completion
 */
public abstract class Quest {
    protected String name;
    protected String description;
    protected int reward;
    protected boolean isCompleted;


    public Quest() {
        name = "";
        description = "";
        reward = 0;
        isCompleted = false;
    }

    /**
     * Checks if the given player has met the complete condition
     *
     * @param p the player
     * @return has completed
     */
    public abstract boolean checkCompleted(Player p);

    public int getReward() {
        return reward;
    }

    public boolean isCompleted() {
//        if(!changedColor) {
//            for(Building building : college.getBuildings()) {
//                building.setActiveQuest();
//            }
//        }
        return isCompleted;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


}
