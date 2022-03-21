package com.mygdx.testing.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.testing.GdxTestRunner;
import com.mygdx.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import java.awt.datatransfer.FlavorListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@RunWith(GdxTestRunner.class)

public class ConstantsTest {
    Constants constants;

    @Before
    public void setUp() {
        constants = new Constants();
        constants.int_test();
    }

    @Test
    public void testScreenWidth() {
        assertEquals("", 1920, Constants.SCREEN_WIDTH);
    }

    @Test
    public void testScreenHeight() {
        assertEquals("", 1080, Constants.SCREEN_HEIGHT);
    }

    @Test
    public void testFullscreen() {
        assertEquals("", false, constants.FULLSCREEN);
    }

    @Test
    public void testAspectRatio() {
        assertEquals("", (float) constants.SCREEN_WIDTH / (float) constants.SCREEN_HEIGHT, constants.ASPECT_RATIO, 0.0);
    }

    @Test
    public void testViewportHeight() {
        assertEquals("", 800, constants.VIEWPORT_HEIGHT);
    }

    @Test
    public void testViewportWidth() {
        assertEquals("", (int) (constants.ASPECT_RATIO * constants.VIEWPORT_HEIGHT), constants.VIEWPORT_WIDTH);
    }

    @Test
    public void testHalfViewportHeight() {
        assertEquals("", constants.VIEWPORT_HEIGHT / 2, constants.HALF_VIEWPORT_HEIGHT);
    }

    @Test
    public void testHalfViewportWidth() {
        assertEquals("", constants.VIEWPORT_WIDTH / 2, constants.HALF_VIEWPORT_WIDTH);
    }

    @Test
    public void testDimensions() {
        assertEquals("", new Vector2(constants.VIEWPORT_WIDTH, constants.VIEWPORT_HEIGHT), constants.DIMENSIONS);
    }

    @Test
    public void testHalfDimensions() {
        assertEquals("", new Vector2(constants.HALF_VIEWPORT_WIDTH, constants.HALF_VIEWPORT_HEIGHT), constants.HALF_DIMENSIONS);
    }

    @Test
    public void testViewportTitle() {
        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertTrue("", constants.VIEWPORT_TITLE.length() > 5),
                () -> assertTrue("", constants.VIEWPORT_TITLE.length() < 50)
        );
    }

    @Test
    public void testPhysicsTimeStep() {
        assertEquals("", 1.0f / 60.0f, constants.PHYSICS_TIME_STEP, 0.0);
    }

    @Test
    public void testZoom() {
        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertTrue("", constants.ZOOM > 0f),
                () -> assertTrue("", constants.ZOOM < 5f)
        );
    }

    @Test
    public void testVSync() {
        assertEquals("", true, constants.VSYNC);
    }

    @Test
    public void testBuildingScale() {
        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertTrue("", constants.BUILDING_SCALE > 0f),
                () -> assertTrue("", constants.BUILDING_SCALE < 2f)
        );
    }

    @Test
    public void testTileSize() {
        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertTrue("", constants.TILE_SIZE > 10),
                () -> assertTrue("", constants.TILE_SIZE < 50)
        );
    }

    @Test
    public void testBackgroundColour() {
        assertEquals("", new Vector3(0.0f, 0.0f, 0.0f), constants.BACKGROUND_COLOUR);
    }

    @Test
    public void testOperatingSystem() {
        assertEquals("", System.getProperty("os.name"), constants.OPERATING_SYSTEM);
    }
}
