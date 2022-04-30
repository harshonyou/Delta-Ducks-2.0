package com.mygdx.game.Managers;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Entitys.Building;
import com.mygdx.game.Faction;
import com.mygdx.game.PirateGame;
import com.mygdx.game.UI.Page;

public final class CaptureManager {

    public static boolean initialised = false;

    private static PirateGame reference;

    private static float captureBonus = 10;
    private static float destroyBonus = 10;

    private static float captureXpBonus = 10;
    private static float destroyXpBonus = 10;

    public static void Initialise(PirateGame r) {
        if (initialised) {
            return;
        }
        initialised = true;
        reference = r;
        captureBonus = 0;
        destroyBonus = 0;
        captureXpBonus = 0;
        destroyXpBonus = 0;
    }

    public static void pause() {
        if (!initialised) {
            return;
        }
        reference.pause();
    }

    public static void changeScreen() {
        if (!initialised) {
            return;
        }
        reference.setScreen(reference.pause);
    }

    public static void handler(Building flag) {
        if (!initialised) {
            return;
        }
        reference.pause();
        reference.capture.updateFlag(flag);
        reference.setScreen(reference.capture);
    }

//    public static void handlerResolver() {
//        if (!initialised) {
//            return;
//        }
//
//    }

    public static void setCaptureBonus(float b) {
        captureBonus = b;
    }

    public static float getCaptureBonus() {
        return captureBonus;
    }

    public static void setDestroyBonus(float b) {
        destroyBonus = b;
    }

    public static float getDestroyBonus() {
        return destroyBonus;
    }

    public static void setCaptureXpBonus(float b) {
        captureXpBonus = b;
    }

    public static float getCaptureXpBonus() {
        return captureXpBonus;
    }

    public static void setDestroyXpBonus(float b) {
        destroyXpBonus = b;
    }

    public static float getDestroyXpBonus() {
        return destroyXpBonus;
    }

    public static void captureHandler(String name) {
        CaptionManager.setMaxTime(5f);
        CaptionManager.setDisplay("You have captured " + name +" college\nbut you will still need to fight it's ships.\n(Gold Gained : " +captureBonus+ ")");
        GameManager.getPlayer().setPlunder((int) (GameManager.getPlayer().getPlunder() + captureBonus));
        GameManager.getPlayer().setXp((int) (GameManager.getPlayer().getXp() + captureXpBonus));
    }

    public static void destroyHandler(String name, int num) {
        CaptionManager.setMaxTime(5f);
        CaptionManager.setDisplay("You have destroyed " + name +" college\nand "+ num +" of it's ships.\n(Gold Gained : " +destroyBonus+ ")");
        GameManager.getPlayer().setPlunder((int) (GameManager.getPlayer().getPlunder() + destroyBonus));
        GameManager.getPlayer().setXp((int) (GameManager.getPlayer().getXp() + destroyXpBonus));
    }
}
