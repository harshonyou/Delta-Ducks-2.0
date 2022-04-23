package com.mygdx.game.Managers;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.AINavigation;
import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Entitys.Ship;

public class DifficultyManager {
    public static boolean initialised = false;

    public enum Difficulty {EASY, MEDIUM, HARD};
    public static Difficulty currentDifficulty;

    public static void Initialise(Difficulty difficulty) {
        if (initialised) {
            return;
        }
        initialised = true;
        currentDifficulty = difficulty;
        difficultyHandler();
    }

    private static void difficultyHandler() {
        switch (currentDifficulty) {
            case EASY:
                easyHandler();
                break;
            case MEDIUM:
                mediumHandler();
                break;
            default:
                hardHandler();
                break;
        }
    }

    private static void easyHandler() {
        System.out.println("Easy");
        GameManager.getPlayer().setHealth(100);
        GameManager.getPlayer().setDamageDelt(10f);
        GameManager.getPlayer().setPlunder(100);
        GameManager.getPlayer().setPlayerSpeed(100);
        GameManager.getPlayer().setAmmo(100);
        GameManager.getPlayer().setBulletSpeed(30000);

        for(Ship ship : GameManager.getShips()) {
            if(ship.getFaction() == GameManager.getPlayer().getFaction()) {

            } else {
                ship.setDamageDelt(30f);
                ship.setBulletSpeed(9000);
            }
        }
    }

    private static void mediumHandler() {
        System.out.println("Medium");
        GameManager.getPlayer().setHealth(80);
        GameManager.getPlayer().setDamageDelt(20f);
        GameManager.getPlayer().setPlunder(60);
        GameManager.getPlayer().setPlayerSpeed(85);
        GameManager.getPlayer().setAmmo(60);
        GameManager.getPlayer().setBulletSpeed(10000);

        for(Ship ship : GameManager.getShips()) {
            if(ship.getFaction() == GameManager.getPlayer().getFaction()) {

            } else {
                ship.setDamageDelt(20f);
                ship.setBulletSpeed(14000);
            }
        }
    }

    private static void hardHandler() {
        System.out.println("Hard");
        GameManager.getPlayer().setHealth(60);
        GameManager.getPlayer().setDamageDelt(30f);
        GameManager.getPlayer().setPlunder(30);
        GameManager.getPlayer().setPlayerSpeed(70);
        GameManager.getPlayer().setAmmo(30);
        GameManager.getPlayer().setBulletSpeed(8000);

        for(Ship ship : GameManager.getShips()) {
            if(ship.getFaction() == GameManager.getPlayer().getFaction()) {

            } else {
                ship.setDamageDelt(10f);
                ship.setBulletSpeed(20000);
            }
        }
    }

    public static String getCurrentDifficulty() {
        switch (currentDifficulty) {
            case EASY:
                return "easy";
            case MEDIUM:
                return "medium";
            default:
                return "hard";
        }
    }
}
