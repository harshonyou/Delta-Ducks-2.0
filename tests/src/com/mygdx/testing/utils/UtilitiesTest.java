package com.mygdx.testing.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.testing.GdxTestRunner;
import com.mygdx.utils.Constants;
import com.mygdx.utils.Utilities;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.mygdx.utils.Constants.TILE_SIZE;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(GdxTestRunner.class)

public class UtilitiesTest {

    private int randomNum(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    @Test
    public void testVectorToAngle() {
        float x = randomNum(1, 100);
        float y = randomNum(1, 100);

        Vector2 testVector  = new Vector2(x, y);
        float testAngle = Utilities.vectorToAngle(testVector);

        float expectedAngle = (float) Math.atan2(-x, y);

        float sillyAngleFirst = (float) Math.atan2(x, y);
        float sillyAngleSecond = (float) Math.atan2(x, -y);

        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertEquals("", testAngle, expectedAngle, 0.0),
                () -> assertNotEquals(testAngle, sillyAngleFirst, 0.0, ""),
                () -> assertNotEquals(testAngle, sillyAngleSecond, 0.0, "")
        );
    }

    @Test
    public void testAngleToVector() {
        float x = randomNum(1, 100);
        float y = randomNum(1, 100);

        float testAngle = randomNum(1, 100);
        Vector2 testVector  = Utilities.angleToVector(new Vector2(), testAngle);

        Vector2 expectedVector = new Vector2(-(float) Math.sin(testAngle), (float) Math.cos(testAngle));

        Vector2 sillyVectorFirst = new Vector2((float) Math.sin(testAngle), -(float) Math.cos(testAngle));
        Vector2 sillyVectorSecond = new Vector2(-(float) Math.cos(testAngle), (float) Math.sin(testAngle));

        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertEquals("", testVector, expectedVector),
                () -> assertNotEquals(testVector, sillyVectorFirst, ""),
                () -> assertNotEquals(testVector, sillyVectorSecond, "")
        );
    }

    @Test
    public void testTilesToDistance() {
        float x = randomNum(1, 100);
        float y = randomNum(1, 100);

        float testTilesFloat = randomNum(1, 100);
        Vector2 testTilesVector = new Vector2(x, y);
        float testTileSizeFloat = randomNum(1, 100);

        float testDistanceFloat = Utilities.tilesToDistance(testTilesFloat);
        Vector2 testDistanceVector = Utilities.tilesToDistance(testTilesVector);

        float expectedDistanceFloat = Constants.TILE_SIZE * testTilesFloat;
        Vector2 expectedDistanceVector = testTilesVector.cpy().scl(Constants.TILE_SIZE);

        float sillyDistanceFloat = testTileSizeFloat * testTilesFloat;
        Vector2 sillyDistanceVector = testTilesVector.cpy().scl(testTileSizeFloat);

        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertEquals("", testDistanceFloat, expectedDistanceFloat, 0.0),
                () -> assertNotEquals(testDistanceFloat, sillyDistanceFloat, 0.0, ""),
                () -> assertEquals("", testDistanceVector, expectedDistanceVector),
                () -> assertNotEquals(testDistanceVector, sillyDistanceVector, "")
        );
    }

    @Test
    public void testDistanceToTiles() {
        float x = randomNum(1, 100);
        float y = randomNum(1, 100);

        float testDistanceFloat = randomNum(1, 100);
        Vector2 testDistanceVector = new Vector2(x, y);
        float testTileSizeFloat = randomNum(1, 100);

        float testTilesInt = Utilities.distanceToTiles(testDistanceFloat);
        Vector2 testTilesVector = Utilities.distanceToTiles(testDistanceVector);

        float expectedTilesInt = (int) (testDistanceFloat / Constants.TILE_SIZE);
        Vector2 expectedTilesVector = testDistanceVector.cpy().scl(1.0f / Constants.TILE_SIZE);

        float sillyDistanceFloat = testDistanceFloat / testTileSizeFloat;
        Vector2 sillyDistanceVector = testDistanceVector.cpy().scl(1.0f / testTileSizeFloat);

        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertEquals("", testTilesInt, expectedTilesInt, 0.0),
                () -> assertNotEquals(testTilesInt, sillyDistanceFloat, 0.0, ""),
                () -> assertEquals("", testTilesVector, expectedTilesVector),
                () -> assertNotEquals(testTilesVector, sillyDistanceVector, "")
        );
    }

    @Test
    public void testCheckProximity() {
        float a = randomNum(1, 100);
        float b = randomNum(1, 100);

        float x = randomNum(1, 100);
        float y = randomNum(1, 100);

        Vector2 testVectorA = new Vector2(a, b);
        Vector2 testVectorB = new Vector2(x, y);
        float testRadius = randomNum(1, 100);

        boolean testProximity = Utilities.checkProximity(testVectorA, testVectorB, testRadius);

        boolean expectedProximity = (Math.abs(testVectorA.dst2(testVectorB))) < (testRadius * testRadius);

        boolean sillyProximity = (Math.abs(testVectorA.dst2(testVectorB))) > (testRadius * testRadius);

        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertEquals("", testProximity, expectedProximity),
                () -> assertNotEquals(testProximity, sillyProximity, "")
        );
    }

    @Test
    public void testAngleBetween() {
        float a = randomNum(1, 100);
        float b = randomNum(1, 100);

        float x = randomNum(1, 100);
        float y = randomNum(1, 100);

        Vector2 testVectorA = new Vector2(a, b);
        Vector2 testVectorB = new Vector2(x, y);

        float testAngle = Utilities.angleBetween(testVectorA, testVectorB);

        float expectedAngle = MathUtils.atan2(
                testVectorB.y * testVectorA.x - testVectorB.x * testVectorA.y,
                testVectorB.x * testVectorA.x + testVectorB.y * testVectorA.y);

        float sillyAngle = MathUtils.atan2(
                testVectorA.y * testVectorB.x - testVectorA.x * testVectorB.y,
                testVectorA.x * testVectorB.x + testVectorA.y * testVectorB.y);

        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertEquals("", testAngle, expectedAngle, 0.0),
                () -> assertNotEquals(testAngle, sillyAngle,0.0,"")
        );
    }

    @Test
    @DisplayName("Scale")
    public void testScale() {
        float a = randomNum(1, 100);
        float b = randomNum(1, 100);

        float x = randomNum(1, 100);
        float y = randomNum(1, 100);

        Vector2 testVectorA = new Vector2(a, b);
        Vector2 testVectorB = new Vector2(x, y);

        float testX = randomNum(1, 100);
        float testMin0 = randomNum(1, 100);
        float testMax0 = randomNum(1, 100);
        float testMin1 = randomNum(1, 100);
        float testMax1 = randomNum(1, 100);

        float testScaleFloat = Utilities.scale(testX, testMin0, testMax0, testMin1, testMax1);
        float testScalVector = Utilities.scale(testX, testVectorA, testVectorB);

        float expectedScaleFloat = (testMax1 - testMin1) * ((testX - testMin0 * testX) / (testMax0 * testX - testMin0 * testX)) + testMin1;
        float expectedScaleVector = (testVectorB.y - testVectorB.x) * ((testX - testVectorA.x * testX) / (testVectorA.y * testX - testVectorA.x * testX)) + testVectorB.x;

        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertEquals("", testScaleFloat, expectedScaleFloat, 0.0),
                () -> assertEquals("", testScalVector, expectedScaleVector, 0.0)
        );
    }

    @Test
    public void testRound() {
        float x = randomNum(1, 100);
        float y = randomNum(1, 100);

        Vector2 testVector = new Vector2(x, y);

        Vector2 testRound = Utilities.round(testVector);

        Vector2 expectedRound = new Vector2(Math.round(x), Math.round(y));

        Vector2 sillyRound = new Vector2(Math.round(y), Math.round(x));

        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertEquals("", testRound, expectedRound),
                () -> assertNotEquals(testRound, sillyRound, "")
        );
    }

    @Test
    public void testRandomPos() {
        float testMin = randomNum(1, 100);
        float testMax = testMin + randomNum(1, 100);
        Random testRandom = new Random();


        Vector2 testRandomPos = Utilities.randomPos(testMin, testMax);

        Vector2 expectedRandomPos = new Vector2(testMin + testRandom.nextFloat() * (testMax - testMin), testMin + testRandom.nextFloat() * (testMax - testMin));

        Vector2 sillyRandomPos = new Vector2(testMax + testRandom.nextFloat() * (testMax - testMin), testMax + testRandom.nextFloat() * (testMax - testMin));

        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertFalse("", sillyRandomPos.x < testMax && sillyRandomPos.y < testMax),
                () -> assertNotEquals(testRandomPos, expectedRandomPos, "")
        );
    }

    @Test
    public void testRandomChoice() {
        int lengthOfList = randomNum(1, 100);
        ArrayList<Integer> testList = new ArrayList<>(lengthOfList);

        for(int i=1; i<lengthOfList+1; i++) {
            testList.add(i);
        }
        Integer choice = -1;
        int testRandomChoice = Utilities.randomChoice(testList, choice);

        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertNotEquals(0, testRandomChoice, "")
        );
    }

    @Test
    public void testFloor() {
        float x = randomNum(1, 100);
        float y = randomNum(1, 100);

        Vector2 testVector = new Vector2(x, y);

        Vector2 testFloor = Utilities.floor(testVector);

        Vector2 expectedFloor = new Vector2(MathUtils.floor(testVector.x), MathUtils.floor(testVector.y));

        Vector2 sillyFloor = new Vector2(MathUtils.floor(testVector.y), MathUtils.floor(testVector.x));

        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertEquals("", testFloor, expectedFloor),
                () -> assertNotEquals(testFloor, sillyFloor, "")
        );
    }

    @Test
    public void testContains() {
        int lengthOfList = randomNum(1, 100);
        ArrayList<Integer> testList = new ArrayList<>(lengthOfList);

        for(int i=1; i<lengthOfList+1; i++) {
            testList.add(i);
        }

        boolean expectedContains = Utilities.contains(testList, 1);

        boolean sillyContains = Utilities.contains(testList, 0);

        assertAll("The expected value is incorrect or some silly mistake has occurred.",
                () -> assertTrue("", expectedContains),
                () -> assertFalse("", sillyContains)
        );

    }
}