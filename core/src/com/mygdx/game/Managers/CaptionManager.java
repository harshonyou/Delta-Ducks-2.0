package com.mygdx.game.Managers;

import com.badlogic.gdx.math.Vector2;

public class CaptionManager {
    public static boolean initialised = false;

    private static float timeCounter;
    private static float maxTime;
    public static String message;

    public static void Initialise() {
        if (initialised) {
            return;
        }
        initialised = true;

        timeCounter = 0f;
        maxTime = 2f;
        message = "";
    }

    public static void update() {
        deltaTimeHandler();
        displayHandler();
    }

    public static void deltaTimeHandler() {
        timeCounter += EntityManager.getDeltaTime();
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
