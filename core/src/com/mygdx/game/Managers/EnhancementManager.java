package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public final class EnhancementManager {
    public static boolean initialised = false;

    private int health;
    private int speed;

    private static Vector2 teleport;

    private int armor;

    private static float immunityCounter;
    private static float IMMUNITY_MAX_TIMER = .5f;

    private static float bulletspeedCounter;
    private static float BULLET_SPEED_TIMER = .5f;

    public static void Initialise() {
        if (initialised) {
            return;
        }
        initialised = true;
        teleport = new Vector2(0, 0);
        immunityCounter = 0f;
        bulletspeedCounter = 0f;
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
    }

    public static void healthHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            System.out.println("Health!");

        }
    }

    public static void speedHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
            System.out.println("Speed!");

        }
    }

    public static void teleportHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
            System.out.println("Teleport!");

        }
    }

    public static void armorHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
            System.out.println("Armor!");

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
