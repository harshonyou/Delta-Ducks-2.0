package com.mygdx.testing.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Entitys.Building;
import com.mygdx.game.Entitys.College;
import com.mygdx.game.Entitys.Player;
import com.mygdx.game.Entitys.Ship;
import com.mygdx.game.Managers.*;
import com.mygdx.game.PirateGame;
import com.mygdx.testing.GdxTestRunner;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class SaveTests {

    @BeforeClass
    public static void init(){
        PirateGame.loadResources();
        PhysicsManager.Initialise();
        GameManager.Initialise();
    }

    @AfterClass
    public static void dispose(){
        ResourceManager.reset();
    }

    @Before
    public void pre() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();
        DifficultyManager.reset();
        DifficultyManager.Initialise(DifficultyManager.Difficulty.EASY);
    }

    @Test
    public void saveColleges() {
        SaveManager.save();
        Preferences prefs = Gdx.app.getPreferences("saved_preference");

        for (College college : GameManager.getColleges()) {
            int acc = 0;
            for(Building building : college.getBuildings()) {
                assertEquals(prefs.getBoolean("College" + college.f.id + "B" + acc++), building.isAlive());
            }
        }
    }

    @Test
    public void saveShips() {
        SaveManager.save();
        Preferences prefs = Gdx.app.getPreferences("saved_preference");

        int acc = 0;
        for (Ship ship : GameManager.getShips()) {
            acc++;
            assertEquals(prefs.getFloat("Ship"+acc+"x"), ship.getPosition().x, 0);
            assertEquals(prefs.getFloat("Ship"+acc+"y"), ship.getPosition().y, 0);
            assertEquals(prefs.getFloat("Ship"+acc+"h"), ship.getHealth(), 0);
            assertEquals(prefs.getFloat("Ship"+acc+"p"), ship.getPlunder(), 0);
            assertEquals(prefs.getFloat("Ship"+acc+"a"), ship.getAmmo(), 0);
        }
    }

    @Test
    public void saveDifficulty() {
        SaveManager.save();
        Preferences prefs = Gdx.app.getPreferences("saved_preference");

        assertEquals(prefs.getString("Difficulty"), DifficultyManager.getCurrentDifficulty());
    }

    @Test
    public void savePlayerStats() {
        SaveManager.save();
        Preferences prefs = Gdx.app.getPreferences("saved_preference");

        assertEquals(prefs.getInteger("PlayerHealth"), GameManager.getPlayer().getHealth());
        assertEquals(prefs.getInteger("PlayerArmor"), GameManager.getPlayer().getArmor());
        assertEquals(prefs.getInteger("PlayerPlunder"), GameManager.getPlayer().getPlunder());
        assertEquals(prefs.getInteger("PlayerAmmo"), GameManager.getPlayer().getAmmo());
        assertEquals(prefs.getInteger("PlayerXp"), GameManager.getPlayer().getXp());
    }

    @Test
    public void loadColleges() {
        for (College college : GameManager.getColleges()) {
            for(Building building : college.getBuildings()) {
                if(building.isFlag()){
                    building.destroyFlag();
                } else {
                    building.destroy();
                }
            }
        }

        SaveManager.save();
        SaveManager.load();

        Preferences prefs = Gdx.app.getPreferences("saved_preference");

        for (College college : GameManager.getColleges()) {
            int acc = 0;
            for(Building building : college.getBuildings()) {
                assertEquals(prefs.getBoolean("College" + college.f.id + "B" + acc++), building.isAlive());
            }
        }
    }

    @Test
    public void loadShips() {
        for (Ship ship : GameManager.getShips()) {
            ship.setPosition(new Vector2(12,21));
            ship.setHealth(121);
            ship.setPlunder(212);
            ship.setArmor(777);
        }

        SaveManager.save();
        Preferences prefs = Gdx.app.getPreferences("saved_preference");

        int acc = 0;
        for (Ship ship : GameManager.getShips()) {
            acc++;
            assertEquals(prefs.getFloat("Ship"+acc+"x"), ship.getPosition().x, 0);
            assertEquals(prefs.getFloat("Ship"+acc+"y"), ship.getPosition().y, 0);
            assertEquals(prefs.getFloat("Ship"+acc+"h"), ship.getHealth(), 0);
            assertEquals(prefs.getFloat("Ship"+acc+"p"), ship.getPlunder(), 0);
            assertEquals(prefs.getFloat("Ship"+acc+"a"), ship.getAmmo(), 0);
        }
    }

    @Test
    public void loadDifficulty() {
        DifficultyManager.reset();
        DifficultyManager.Initialise(DifficultyManager.Difficulty.HARD);
        SaveManager.save();
        Preferences prefs = Gdx.app.getPreferences("saved_preference");

        assertEquals(prefs.getString("Difficulty"), DifficultyManager.getCurrentDifficulty());
    }

    @Test
    public void loadPlayerStats() {
        Player p = GameManager.getPlayer();
        p.setHealth(100);
        p.setArmor(123);
        p.setPlunder(24);
        p.setAmmo(35);
        p.setXp(13);
        SaveManager.save();
        Preferences prefs = Gdx.app.getPreferences("saved_preference");

        assertEquals(prefs.getInteger("PlayerHealth"), GameManager.getPlayer().getHealth());
        assertEquals(prefs.getInteger("PlayerArmor"), GameManager.getPlayer().getArmor());
        assertEquals(prefs.getInteger("PlayerPlunder"), GameManager.getPlayer().getPlunder());
        assertEquals(prefs.getInteger("PlayerAmmo"), GameManager.getPlayer().getAmmo());
        assertEquals(prefs.getInteger("PlayerXp"), GameManager.getPlayer().getXp());
    }
}
