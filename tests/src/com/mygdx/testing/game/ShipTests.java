package com.mygdx.testing.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Entitys.Ship;
import com.mygdx.game.Faction;
import com.mygdx.game.Managers.EntityManager;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.PhysicsManager;
import com.mygdx.game.PirateGame;
import com.mygdx.testing.GdxTestRunner;
import com.mygdx.utils.Utilities;
import org.junit.*;
import org.junit.runner.RunWith;

import com.badlogic.gdx.Gdx;

import java.util.concurrent.ThreadLocalRandom;

import static com.mygdx.utils.Constants.INIT_CONSTANTS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(GdxTestRunner.class)
public class ShipTests {

    private int randomNum(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    @BeforeClass
    public static void init(){
//        INIT_CONSTANTS();
//        PirateGame pg = new PirateGame();
//        PirateGame.loadResources();
//        EntityManager.Initialise();
//        PhysicsManager.Initialise(); // error
//        GameManager.Initialise();
//        GameManager.SpawnGame(1);
        PirateGame.loadResources();
        PhysicsManager.Initialise();
    }

    @AfterClass
    public static void dispose(){

    }

    @Test
    public void isAlive() {
        Ship s1 = new Ship();
        Ship s2 = new Ship();
        s2.setHealth(0);

        assertAll("",
                () -> assertTrue("Alive",
                        s1.isAlive()),
                () -> assertFalse("Dead",
                        s2.isAlive())
        );
    }

    @Test
    public void getAttackRange() {
        Ship s = new Ship();
        float f = Utilities.tilesToDistance(GameManager.getSettings().get("starting").getFloat("attackRange_tiles"));
        assertTrue("Equal", s.getAttackRange() == f);
    }

    @Test
    public void plunder() {
        Ship s = new Ship();
        int p = s.getPlunder();
        int x = randomNum(1, 100);
        s.plunder(x);
        assertTrue("Equal", s.getPlunder() == p+x);
    }

    @Test
    public void setFaction() {
        Ship s = new Ship();
        Faction f = s.getFaction();
        int x = randomNum(1, 4);
        s.setFaction(x);
        assertTrue("Equal", s.getFaction().id == x);
    }

    @Test
    public void getFaction() {
        Ship s = new Ship();
        int x = randomNum(1, 4);
        s.setFaction(x);
        Faction f = s.getFaction();
        assertTrue("Equal", s.getFaction().id == x);
    }

    @Test
    public void getHealth() {
        Ship s = new Ship();
        assertTrue("Equal", s.getHealth() == 100);
    }

    @Test
    public void setHealth() {
        Ship s = new Ship();
        s.setHealth(110);
        assertTrue("Equal", s.getHealth() - 100 == 10);
    }

    @Test
    public void getArmor() {
        Ship s = new Ship();
        assertTrue("Equal", s.getArmor() == 0);
    }

    @Test
    public void setArmor() {
        Ship s = new Ship();
        s.setArmor(110);
        assertTrue("Equal", s.getArmor() - 100 == 10);
    }

    @Test
    public void setSpeed() {
        Ship s = new Ship();
        s.setSpeed(10,10);
        assertTrue("Equal", s.getSpeed().equals(new Vector2(10.0f,10.0f)));
    }

    @Test
    public void getSpeed() {
        Ship s = new Ship();
        assertTrue("Equal", s.getSpeed().equals(new Vector2(0,0)));
    }

    @Test
    public void destroy() {
        Ship s = new Ship();
        s.destroy();
        assertFalse("Equal", s.isAlive());
    }

    @Test
    public void getPlunder() {
        Ship s = new Ship();
        assertTrue("Equal", s.getPlunder() == 0);
    }

    @Test
    public void setPlunder() {
        Ship s = new Ship();
        s.setPlunder(110);
        assertTrue("Equal", s.getPlunder() - 100 == 10);
    }

    @Test
    public void getPlunderBonus() {
        Ship s = new Ship();
        assertTrue("Equal", s.getPlunderBonus() == 10f);
    }

    @Test
    public void setPlunderBonus() {
        Ship s = new Ship();
        s.setPlunderBonus(110);
        assertTrue("Equal", s.getPlunderBonus() - 100f == 10f);
    }


    @Test
    public void getXp() {
        Ship s = new Ship();
        assertTrue("Equal", s.getXp() == 0);
    }

    @Test
    public void setXp() {
        Ship s = new Ship();
        s.setXp(110);
        assertTrue("Equal", s.getXp() - 100 == 10f);
    }

    @Test
    public void getXpBonus() {
        Ship s = new Ship();
        assertTrue("Equal", s.getXpBonus() == 10f);
    }

    @Test
    public void setXpBonus() {
        Ship s = new Ship();
        s.setXpBonus(110);
        assertTrue("Equal", s.getXpBonus() - 100 == 10f);
    }

    @Test
    public void getAmmo() {
        Ship s = new Ship();
        assertTrue("Equal", s.getAmmo() == 50);
    }

    @Test
    public void setAmmo() {
        Ship s = new Ship();
        s.setAmmo(110);
        assertTrue("Equal", s.getAmmo() - 100 == 10);
    }

    @Test
    public void setDamageDelt() {
        Ship s = new Ship();
        assertTrue("Equal", s.getAmmo() == 50);
    }

    @Test
    public void getBulletSpeed() {
        Ship s = new Ship();
        assertTrue("Equal", s.getBulletSpeed() == 10000.0f);
    }

    @Test
    public void setBulletSpeed() {
        Ship s = new Ship();
        s.setBulletSpeed(110);
        assertTrue("Equal", s.getBulletSpeed() - 100f == 10f);
    }

    @Test
    public void getPosition() {
        Ship s = new Ship();
        assertTrue("Equal", s.getPosition().equals(new Vector2(800, 800)));
    }

    @Test
    public void setPosition() {
        Ship s = new Ship();
        s.setPosition(new Vector2(-50, -50));
        assertTrue("Equal", s.getPosition().equals(new Vector2(-34, -34)));
    }

    @Test
    public void getVelocity() {
        Ship s = new Ship();
        assertTrue("Equal", s.getVelocity().equals(new Vector2(0, 0)));
    }

}
