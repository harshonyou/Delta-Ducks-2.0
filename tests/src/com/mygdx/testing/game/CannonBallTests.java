package com.mygdx.testing.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.ComponentType;
import com.mygdx.game.Components.Renderable;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Entitys.CannonBall;
import com.mygdx.game.Entitys.Ship;
import com.mygdx.game.Faction;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.PhysicsManager;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.PirateGame;
import com.mygdx.testing.GdxTestRunner;
import com.mygdx.utils.Utilities;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(GdxTestRunner.class)
public class CannonBallTests {

    @BeforeClass
    public static void init(){
        PirateGame.loadResources();
        PhysicsManager.Initialise();
    }

    @AfterClass
    public static void dispose(){
        ResourceManager.reset();
    }

    @Test
    public void shootTest() {
        Ship ship = new Ship();
        CannonBall ball = new CannonBall();

        Transform shipTransformable = (Transform) ship.getComponent(ComponentType.Transform);
        Transform cannonTransformable = (Transform) ball.getComponent(ComponentType.Transform);

        Vector2 direction = new Vector2(1,1);
        Vector2 initial = cannonTransformable.getPosition().cpy();
        ship.shoot(direction);
        Vector2 after = cannonTransformable.getPosition().cpy();

        assertNotEquals(initial, after);
        assertEquals(shipTransformable.getPosition().cpy(),after);
    }
}