package com.mygdx.testing.ai;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AI.Node;
import com.mygdx.game.AI.NodeHeuristic;
import com.mygdx.testing.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class NodeTests {

    @Test
    public void getPosition() {
        Node n = new Node(10, 20);
        assertEquals(n.getPosition(), new Vector2(10, 20));
    }

    @Test
    public void set() {
        Node n = new Node(10, 20);
        n.set(20, 10);
        assertEquals(n.getPosition(), new Vector2(20, 10));
    }
}
