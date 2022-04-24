package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public final class EnhancementManager {
    public static boolean initialised = false;

    private static int health;

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

    public enum enhancement {HEALTH, SPEED, TELEPORT, ARMOR, IMMUNITY, BULLETSPEED};

    private static float healthTax;
    private static float speedTax;
    private static float teleportTax;
    private static float armorTax;
    private static float immunityTax;
    private static float bulletspeedTax;

    public static void Initialise() {
        if (initialised) {
            return;
        }
        initialised = true;

        health = 0;
        speed = 0;
        armor = 0;
        unitPrice = 0;

        healthTax = 0;
        speedTax = 0;
        teleportTax = 0;
        armorTax = 0;
        immunityTax = 0;
        bulletspeedTax = 0;

        defaultSpeed = (int) GameManager.getPlayer().getPlayerSpeed();
        teleport = new Vector2(0, 0);
        immunityCounter = 0f;
        bulletspeedCounter = 0f;
        SPEED_MAX_TIMER = 2.5f;
    }

    public static void update() {
        deltaTimeHandler();

        healthHandler();
        speedHandler();
        teleportHandler();
        armorHandler();
        immunityHandler();
        bulletSpeedHandler();
    }

    public static void deltaTimeHandler() {
        immunityCounter += EntityManager.getDeltaTime();
        bulletspeedCounter += EntityManager.getDeltaTime();
        speedTimer += EntityManager.getDeltaTime();
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
            case TELEPORT:
                teleportTax = tax;
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
            case TELEPORT:
                return teleportTax;
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

    public static void setHealth(int h) {
        health = h;
    }

    public static void healthHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            System.out.println("Health!");
            if(GameManager.getPlayer().getHealth() + health > 100) {
                GameManager.getPlayer().setHealth(100);
            } else {
                taxation(getTaxation(enhancement.HEALTH));
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
                taxation(getTaxation(enhancement.SPEED));
                speedTimer = 0;
                GameManager.getPlayer().setPlayerSpeed(speed);
            }
        }

        if(speedTimer >= SPEED_MAX_TIMER) {
            speedTimer = 0;
            GameManager.getPlayer().setPlayerSpeed(defaultSpeed);
        }

    }

    public static void teleportHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
            System.out.println("Teleport!");

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
                taxation(getTaxation(enhancement.ARMOR));
                GameManager.getPlayer().setArmor(GameManager.getPlayer().getArmor() + armor);
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
}
