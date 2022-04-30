package com.mygdx.testing.ai;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AI.Node;
import com.mygdx.game.AI.Path;
import com.mygdx.testing.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class PathTests {

    @Test
    public void estimate() {
        Node n1 = new Node(10, 20);
        Node n2 = new Node(20, 10);
        Path p = new Path(n1, n2);
        assertEquals(p.getCost(), -1, 0);
    }

    @Test
    public void getFromNode() {
        Node n1 = new Node(10, 20);
        Node n2 = new Node(20, 10);
        Path p = new Path(n1, n2);
        assertEquals(p.getFromNode(), n1);
    }

    @Test
    public void getToNode() {
        Node n1 = new Node(10, 20);
        Node n2 = new Node(20, 10);
        Path p = new Path(n1, n2);
        assertEquals(p.getToNode(), n2);
    }
}
