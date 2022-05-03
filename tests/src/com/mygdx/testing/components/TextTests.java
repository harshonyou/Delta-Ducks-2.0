package com.mygdx.testing.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Components.Text;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.PhysicsManager;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.PirateGame;
import com.mygdx.testing.GdxTestRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class TextTests {

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
    public void setPosition() {
        Text t = new Text();
        t.setPosition(-50,-50);
        assertEquals(t.getPosition(), new Vector2(-50, -50));
    }

    @Test
    public void getPosition() {
        Text t = new Text();
        assertEquals(t.getPosition(), new Vector2(0, 0));
    }

    @Test
    public void setFontColour() {
        Text t = new Text();
        t.setFontColour(new Vector3(-50,-50,-50));
        assertEquals(t.getFontColour(), new Vector3(-50, -50,-50));
    }

    @Test
    public void getFontColour() {
        Text t = new Text();
        assertEquals(t.getFontColour(), null);
    }
}
