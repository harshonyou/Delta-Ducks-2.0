package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Entitys.Building;
import com.mygdx.game.Entitys.College;
import com.mygdx.game.Entitys.Ship;

import java.util.ArrayList;

/**
 * Added the whole class for assessment 2
 * It helps to manage the management of save and load of player data
 */
public class SaveManager {

    /**
     * Save the player data
     */
    public static void save() {
        Preferences prefs = Gdx.app.getPreferences("saved_preference");

        for (College college : GameManager.getColleges()) {
            int acc = 0;
            for(Building building : college.getBuildings()) {
                prefs.putBoolean("College" + college.f.id + "B" + acc++, building.isAlive());
            }
        }

        int acc = 0;
        for (Ship ship : GameManager.getShips()) {
            acc++;
            prefs.putFloat("Ship"+acc+"x", ship.getPosition().x);
            prefs.putFloat("Ship"+acc+"y", ship.getPosition().y);
            prefs.putInteger("Ship"+acc+"h", ship.getHealth());
            prefs.putInteger("Ship"+acc+"p", ship.getPlunder());
            prefs.putInteger("Ship"+acc+"a", ship.getAmmo());
        }

//        System.out.println(GameManager.getPlayer().getFactonId());

//        prefs.putString("Ships", ships.toString());

        prefs.putString("Difficulty", DifficultyManager.getCurrentDifficulty());

        // Player stats
        prefs.putInteger("PlayerHealth", GameManager.getPlayer().getHealth());
        prefs.putInteger("PlayerArmor", GameManager.getPlayer().getArmor());
        prefs.putInteger("PlayerPlunder", GameManager.getPlayer().getPlunder());
        prefs.putInteger("PlayerAmmo", GameManager.getPlayer().getAmmo());
        prefs.putInteger("PlayerXp", GameManager.getPlayer().getXp());

        prefs.flush();
    }

    /**
     * Load the player data
     */
    public static void load() {
        Preferences prefs = Gdx.app.getPreferences("saved_preference");;

        if(prefs.get().isEmpty()) {
            DifficultyManager.Initialise(DifficultyManager.Difficulty.EASY);
            return;
        }

        for (College college : GameManager.getColleges()) {
            int acc = 0;
            for(Building building : college.getBuildings()) {
                if(!prefs.getBoolean("College" + college.f.id + "B" + acc++)){
                    building.destroy();
                }
            }
        }

//        for (College college : GameManager.getColleges()) {
//            int acc = 0;
//            for(Building building : college.getBuildings()) {
//                prefs.putBoolean("College" + college.f.id + "B" + acc++, building.isAlive());
//            }
//        }

        int acc = 0;
        for (Ship ship : GameManager.getShips()) {
//            ship.destroy();
            acc++;
            ship.setPosition(new Vector2(prefs.getFloat("Ship"+acc+"x"), prefs.getFloat("Ship"+acc+"y")));
            ship.setHealth(prefs.getInteger("Ship"+acc+"h"));
            ship.setPlunder(prefs.getInteger("Ship"+acc+"p"));
            ship.setAmmo(prefs.getInteger("Ship"+acc+"a"));
        }

        switch (prefs.getString("Difficulty")) {
            case "easy":
                DifficultyManager.Initialise(DifficultyManager.Difficulty.EASY);
                break;
            case "medium":
                DifficultyManager.Initialise(DifficultyManager.Difficulty.MEDIUM);
                break;
            default:
                DifficultyManager.Initialise(DifficultyManager.Difficulty.HARD);
        }

        // Player stats prefs.getInteger("")
        GameManager.getPlayer().setHealth(prefs.getInteger("PlayerHealth"));
        GameManager.getPlayer().setArmor(prefs.getInteger("PlayerArmor"));
        GameManager.getPlayer().setPlunder(prefs.getInteger("PlayerPlunder"));
        GameManager.getPlayer().setAmmo(prefs.getInteger("PlayerAmmo"));
        GameManager.getPlayer().setXp(prefs.getInteger("PlayerXp"));

    }
}
