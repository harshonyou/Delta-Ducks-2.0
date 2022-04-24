package com.mygdx.game.Managers;

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

        EnhancementManager.setTax(EnhancementManager.enhancement.HEALTH, 20f);
        EnhancementManager.setTax(EnhancementManager.enhancement.SPEED, 30f);
        EnhancementManager.setTax(EnhancementManager.enhancement.AMMO, 5f);
        EnhancementManager.setTax(EnhancementManager.enhancement.ARMOR, 10f);
        EnhancementManager.setTax(EnhancementManager.enhancement.IMMUNITY, 40f);
        EnhancementManager.setTax(EnhancementManager.enhancement.BULLETSPEED, 10f);

        EnhancementManager.setHealth(30);
        EnhancementManager.setSpeed(80, 5000, 3f);
        EnhancementManager.setArmor(30);
        EnhancementManager.setAmmo(20);

        for(Ship ship : GameManager.getShips()) {
            if(ship.getFaction() == GameManager.getPlayer().getFaction()) {

            } else {
                ship.setDamageDelt(30f);
                ship.setBulletSpeed(9000);
                ship.setPlunderBonus(30f);
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

        EnhancementManager.setTax(EnhancementManager.enhancement.HEALTH, 25f);
        EnhancementManager.setTax(EnhancementManager.enhancement.SPEED, 30f);
        EnhancementManager.setTax(EnhancementManager.enhancement.AMMO, 10f);
        EnhancementManager.setTax(EnhancementManager.enhancement.ARMOR, 15f);
        EnhancementManager.setTax(EnhancementManager.enhancement.IMMUNITY, 40f);
        EnhancementManager.setTax(EnhancementManager.enhancement.BULLETSPEED, 15f);

        EnhancementManager.setHealth(20);
        EnhancementManager.setSpeed(75, 3000, 2f);
        EnhancementManager.setArmor(20);
        EnhancementManager.setAmmo(10);

        for(Ship ship : GameManager.getShips()) {
            if(ship.getFaction() == GameManager.getPlayer().getFaction()) {

            } else {
                ship.setDamageDelt(20f);
                ship.setBulletSpeed(14000);
                ship.setPlunderBonus(15f);
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

        EnhancementManager.setTax(EnhancementManager.enhancement.HEALTH, 30f);
        EnhancementManager.setTax(EnhancementManager.enhancement.SPEED, 40f);
        EnhancementManager.setTax(EnhancementManager.enhancement.AMMO, 15f);
        EnhancementManager.setTax(EnhancementManager.enhancement.ARMOR, 20f);
        EnhancementManager.setTax(EnhancementManager.enhancement.IMMUNITY, 35f);
        EnhancementManager.setTax(EnhancementManager.enhancement.BULLETSPEED, 20f);

        EnhancementManager.setHealth(10);
        EnhancementManager.setSpeed(60, 1000, 2f);
        EnhancementManager.setArmor(10);
        EnhancementManager.setAmmo(5);

        for(Ship ship : GameManager.getShips()) {
            if(ship.getFaction() == GameManager.getPlayer().getFaction()) {

            } else {
                ship.setDamageDelt(10f);
                ship.setBulletSpeed(20000);
                ship.setPlunderBonus(10f);
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
