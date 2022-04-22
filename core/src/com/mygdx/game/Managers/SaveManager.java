package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Entitys.College;
import com.mygdx.game.Entitys.Ship;

import java.util.ArrayList;

public class SaveManager {

    public static void save() {
        Preferences prefs = Gdx.app.getPreferences("saved_preference");

        for (College college : GameManager.getColleges()) {
            prefs.putBoolean("College" + college.f.id, college.aliveTest());
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
        prefs.flush();
    }

    public static void load() {
        Preferences prefs = Gdx.app.getPreferences("saved_preference");

        for (College college : GameManager.getColleges()) {
//            college.destroy();
            if(!prefs.getBoolean("College" + college.f.id)){
                System.out.println("Dead");
                college.destroy();
            }
        }

        int acc = 0;
        for (Ship ship : GameManager.getShips()) {
//            ship.destroy();
            acc++;
            ship.setPosition(new Vector2(prefs.getFloat("Ship"+acc+"x"), prefs.getFloat("Ship"+acc+"y")));
            ship.setHealth(prefs.getInteger("Ship"+acc+"h"));
            ship.setPlunder(prefs.getInteger("Ship"+acc+"p"));
            ship.setAmmo(prefs.getInteger("Ship"+acc+"a"));
        }
    }

    public static void test() {
        Preferences prefs = Gdx.app.getPreferences("saved_preference");

    }
}
