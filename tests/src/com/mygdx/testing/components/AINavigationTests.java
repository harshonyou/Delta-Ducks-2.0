package com.mygdx.testing.components;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.AINavigation;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Entitys.Boulder;
import com.mygdx.game.Entitys.Player;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.PhysicsManager;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.PirateGame;
import com.mygdx.testing.GdxTestRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class AINavigationTests {

    @BeforeClass
    public static void init(){
        PirateGame.loadResources();
        PhysicsManager.Initialise();
        GameManager.Initialise();
    }

    @AfterClass
    public static void dispose(){
        ResourceManager.reset();
    }

    @Test
    public void getBoundingRadius() {
        AINavigation nav = new AINavigation();
        assertEquals(nav.getBoundingRadius(), 128, 0);
    }

    @Test
    public void isTagged() {
        AINavigation nav = new AINavigation();
        assertFalse(nav.isTagged());
    }

    @Test
    public void setTagged() {
        AINavigation nav = new AINavigation();
        nav.setTagged(true);
        assertTrue(nav.isTagged());
    }

    @Test
    public void getZeroLinearSpeedThreshold() {
        AINavigation nav = new AINavigation();
        assertEquals(nav.getZeroLinearSpeedThreshold(), 0.01f, 0);
    }

    @Test
    public void getMaxLinearSpeed() {
        AINavigation nav = new AINavigation();
        assertEquals(nav.getMaxLinearSpeed(), 500, 0);
    }

    @Test
    public void setMaxLinearSpeed() {
        AINavigation nav = new AINavigation();
        nav.setMaxLinearSpeed(101);
        assertEquals(nav.getMaxLinearSpeed(), 101, 0);
    }

    @Test
    public void getMaxLinearAcceleration() {
        AINavigation nav = new AINavigation();
        assertEquals(nav.getMaxLinearAcceleration(), 50000, 0);
    }

    @Test
    public void setMaxLinearAcceleration() {
        AINavigation nav = new AINavigation();
        nav.setMaxLinearAcceleration(101);
        assertEquals(nav.getMaxLinearAcceleration(), 101, 0);
    }

    @Test
    public void getMaxAngularSpeed() {
        AINavigation nav = new AINavigation();
        assertEquals(nav.getMaxAngularSpeed(), 0, 0);
    }

    @Test
    public void setMaxAngularSpeed() {
        AINavigation nav = new AINavigation();
        nav.setMaxAngularSpeed(101);
        assertEquals(nav.getMaxAngularSpeed(), 101, 0);
    }

    @Test
    public void getMaxAngularAcceleration() {
        AINavigation nav = new AINavigation();
        assertEquals(nav.getMaxAngularAcceleration(), 0, 0);
    }

    @Test
    public void setMaxAngularAcceleration() {
        AINavigation nav = new AINavigation();
        nav.setMaxAngularAcceleration(101);
        assertEquals(nav.getMaxAngularAcceleration(), 101, 0);
    }

    @Test
    public void vectorToAngle() {
        AINavigation nav = new AINavigation();
        Vector2 v = new Vector2(13, 52);
        float f = (float) Math.atan2(-v.x, v.y);
        assertEquals(nav.vectorToAngle(v), f, 0);
    }

    @Test
    public void angleToVector() {
        AINavigation nav = new AINavigation();
        Vector2 v = new Vector2(13, 52);
        float f = .13f;
        Vector2 out = new Vector2();
        out.x = -(float) Math.sin(f);
        out.y = (float) Math.cos(f);
        assertEquals(nav.angleToVector(v, f), out);
    }

}
