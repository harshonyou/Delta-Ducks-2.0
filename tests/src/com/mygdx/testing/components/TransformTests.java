package com.mygdx.testing.components;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.Transform;
import com.mygdx.testing.GdxTestRunner;
import com.mygdx.utils.Utilities;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class TransformTests {

    @Test
    public void setPosition() {
        Transform t = new Transform();
        t.setPosition(-50,-50);
        assertEquals(t.getPosition(), new Vector2(-50, -50));
    }

    @Test
    public void getPosition() {
        Transform t = new Transform();
        assertEquals(t.getPosition(), new Vector2(0, 0));
    }

    @Test
    public void setScale() {
        Transform t = new Transform();
        t.setScale(-50,-50);
        assertEquals(t.getScale(), new Vector2(-50, -50));
    }

    @Test
    public void getScale() {
        Transform t = new Transform();
        assertEquals(t.getScale(), new Vector2(1, 1));
    }

    @Test
    public void setRotation() {
        Transform t = new Transform();
        t.setRotation(50);
        assertEquals(t.getRotation(), 50, 0);
    }

    @Test
    public void getRotation() {
        Transform t = new Transform();
        assertEquals(t.getRotation(), 0, 0);
    }

    @Test
    public void getOrientation() {
        Transform t = new Transform();
        assertEquals(t.getOrientation(), 0, 0);
    }

    @Test
    public void vectorToAngle() {
        Transform t = new Transform();
        Vector2 v = new Vector2(13, 52);
        float f = (float) Math.atan2(-v.x, v.y);
        assertEquals(t.vectorToAngle(v), f, 0);
    }

    @Test
    public void angleToVector() {
        Transform t = new Transform();
        Vector2 v = new Vector2(13, 52);
        float f = .13f;
        Vector2 out = new Vector2();
        out.x = -(float) Math.sin(f);
        out.y = (float) Math.cos(f);
        assertEquals(t.angleToVector(v, f), out);
    }

}
