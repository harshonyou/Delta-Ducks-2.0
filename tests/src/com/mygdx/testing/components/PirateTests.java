package com.mygdx.testing.components;

import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Entitys.Ship;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.PhysicsManager;
import com.mygdx.game.Managers.RenderingManager;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.PirateGame;
import com.mygdx.testing.GdxTestRunner;
import org.junit.*;
import org.junit.runner.RunWith;

import static com.mygdx.utils.Constants.INIT_CONSTANTS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class PirateTests {


    @BeforeClass
    public static void init() {
        PirateGame.loadResources();
        PhysicsManager.Initialise();
    }

    @AfterClass
    public static void dispose() {
        ResourceManager.reset();
    }

    @Test
    public void addTarget() {
        Pirate p = new Pirate();
        Ship s = new Ship();
        p.addTarget(s);
        assertTrue("True", p.getTargets().contains(s));
    }

    @Test
    public void getPlunder() {
        Pirate p = new Pirate();
        assertTrue("True", p.getPlunder() == 0);
    }

    @Test
    public void setPlunder() {
        Pirate p = new Pirate();
        p.setPlunder(110);
        assertTrue("True", p.getPlunder() - 100 == 10);
    }

    @Test
    public void addPlunder() {
        Pirate p = new Pirate();
        int pe = p.getPlunder();
        p.addPlunder(110);
        assertTrue("True", p.getPlunder() == pe + 110);
    }

    @Test
    public void getXp() {
        Pirate p = new Pirate();
        assertTrue("True", p.getXp() == 0);
    }

    @Test
    public void setXp() {
        Pirate p = new Pirate();
        p.setXp(110);
        assertTrue("True", p.getXp() - 100 == 10);
    }

    @Test
    public void addXp() {
        Pirate p = new Pirate();
        int pe = p.getXp();
        p.addXp(110);
        assertTrue("True", p.getXp() == pe + 110);
    }

    @Test
    public void getFaction() {
        Pirate p = new Pirate();
        assertTrue("True", p.getFaction().id == 1);
    }

    @Test
    public void setFactionId() {
        Pirate p = new Pirate();
        int pe = p.getFaction().id;
        p.setFactionId(2);
        assertFalse("True", p.getFaction().id == pe);
    }

    @Test
    public void takeDamage() {
        Pirate p = new Pirate();
        p.takeDamage(999);
        assertTrue("True", p.getHealth()==0);
    }

    @Test
    public void getHealth() {
        Pirate p = new Pirate();
        assertTrue("True", p.getHealth() == 100);
    }

    @Test
    public void setHealth() {
        Pirate p = new Pirate();
        p.setHealth(110);
        assertTrue("True", p.getHealth() - 100 == 10);
    }

    @Test
    public void getArmor() {
        Pirate p = new Pirate();
        assertTrue("True", p.getArmor() == 0);
    }

    @Test
    public void setArmor() {
        Pirate p = new Pirate();
        p.setArmor(110);
        assertTrue("True", p.getArmor() - 100 == 10);
    }

    @Test
    public void reload() {
        Pirate p = new Pirate();
        int pe = p.getAmmo();
        p.reload(100);
        assertTrue("True", p.getAmmo() == pe + 100);
    }

    @Test
    public void canAttack() {
        Pirate p = new Pirate();
        assertFalse("False", p.canAttack());
    }

    @Test
    public void isAgro() {
        Pirate p = new Pirate();
        assertFalse("False", p.isAgro());
    }

    @Test
    public void isAlive() {
        Pirate p = new Pirate();
        assertTrue("True", p.isAlive());
    }

    @Test
    public void kill() {
        Pirate p = new Pirate();
        p.kill();
        assertFalse("False", p.isAlive());
    }

    @Test
    public void setAmmo() {
        Pirate p = new Pirate();
        assertTrue("True", p.getAmmo() == 50);
    }

    @Test
    public void getAmmo() {
        Pirate p = new Pirate();
        p.setAmmo(110);
        assertTrue("True", p.getAmmo() - 100 == 10);
    }

}
