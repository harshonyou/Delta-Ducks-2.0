package com.mygdx.game.Managers;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Entitys.Building;
import com.mygdx.game.Faction;
import com.mygdx.game.PirateGame;
import com.mygdx.game.UI.Page;

/**
 * Added the whole class for assessment 2
 * It helps to manage the management of capture or destroy college
 */
public final class CaptureManager {

    public static boolean initialised = false;

    private static PirateGame reference;

    private static float captureBonus = 10;
    private static float destroyBonus = 10;

    private static float captureXpBonus = 10;
    private static float destroyXpBonus = 10;

    /**
     * Should only be called once although if it isn't called at all it will be called automatically
     * @param r the game to refer
     */
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

    /**
     * Pause the content of the game
     */
    public static void pause() {
        if (!initialised) {
            return;
        }
        reference.pause();
    }

    /**
     * Change the screen to Pause Screen
     */
    public static void changeScreen() {
        if (!initialised) {
            return;
        }
        reference.setScreen(reference.pause);
    }

    /**
     * Triggers after the college is destroyed
     * @param flag of the college which is about to be either captured or destoryed
     */
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

    /**
     *
     * @param b update the capture gold bonus
     */
    public static void setCaptureBonus(float b) {
        captureBonus = b;
    }

    /**
     *
     * @return the capture gold bonus
     */
    public static float getCaptureBonus() {
        return captureBonus;
    }

    /**
     *
     * @param b update destory gold bonus
     */
    public static void setDestroyBonus(float b) {
        destroyBonus = b;
    }

    /**
     *
     * @return destroy gold bonus
     */
    public static float getDestroyBonus() {
        return destroyBonus;
    }

    /**
     *
     * @param b set the capture xp bonus
     */
    public static void setCaptureXpBonus(float b) {
        captureXpBonus = b;
    }

    /**
     *
     * @return the capture xp bonus
     */
    public static float getCaptureXpBonus() {
        return captureXpBonus;
    }

    /**
     *
     * @param b update destroy xp bonus
     */
    public static void setDestroyXpBonus(float b) {
        destroyXpBonus = b;
    }

    /**
     *
     * @return destroy xp bonus
     */
    public static float getDestroyXpBonus() {
        return destroyXpBonus;
    }

    /**
     * Triggers when player decides to capture the college
     * @param name of the college
     */
    public static void captureHandler(String name) {
        CaptionManager.setMaxTime(5f);
        CaptionManager.setDisplay("You have captured " + name +" college\nbut you will still need to fight it's ships.\n(Gold Gained : " +captureBonus+ ")");
        GameManager.getPlayer().setPlunder((int) (GameManager.getPlayer().getPlunder() + captureBonus));
        GameManager.getPlayer().setXp((int) (GameManager.getPlayer().getXp() + captureXpBonus));
    }

    /**
     * Triggers when player decides to destroy the college
     * @param name of the college
     * @param num of ships
     */
    public static void destroyHandler(String name, int num) {
        CaptionManager.setMaxTime(5f);
        CaptionManager.setDisplay("You have destroyed " + name +" college\nand "+ num +" of it's ships.\n(Gold Gained : " +destroyBonus+ ")");
        GameManager.getPlayer().setPlunder((int) (GameManager.getPlayer().getPlunder() + destroyBonus));
        GameManager.getPlayer().setXp((int) (GameManager.getPlayer().getXp() + destroyXpBonus));
    }
}
