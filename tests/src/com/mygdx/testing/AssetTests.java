package com.mygdx.testing;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    public void testCollegeAssetExists() {
        assertTrue("This test will only pass if ship.png exists",
                Gdx.files.internal("ship.png").exists());
    }
}