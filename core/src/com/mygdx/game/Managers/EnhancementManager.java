package com.mygdx.game.Managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public final class EnhancementManager {
    public static boolean initialised = false;

    private static int health;
    private static int ammo;

    private static int speed;
    private static int defaultSpeed;
    private static float speedTimer;
    public static float SPEED_MAX_TIMER;

    private static Vector2 teleport;

    private static int armor;

    private static float immunityCounter;
    public static float IMMUNITY_MAX_TIMER = .5f;

    private static float bulletspeedCounter;
    public static float BULLET_SPEED_TIMER = .5f;

    private static float unitPrice;

    public enum enhancement {HEALTH, SPEED, AMMO, ARMOR, IMMUNITY, BULLETSPEED};

    private static float healthTax;
    private static float speedTax;
    private static float ammoTax;
    private static float armorTax;
    private static float immunityTax;
    private static float bulletspeedTax;

    private static float timeCounter;
    private static float maxTime;
    public static String message;

    public static void Initialise() {
        if (initialised) {
            return;
        }
        initialised = true;

        health = 0;
        speed = 0;
        armor = 0;
        unitPrice = 0;
        ammo = 0;

        healthTax = 0;
        speedTax = 0;
        ammoTax = 0;
        armorTax = 0;
        immunityTax = 0;
        bulletspeedTax = 0;

        defaultSpeed = (int) GameManager.getPlayer().getPlayerSpeed();
        teleport = new Vector2(0, 0);
        immunityCounter = 0f;
        bulletspeedCounter = 0f;
        SPEED_MAX_TIMER = 2.5f;

        timeCounter = 0f;
        maxTime = 2f;
        message = "";
    }

    public static void update() {
        deltaTimeHandler();

        if(GameManager.getPlayer().getPlunder() > 0){
            healthHandler();
            speedHandler();
            ammoHandler();
            armorHandler();
            immunityHandler();
            bulletSpeedHandler();
        }
        displayHandler();
    }

    public static void deltaTimeHandler() {
        immunityCounter += EntityManager.getDeltaTime();
        bulletspeedCounter += EntityManager.getDeltaTime();
        speedTimer += EntityManager.getDeltaTime();

        timeCounter += EntityManager.getDeltaTime();
    }

    public static void setUnitPrice(float p) {
        unitPrice = p;
    }

    public static float getUnitPrice() {
        return unitPrice;
    }

    public static void taxation(float p) {
        if(GameManager.getPlayer().getPlunder() - p <= 0) {
            GameManager.getPlayer().setPlunder(0);
        } else {
            GameManager.getPlayer().setPlunder((int) (GameManager.getPlayer().getPlunder() - p));
        }
    }

    public static void setTax(enhancement e, float tax) {
        switch (e){
            case HEALTH:
                healthTax = tax;
                break;
            case SPEED:
                speedTax = tax;
                break;
            case AMMO:
                ammoTax = tax;
                break;
            case ARMOR:
                armorTax = tax;
                break;
            case IMMUNITY:
                immunityTax = tax;
                break;
            case BULLETSPEED:
                bulletspeedTax = tax;
                break;
            default:
                break;
        }
    }

    public static float getTaxation(enhancement e) {
        switch (e){
            case HEALTH:
                return healthTax;
            case SPEED:
                return speedTax;
            case AMMO:
                return ammoTax;
            case ARMOR:
                return armorTax;
            case IMMUNITY:
                return immunityTax;
            case BULLETSPEED:
                return bulletspeedTax;
            default:
                return 1f;
        }
    }

    public static boolean getValidation(enhancement e) {
        switch (e){
            case HEALTH:
                return GameManager.getPlayer().getPlunder() >= healthTax;
            case SPEED:
                return GameManager.getPlayer().getPlunder() >= speedTax;
            case AMMO:
                return GameManager.getPlayer().getPlunder() >= ammoTax;
            case ARMOR:
                return GameManager.getPlayer().getPlunder() >= armorTax;
            case IMMUNITY:
                return GameManager.getPlayer().getPlunder() >= immunityTax;
            case BULLETSPEED:
                return GameManager.getPlayer().getPlunder() >= bulletspeedTax;
            default:
                return false;
        }
    }

    public static void setHealth(int h) {
        health = h;
    }

    public static void healthHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            System.out.println("Health!");
            if (GameManager.getPlayer().getHealth() >= 100) {
                EnhancementManager.setDisplay("You already have got full health.");
            } else if (!getValidation(enhancement.HEALTH)) {
                EnhancementManager.setDisplay("You have not got sufficient plunder to buy health.");
            } else if(GameManager.getPlayer().getHealth() + health > 100) {
                taxation(getTaxation(enhancement.HEALTH));
                EnhancementManager.setDisplay("You have gained " + (100 - GameManager.getPlayer().getHealth()) + " health for " + getTaxation(enhancement.HEALTH) + " coins.");
                GameManager.getPlayer().setHealth(100);
            } else {
                taxation(getTaxation(enhancement.HEALTH));
                EnhancementManager.setDisplay("You have gained " + (health) + " health for " + getTaxation(enhancement.HEALTH) + " coins.");
                GameManager.getPlayer().setHealth(GameManager.getPlayer().getHealth() + health);
            }
        }
    }

    public static void setSpeed(int d, int s, float t) {
        defaultSpeed = d;
        speed = s;
        SPEED_MAX_TIMER = t;
    }

    public static void speedHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            System.out.println("Speed!");
            if(GameManager.getPlayer().getPlayerSpeed() == defaultSpeed) {
                if (getValidation(enhancement.SPEED)) {
                    taxation(getTaxation(enhancement.SPEED));
                    speedTimer = 0;
                    GameManager.getPlayer().setPlayerSpeed(speed);
                }
            }
        }

        if(speedTimer >= SPEED_MAX_TIMER) {
            speedTimer = 0;
            GameManager.getPlayer().setPlayerSpeed(defaultSpeed);
        }

    }

    public static void setAmmo(int a) {
        ammo = a;
    }

    public static void ammoHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
            System.out.println("Bullets!");
            if (getValidation(enhancement.AMMO)) {
                taxation(getTaxation(enhancement.AMMO));
                GameManager.getPlayer().setAmmo(GameManager.getPlayer().getAmmo() + ammo);
            }
        }
    }

    public static void setArmor(int a) {
        armor = a;
    }

    public static void armorHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
            System.out.println("Armor!");
            if(GameManager.getPlayer().getArmor() + armor > 100) {
                GameManager.getPlayer().setArmor(100);
            } else {
                if (getValidation(enhancement.ARMOR)) {
                    taxation(getTaxation(enhancement.ARMOR));
                    GameManager.getPlayer().setArmor(GameManager.getPlayer().getArmor() + armor);
                }
            }
        }
    }

    public static void immunityHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)){
            if(immunityCounter >= IMMUNITY_MAX_TIMER) {
                immunityCounter = 0f;
                System.out.println("Immunity!");
            }
        }
    }

    public static void bulletSpeedHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)){
            if(bulletspeedCounter >= BULLET_SPEED_TIMER) {
                bulletspeedCounter = 0f;
                System.out.println("Bullet Speed!");
            }
        }
    }

    public static void displayHandler() {
        if(timeCounter > maxTime) {
            timeCounter = 0;
            message = "";
        }
//            private static float timeCounter;
//        private static float maxTime;
    }

    public static void setMaxTime(float mT) {
        maxTime = mT;
    }

    public static String getdisplay() {
        return message;
    }

    public static void setDisplay(String s) {
        message = s;
        timeCounter = 0;
    }
}
