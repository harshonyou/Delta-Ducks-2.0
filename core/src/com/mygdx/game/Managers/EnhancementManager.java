package com.mygdx.game.Managers;

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

    private static boolean immunityToggle;
    private static float defaultArmor;
    private static float immunityCounter;
    public static float IMMUNITY_MAX_TIMER = .5f;

    private static boolean infiniteAmmoToggle;
    private static float defaultAmmo;
    private static float infiniteAmmoCounter;
    public static float INFINITEAMMO_MAX_TIMER = .5f;

    private static float bulletspeedCounter;
    public static float BULLET_SPEED_TIMER = .5f;

    private static float unitPrice;

    public enum enhancement {HEALTH, SPEED, AMMO, ARMOR, IMMUNITY, INFINITEAMMO};

    private static float healthTax;
    private static float speedTax;
    private static float ammoTax;
    private static float armorTax;
    private static float immunityTax;
    private static float infiniteAmmoTax;

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
        infiniteAmmoTax = 0;

        defaultSpeed = (int) GameManager.getPlayer().getPlayerSpeed();
        teleport = new Vector2(0, 0);
        immunityCounter = 0f;
        bulletspeedCounter = 0f;
        SPEED_MAX_TIMER = 2.5f;

        infiniteAmmoCounter = 0f;
        immunityToggle = false;
        infiniteAmmoToggle = false;
    }

    public static void update() {
        deltaTimeHandler();

        if(GameManager.getPlayer().getPlunder() > 0){
            healthHandler();
            speedHandler();
            ammoHandler();
            armorHandler();
            immunityHandler();
            infiniteAmmoHandler();
        }
    }

    public static void deltaTimeHandler() {
        immunityCounter += EntityManager.getDeltaTime();
        bulletspeedCounter += EntityManager.getDeltaTime();
        speedTimer += EntityManager.getDeltaTime();
        infiniteAmmoCounter += EntityManager.getDeltaTime();
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
            case INFINITEAMMO:
                infiniteAmmoTax = tax;
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
            case INFINITEAMMO:
                return infiniteAmmoTax;
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
            case INFINITEAMMO:
                return GameManager.getPlayer().getPlunder() >= infiniteAmmoTax;
            default:
                return false;
        }
    }

    public static void setHealth(int h) {
        health = h;
    }

    public static void healthHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            if (GameManager.getPlayer().getHealth() >= 100) {
                CaptionManager.setDisplay("You already have got full health.");
            } else if (!getValidation(enhancement.HEALTH)) {
                CaptionManager.setDisplay("You have not got sufficient plunder to buy health.");
            } else if(GameManager.getPlayer().getHealth() + health > 100) {
                taxation(getTaxation(enhancement.HEALTH));
                CaptionManager.setDisplay("You have gained " + (100 - GameManager.getPlayer().getHealth()) + " health for " + getTaxation(enhancement.HEALTH) + " coins.");
                GameManager.getPlayer().setHealth(100);
            } else {
                taxation(getTaxation(enhancement.HEALTH));
                CaptionManager.setDisplay("You have gained " + (health) + " health for " + getTaxation(enhancement.HEALTH) + " coins.");
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
            if (!getValidation(enhancement.SPEED)) {
                CaptionManager.setDisplay("You have not got sufficient plunder to buy speed boost.");
            } else if(GameManager.getPlayer().getPlayerSpeed() == defaultSpeed) {
                taxation(getTaxation(enhancement.SPEED));
                speedTimer = 0;
                GameManager.getPlayer().setPlayerSpeed(speed);
                CaptionManager.setDisplay("You have activated speed boost for " + getTaxation(enhancement.SPEED) + " coins.");
            } else {
                CaptionManager.setDisplay("You already have got speed boost activated.");
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
        if (!getValidation(enhancement.AMMO)) {
            CaptionManager.setDisplay("You have not got sufficient plunder to buy ammo.");
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
            System.out.println("Bullets!");
            taxation(getTaxation(enhancement.AMMO));
            GameManager.getPlayer().setAmmo(GameManager.getPlayer().getAmmo() + ammo);
            CaptionManager.setDisplay("You just purchased " + ammo + " ammo for " + getTaxation(enhancement.AMMO) + " coins.");
        }
    }

    public static void setArmor(int a) {
        armor = a;
    }

    public static void armorHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
            System.out.println("Armor!");
            if (GameManager.getPlayer().getArmor() >= 100) {
                CaptionManager.setDisplay("You already have got full armor.");
            } else if (!getValidation(enhancement.ARMOR)) {
                CaptionManager.setDisplay("You have not got sufficient plunder to buy armor.");
            } if(GameManager.getPlayer().getArmor() + armor > 100) {
                taxation(getTaxation(enhancement.ARMOR));
                CaptionManager.setDisplay("You have gained " + (100 - GameManager.getPlayer().getArmor()) + " armor for " + getTaxation(enhancement.ARMOR) + " coins.");
                GameManager.getPlayer().setArmor(100);
            } else {
                taxation(getTaxation(enhancement.ARMOR));
                GameManager.getPlayer().setArmor(GameManager.getPlayer().getArmor() + armor);
                CaptionManager.setDisplay("You have gained " + (armor) + " armor for " + getTaxation(enhancement.ARMOR) + " coins.");
            }
        }
    }

    public static void setImmunity(float t) {
        IMMUNITY_MAX_TIMER = t;
    }

    public static void immunityHandler() {
         if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
             if (!getValidation(enhancement.IMMUNITY)) {
                 CaptionManager.setDisplay("You have not got sufficient plunder to buy immunity.");
             } if(!immunityToggle) {
                taxation(getTaxation(enhancement.IMMUNITY));
                immunityCounter = 0f;
                defaultArmor = GameManager.getPlayer().getArmor();
                GameManager.getPlayer().hideArmor();
                GameManager.getPlayer().setArmor(999);
                immunityToggle = true;
                CaptionManager.setDisplay("You have gained 999 armor for " + IMMUNITY_MAX_TIMER + " seconds\nin exchange of " + getTaxation(enhancement.IMMUNITY) + " coins.");
            } else {
                CaptionManager.setDisplay("You already have got immunity activated.");
            }
        }

        if(immunityCounter >= IMMUNITY_MAX_TIMER && immunityToggle) {
            immunityCounter = 0;
            immunityToggle = false;
            GameManager.getPlayer().setArmor((int) defaultArmor);
            GameManager.getPlayer().showArmor();
        }
    }

    public static void setInfiniteAmmo(float t) {
        INFINITEAMMO_MAX_TIMER = t;
    }

    public static void infiniteAmmoHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)){
            if (!getValidation(enhancement.INFINITEAMMO)) {
                CaptionManager.setDisplay("You have not got sufficient plunder to buy infinite ammo.");
            } if(!infiniteAmmoToggle) {
                taxation(getTaxation(enhancement.INFINITEAMMO));
                infiniteAmmoCounter = 0f;
                System.out.println("Infinite Ammo!");
                defaultAmmo = GameManager.getPlayer().getAmmo();
                GameManager.getPlayer().setAmmo(999);
                infiniteAmmoToggle = true;
                CaptionManager.setDisplay("You have gained 999 ammo for " + INFINITEAMMO_MAX_TIMER + " seconds\nin exchange of " + getTaxation(enhancement.INFINITEAMMO) + " coins.");
            } else {
                CaptionManager.setDisplay("You already have got infinite ammo activated.");
            }
        }

        if(infiniteAmmoCounter >= INFINITEAMMO_MAX_TIMER && infiniteAmmoToggle) {
            infiniteAmmoCounter = 0;
            infiniteAmmoToggle = false;
            GameManager.getPlayer().setAmmo((int) defaultAmmo);
        }
    }

}
