package com.mygdx.testing.managers;

import com.mygdx.game.Managers.CaptionManager;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.PhysicsManager;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.PirateGame;
import com.mygdx.testing.GdxTestRunner;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.mygdx.utils.Constants.INIT_CONSTANTS;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class CaptionTests {

    @BeforeClass
    public static void init() {
    }

    @AfterClass
    public static void dispose() {
    }

    @Before
    public void pre() {
        CaptionManager.reset();
    }

    @Test
    public void setMaxTime() {
        CaptionManager.Initialise();
        CaptionManager.setMaxTime(10);
        assertTrue("Equal", CaptionManager.getMaxTime()==10);
    }

    @Test
    public void getMaxTime() {
        CaptionManager.Initialise();
        assertTrue("Equal", CaptionManager.getMaxTime()==2);
    }

    @Test
    public void getdisplay() {
        CaptionManager.Initialise();
        assertTrue("Equal", CaptionManager.getdisplay()=="");
    }

    @Test
    public void setDisplay() {
        CaptionManager.Initialise();
        CaptionManager.setDisplay("TEST");
        assertTrue("Equal", CaptionManager.getdisplay()=="TEST");
    }
}
