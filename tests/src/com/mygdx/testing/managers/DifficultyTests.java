package com.mygdx.testing.managers;

import com.mygdx.game.Managers.*;
import com.mygdx.game.PirateGame;
import com.mygdx.testing.GdxTestRunner;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.mygdx.utils.Constants.INIT_CONSTANTS;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(GdxTestRunner.class)
public class DifficultyTests {

    @BeforeClass
    public static void init() {
        INIT_CONSTANTS();
        PirateGame.loadResources();
        PhysicsManager.Initialise();
        GameManager.Initialise();

    }

    @AfterClass
    public static void dispose() {
        GameManager.reset();
        ResourceManager.reset();
    }

    @Before
    public void pre() {
        DifficultyManager.reset();
    }

    @Test
    public void easyPlayerHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.EASY);
        assertAll("",
                () -> assertTrue("True",
                        GameManager.getPlayer().getHealth() == 100),
                () -> assertTrue("True",
                        GameManager.getPlayer().getArmor() == 50),
                () -> assertTrue("True",
                        GameManager.getPlayer().getPlunder() == 100),
                () -> assertTrue("True",
                        GameManager.getPlayer().getPlayerSpeed() == 100.0),
                () -> assertTrue("True",
                        GameManager.getPlayer().getAmmo() == 100),
                () -> assertTrue("True",
                        GameManager.getPlayer().getBulletSpeed() == 30000)
        );

    }

    @Test
    public void mediumPlayerHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.MEDIUM);
        assertAll("",
                () -> assertTrue("True",
                        GameManager.getPlayer().getHealth() == 80),
                () -> assertTrue("True",
                        GameManager.getPlayer().getArmor() == 35),
                () -> assertTrue("True",
                        GameManager.getPlayer().getPlunder() == 60),
                () -> assertTrue("True",
                        GameManager.getPlayer().getPlayerSpeed() == 85),
                () -> assertTrue("True",
                        GameManager.getPlayer().getAmmo() == 60),
                () -> assertTrue("True",
                        GameManager.getPlayer().getBulletSpeed() == 10000)
        );
    }

    @Test
    public void hardPlayerHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.HARD);
        assertAll("",
                () -> assertTrue("True",
                        GameManager.getPlayer().getHealth() == 60),
                () -> assertTrue("True",
                        GameManager.getPlayer().getArmor() == 20),
                () -> assertTrue("True",
                        GameManager.getPlayer().getPlunder() == 30),
                () -> assertTrue("True",
                        GameManager.getPlayer().getPlayerSpeed() == 70),
                () -> assertTrue("True",
                        GameManager.getPlayer().getAmmo() == 30),
                () -> assertTrue("True",
                        GameManager.getPlayer().getBulletSpeed() == 8000)
        );
    }

    @Test
    public void easyEnhancementCostHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.EASY);
        assertAll("",
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.HEALTH) == 20),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.SPEED) == 30),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.AMMO) == 5),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.ARMOR) == 10),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.IMMUNITY) == 40),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.INFINITEAMMO) == 10)
        );
    }

    @Test
    public void mediumEnhancementCostHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.MEDIUM);
        assertAll("",
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.HEALTH) == 25),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.SPEED) == 30),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.AMMO) == 10),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.ARMOR) == 15),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.IMMUNITY) == 40),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.INFINITEAMMO) == 15)
        );
    }

    @Test
    public void hardEnhancementCostHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.HARD);
        assertAll("",
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.HEALTH) == 30),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.SPEED) == 40),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.AMMO) == 15),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.ARMOR) == 20),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.IMMUNITY) == 35),
                () -> assertTrue("True",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.INFINITEAMMO) == 20)
        );
    }

    @Test
    public void easyEnhancementHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.EASY);
        assertAll("",
                () -> assertTrue("True",
                        EnhancementManager.getHealth() == 30),
                () -> assertTrue("True",
                        EnhancementManager.getSpeed() == 5000),
                () -> assertTrue("True",
                        EnhancementManager.getArmor() == 30),
                () -> assertTrue("True",
                        EnhancementManager.getAmmo() == 20),
                () -> assertTrue("True",
                        EnhancementManager.getImmunity() == 10),
                () -> assertTrue("True",
                        EnhancementManager.getInfiniteAmmo() == 10)
        );
    }

    @Test
    public void mediumEnhancementHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.MEDIUM);
        assertAll("",
                () -> assertTrue("True",
                        EnhancementManager.getHealth() == 20),
                () -> assertTrue("True",
                        EnhancementManager.getSpeed() == 3000),
                () -> assertTrue("True",
                        EnhancementManager.getArmor() == 20),
                () -> assertTrue("True",
                        EnhancementManager.getAmmo() == 10),
                () -> assertTrue("True",
                        EnhancementManager.getImmunity() == 5),
                () -> assertTrue("True",
                        EnhancementManager.getInfiniteAmmo() == 5)
        );
    }

    @Test
    public void hardEnhancementHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.HARD);
        assertAll("",
                () -> assertTrue("True",
                        EnhancementManager.getHealth() == 10),
                () -> assertTrue("True",
                        EnhancementManager.getSpeed() == 1000),
                () -> assertTrue("True",
                        EnhancementManager.getArmor() == 10),
                () -> assertTrue("True",
                        EnhancementManager.getAmmo() == 5),
                () -> assertTrue("True",
                        EnhancementManager.getImmunity() == 3),
                () -> assertTrue("True",
                        EnhancementManager.getInfiniteAmmo() == 3)
        );
    }

    @Test
    public void easyCaptureHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.EASY);
        assertAll("",
                () -> assertTrue("True",
                        CaptureManager.getCaptureBonus() == 60),
                () -> assertTrue("True",
                        CaptureManager.getCaptureXpBonus() == 70)
        );
    }

    @Test
    public void mediumCaptureHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.MEDIUM);
        assertAll("",
                () -> assertTrue("True",
                        CaptureManager.getCaptureBonus() == 30),
                () -> assertTrue("True",
                        CaptureManager.getCaptureXpBonus() == 100)
        );
    }

    @Test
    public void hardCaptureHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.HARD);
        assertAll("",
                () -> assertTrue("True",
                        CaptureManager.getCaptureBonus() == 20),
                () -> assertTrue("True",
                        CaptureManager.getCaptureXpBonus() == 120)
        );
    }

    @Test
    public void easyDestroyHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.EASY);
        assertAll("",
                () -> assertTrue("True",
                        CaptureManager.getDestroyBonus() == 100),
                () -> assertTrue("True",
                        CaptureManager.getDestroyXpBonus() == 100)
        );
    }

    @Test
    public void mediumDestroyHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.MEDIUM);
        assertAll("",
                () -> assertTrue("True",
                        CaptureManager.getDestroyBonus() == 50),
                () -> assertTrue("True",
                        CaptureManager.getDestroyXpBonus() == 120)
        );
    }

    @Test
    public void hardDestroyHandler() {
        GameManager.CreatePlayer();
        GameManager.createCollegeAndNPC();

        DifficultyManager.Initialise(DifficultyManager.Difficulty.HARD);
        assertAll("",
                () -> assertTrue("True",
                        CaptureManager.getDestroyBonus() == 30),
                () -> assertTrue("True",
                        CaptureManager.getDestroyXpBonus() == 150)
        );
    }
}