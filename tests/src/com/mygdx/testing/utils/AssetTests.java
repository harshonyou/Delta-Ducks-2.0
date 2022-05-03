package com.mygdx.testing.utils;

import com.badlogic.gdx.Gdx;
import com.mygdx.testing.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(GdxTestRunner.class)

public class AssetTests {

    @Test
    public void testUISkinFolderAssetExists() {
        assertAll("This test will only pass if all the required files under UISkin folder will exists.",
                () -> assertTrue("This test will only pass if default.fnt exists",
                        Gdx.files.internal("UISkin/default.fnt").exists()),
                () -> assertTrue("This test will only pass if default.png exists",
                        Gdx.files.internal("UISkin/default.png").exists()),
                () -> assertTrue("This test will only pass if skin.atlas exists",
                        Gdx.files.internal("UISkin/skin.atlas").exists()),
                () -> assertTrue("This test will only pass if skin.json exists",
                        Gdx.files.internal("UISkin/skin.json").exists()),
                () -> assertTrue("This test will only pass if uiskin.png exists",
                        Gdx.files.internal("UISkin/uiskin.png").exists())
        );
    }

    @Test
    public void testArialTTFAsset() {
        assertTrue("arial.ttf is missing",
                Gdx.files.internal("arial.ttf").exists());
    }

    @Test
    public void testBeachTilesetPNGAssetExists() {
        assertTrue("Beach Tileset.png is missing",
                Gdx.files.internal("Beach Tileset.png").exists());
    }

    @Test
    public void testBeachTilesetTSXAssetExists() {
        assertTrue("Beach Tileset.tsx is missing",
                Gdx.files.internal("Beach Tileset.tsx").exists());
    }

    @Test
    public void testBoatsPNGAssetExists() {
        assertTrue("boats.png Sprite is missing",
                Gdx.files.internal("boats.png").exists());
    }

    @Test
    public void testBoatsTXTAssetExists() {
        assertTrue("Boats.txt file is missing",
                Gdx.files.internal("Boats.txt").exists());
    }

    @Test
    public void testBuildingsTXTAssetExists() {
        assertTrue("Buildings.txt is missing",
                Gdx.files.internal("Buildings.txt").exists());
    }

    @Test
    public void testChestPNGAssetExists(){
        assertTrue("Chest Sprite is missing",
                Gdx.files.internal("Chest.png").exists());
    }

    @Test
    public void testGameSettingsJSONAssetExists() {
        assertTrue("GameSettings.json is missing",
                Gdx.files.internal("GameSettings.json").exists());
    }

    @Test
    public void testMapTMXAssetExists() {
        assertTrue("Map.tmx is missing",
                Gdx.files.internal("Map.tmx").exists());
    }

    @Test
    public void testMenuBGJPGAssetExists() {
        assertTrue("menuBG.jpg is missing",
                Gdx.files.internal("menuBG.jpg").exists());
    }

    @Test
    public void testOtherPNGAssetExists() {
        assertTrue("other.png is missing",
                Gdx.files.internal("other.png").exists());
    }

    @Test
    public void testShipPNGAssetExists() {
        assertTrue("ship.png is missing",
                Gdx.files.internal("ship.png").exists());
    }
}