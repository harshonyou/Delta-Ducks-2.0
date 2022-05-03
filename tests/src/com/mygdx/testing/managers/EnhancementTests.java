package com.mygdx.testing.managers;

import com.mygdx.game.Entitys.Enhancement;
import com.mygdx.game.Entitys.Player;
import com.mygdx.game.Managers.*;
import com.mygdx.game.PirateGame;
import com.mygdx.testing.GdxTestRunner;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(GdxTestRunner.class)
public class EnhancementTests {

    @BeforeClass
    public static void init() {
        PirateGame.loadResources();
        PhysicsManager.Initialise();
        GameManager.Initialise();
    }

    @AfterClass
    public static void dispose() {
        ResourceManager.reset();
    }

    @Before
    public void pre() {
        GameManager.CreatePlayer();
        EnhancementManager.reset();
        EnhancementManager.Initialise();
    }

    @Test
    public void setUnitPrice() {
        ;EnhancementManager.setUnitPrice(10);
        assertTrue("Equal", EnhancementManager.getUnitPrice() == 10);
    }

    @Test
    public void getUnitPrice() {
        assertTrue("Equal", EnhancementManager.getUnitPrice() == 0);
    }

    @Test
    public void taxation() {
        Player p = GameManager.getPlayer();
        p.setPlunder(100);
        float pe = p.getPlunder();
        EnhancementManager.taxation(10);
        assertTrue("Equal", p.getPlunder() + 10 == pe);
    }

    @Test
    public void setTax() {
        EnhancementManager.setTax(EnhancementManager.enhancement.HEALTH, 10);
        EnhancementManager.setTax(EnhancementManager.enhancement.SPEED, 20);
        EnhancementManager.setTax(EnhancementManager.enhancement.AMMO, 30);
        EnhancementManager.setTax(EnhancementManager.enhancement.ARMOR, 40);
        EnhancementManager.setTax(EnhancementManager.enhancement.IMMUNITY, 50);
        EnhancementManager.setTax(EnhancementManager.enhancement.INFINITEAMMO, 60);

//        System.out.println(EnhancementManager.getTaxation(EnhancementManager.enhancement.HEALTH));
//        System.out.println(EnhancementManager.getTaxation(EnhancementManager.enhancement.SPEED));
//        System.out.println(EnhancementManager.getTaxation(EnhancementManager.enhancement.AMMO));
//        System.out.println(EnhancementManager.getTaxation(EnhancementManager.enhancement.ARMOR));
//        System.out.println(EnhancementManager.getTaxation(EnhancementManager.enhancement.IMMUNITY));
//        System.out.println(EnhancementManager.getTaxation(EnhancementManager.enhancement.INFINITEAMMO));

        assertAll("",
                () -> assertTrue("Equal",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.HEALTH) == 10f),
                () -> assertTrue("Equal",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.SPEED) == 20f),
                () -> assertTrue("Equal",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.AMMO) == 30f),
                () -> assertTrue("Equal",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.ARMOR) == 40f),
                () -> assertTrue("Equal",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.IMMUNITY) == 50f),
                () -> assertTrue("Equal",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.INFINITEAMMO) == 60f)
        );
    }

    @Test
    public void getTax() {
//        System.out.println(EnhancementManager.getTaxation(EnhancementManager.enhancement.HEALTH));
//        System.out.println(EnhancementManager.getTaxation(EnhancementManager.enhancement.SPEED));
//        System.out.println(EnhancementManager.getTaxation(EnhancementManager.enhancement.AMMO));
//        System.out.println(EnhancementManager.getTaxation(EnhancementManager.enhancement.ARMOR));
//        System.out.println(EnhancementManager.getTaxation(EnhancementManager.enhancement.IMMUNITY));
//        System.out.println(EnhancementManager.getTaxation(EnhancementManager.enhancement.INFINITEAMMO));

        assertAll("",
                () -> assertTrue("Equal",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.HEALTH) == 0),
                () -> assertTrue("Equal",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.SPEED) == 0),
                () -> assertTrue("Equal",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.AMMO) == 0),
                () -> assertTrue("Equal",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.ARMOR) == 0),
                () -> assertTrue("Equal",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.IMMUNITY) == 0),
                () -> assertTrue("Equal",
                        EnhancementManager.getTaxation(EnhancementManager.enhancement.INFINITEAMMO) == 0)
        );
    }

    @Test
    public void getValidation() {
        Player p = GameManager.getPlayer();
        p.setPlunder(100);

        assertAll("",
                () -> assertTrue("Equal",
                        EnhancementManager.getValidation(EnhancementManager.enhancement.HEALTH)),
                () -> assertTrue("Equal",
                        EnhancementManager.getValidation(EnhancementManager.enhancement.SPEED)),
                () -> assertTrue("Equal",
                        EnhancementManager.getValidation(EnhancementManager.enhancement.AMMO)),
                () -> assertTrue("Equal",
                        EnhancementManager.getValidation(EnhancementManager.enhancement.ARMOR)),
                () -> assertTrue("Equal",
                        EnhancementManager.getValidation(EnhancementManager.enhancement.IMMUNITY)),
                () -> assertTrue("Equal",
                        EnhancementManager.getValidation(EnhancementManager.enhancement.INFINITEAMMO))
        );
    }

    @Test
    public void setHealth() {
        EnhancementManager.setHealth(100);
        assertTrue("Equal", EnhancementManager.getHealth()==100);
    }

    @Test
    public void getHealth() {
        assertTrue("Equal", EnhancementManager.getHealth()==0);
    }

    @Test
    public void healthHandlerFree() {
        Player p = GameManager.getPlayer();
        p.setHealth(90);
        EnhancementManager.setHealth(10);
        EnhancementManager.healthHandler(true);
        assertTrue("Equal", p.getHealth() == 100);
    }

    @Test
    public void healthHandlerPaid() {
        Player p = GameManager.getPlayer();
        p.setPlunder(100);
        p.setHealth(90);
        EnhancementManager.setTax(EnhancementManager.enhancement.HEALTH,10);
        EnhancementManager.setHealth(10);
        EnhancementManager.healthHandler(false);
        assertAll("",
                () -> assertTrue("Equal",
                        p.getHealth() == 100),
                () -> assertTrue("Equal",
                        p.getPlunder() == 90)
        );
    }

    @Test
    public void setAmmo() {
        EnhancementManager.setAmmo(100);
        assertTrue("Equal", EnhancementManager.getAmmo()==100);
    }

    @Test
    public void getAmmo() {
        assertTrue("Equal", EnhancementManager.getAmmo()==0);
    }

    @Test
    public void ammoHandlerFree() {
        Player p = GameManager.getPlayer();
        p.setAmmo(90);
        EnhancementManager.setAmmo(10);
        EnhancementManager.ammoHandler(true);
        assertTrue("Equal", p.getAmmo() == 100);
    }

    @Test
    public void ammoHandlerPaid() {
        Player p = GameManager.getPlayer();
        p.setPlunder(100);
        p.setAmmo(90);
        EnhancementManager.setTax(EnhancementManager.enhancement.AMMO,10);
        EnhancementManager.setAmmo(10);
        EnhancementManager.ammoHandler(false);
        assertAll("",
                () -> assertTrue("Equal",
                        p.getAmmo() == 100),
                () -> assertTrue("Equal",
                        p.getPlunder() == 90)
        );
    }

    @Test
    public void setArmor() {
        EnhancementManager.setArmor(100);
        assertTrue("Equal", EnhancementManager.getArmor()==100);
    }

    @Test
    public void getArmor() {
        assertTrue("Equal", EnhancementManager.getArmor()==0);
    }

    @Test
    public void armorHandlerFree() {
        Player p = GameManager.getPlayer();
        p.setArmor(90);
        EnhancementManager.setArmor(10);
        EnhancementManager.armorHandler(true);
        assertTrue("Equal", p.getArmor() == 100);
    }

    @Test
    public void armorHandlerPaid() {
        Player p = GameManager.getPlayer();
        p.setPlunder(100);
        p.setArmor(90);
        EnhancementManager.setTax(EnhancementManager.enhancement.ARMOR,10);
        EnhancementManager.setArmor(10);
        EnhancementManager.armorHandler(false);
        assertAll("",
                () -> assertTrue("Equal",
                        p.getArmor() == 100),
                () -> assertTrue("Equal",
                        p.getPlunder() == 90)
        );
    }

    @Test
    public void setImmunity() {
        EnhancementManager.setImmunity(100);
        assertTrue("Equal", EnhancementManager.getImmunity()==100);
    }

    @Test
    public void getImmunity() {
        assertTrue("Equal", EnhancementManager.getImmunity()==0.5f);
    }

    @Test
    public void immunityHandlerFree() {
        Player p = GameManager.getPlayer();
        p.setArmor(90);
        EnhancementManager.setImmunity(10);
        EnhancementManager.immunityHandler(true);
        assertTrue("Equal", p.getArmor() == 999);
    }

    @Test
    public void immunityHandlerPaid() {
        Player p = GameManager.getPlayer();
        p.setPlunder(100);
        p.setArmor(90);
        EnhancementManager.setTax(EnhancementManager.enhancement.IMMUNITY,10);
        EnhancementManager.setImmunity(10);
        EnhancementManager.immunityHandler(false);
        assertAll("",
                () -> assertTrue("Equal",
                        p.getArmor() == 999),
                () -> assertTrue("Equal",
                        p.getPlunder() == 90)
        );
    }

    @Test
    public void setInfiniteAmmo() {
        EnhancementManager.setInfiniteAmmo(100);
        assertTrue("Equal", EnhancementManager.getInfiniteAmmo()==100);
    }

    @Test
    public void getInfiniteAmmo() {
        assertTrue("Equal", EnhancementManager.getInfiniteAmmo()==0.5f);
    }

    @Test
    public void infiniteAmmoHandlerFree() {
        Player p = GameManager.getPlayer();
        p.setAmmo(90);
        EnhancementManager.setInfiniteAmmo(10);
        EnhancementManager.infiniteAmmoHandler(true);
        assertTrue("Equal", p.getAmmo() == 999);
    }

    @Test
    public void infiniteAmmoHandlerPaid() {
        Player p = GameManager.getPlayer();
        p.setPlunder(100);
        p.setAmmo(90);
        EnhancementManager.setTax(EnhancementManager.enhancement.INFINITEAMMO,10);
        EnhancementManager.setInfiniteAmmo(10);
        EnhancementManager.infiniteAmmoHandler(false);
        assertAll("",
                () -> assertTrue("Equal",
                        p.getAmmo() == 999),
                () -> assertTrue("Equal",
                        p.getPlunder() == 90)
        );
    }


    @Test
    public void setSpeed() {
        EnhancementManager.setSpeed(0, 100, 10);
        assertTrue("Equal", EnhancementManager.getSpeed()==100);
    }

    @Test
    public void getSpeed() {
        assertTrue("Equal", EnhancementManager.getSpeed()==0);
    }

    @Test
    public void speedHandlerFree() {
        Player p = GameManager.getPlayer();
        EnhancementManager.setSpeed(100, 200, 10);
        EnhancementManager.speedHandler(true);
        assertTrue("Equal", p.getPlayerSpeed() == 200);
    }

    @Test
    public void speedHandlerPaid() {
        Player p = GameManager.getPlayer();
        p.setPlunder(100);
        EnhancementManager.setTax(EnhancementManager.enhancement.SPEED,10);
        EnhancementManager.setSpeed(100, 200, 10);
        EnhancementManager.speedHandler(false);
        EnhancementManager.taxation(EnhancementManager.getTaxation(EnhancementManager.enhancement.SPEED));
        assertAll("",
                () -> assertTrue("Equal" + p.getPlayerSpeed(),
                        p.getPlayerSpeed() == 200),
                () -> assertTrue("Equal" + p.getPlunder(),
                        p.getPlunder() == 90)
        );
    }
}
