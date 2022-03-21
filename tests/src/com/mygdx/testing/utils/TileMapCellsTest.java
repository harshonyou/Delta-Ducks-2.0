package com.mygdx.testing.utils;

import com.mygdx.testing.GdxTestRunner;
import com.mygdx.utils.TileMapCells;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)

public class TileMapCellsTest {

    @Test
    public void testObstacleID() {
        assertEquals("", 61, TileMapCells.OBSTACLE);
    }

    @Test
    public void testPassableID() {
        assertEquals("", 97, TileMapCells.PASSABLE);
    }

    @Test
    public void testPassableCost() {
        assertEquals("", 100000f, TileMapCells.OBSTACLE_COST, 0.0);
    }
}
